package hu.pazmany.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.pazmany.dto.DetailedDogDTO;
import hu.pazmany.dto.DogDTO;
import hu.pazmany.dto.UserDTO;
import hu.pazmany.security.JwtTokenProvider;
import hu.pazmany.service.DogService;
import hu.pazmany.service.UserService;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class Controller {
	private final DogService dogService;
	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider = JwtTokenProvider.getInstance();

	@Autowired
	public Controller(DogService dogService, UserService userService) {
		this.dogService = dogService;
		this.userService = userService;
	}


	@GetMapping("/dogs")
	public List<DogDTO> getAllDogs() {
		return dogService.getAllDogs();
	}

	@GetMapping("/dogs/{id}")
	public ResponseEntity<?> getDogById(@PathVariable Integer id) {
		return dogService.get(id)
				.map(dogEntity -> ResponseEntity.ok(new DetailedDogDTO(
						dogEntity.getId(),
						dogEntity.getName(),
						dogEntity.getPicture(),
						dogEntity.getAge(),
						dogEntity.getBreed())))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping(value = "/newdog")
	public ResponseEntity<?> addNewDog(@RequestHeader("Authorization") String token, @RequestParam("dog") String stringDogDTO, @RequestParam(value = "picture", required = false) MultipartFile mpf) {
		if (inValidToken(token)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		ObjectMapper objectMapper = new ObjectMapper();
		DetailedDogDTO dogDTO;
		try {
			dogDTO = objectMapper.readValue(stringDogDTO, DetailedDogDTO.class);
			ResponseEntity<?> response = validateDogData(dogDTO);
			if (Objects.nonNull(response)) return response;
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hibás JSON formátum");
		}
		// Save the dog and picture
		try {
			if (mpf != null && !mpf.isEmpty()) {
				dogService.addNewDog(dogDTO, mpf);
			} else {
				dogService.addNewDog(dogDTO, null);
			}
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Hibás képformátum");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Kutya sikeresen hozzáadva");
	}

	@PostMapping(value = "/dogs/{id}/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> editDog(@PathVariable Integer id, @RequestHeader("Authorization") String token, @RequestParam("dog") String stringDogDTO, @RequestParam(value = "picture", required = false) MultipartFile mpf) {
		if (inValidToken(token)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		// Retrieve the dog entity from the database
		Optional<DetailedDogDTO> optionalDog = dogService.get(id);
		ObjectMapper objectMapper = new ObjectMapper();
		DetailedDogDTO dogDTO;
		if (optionalDog.isPresent()) {
			try {
				dogDTO = objectMapper.readValue(stringDogDTO, DetailedDogDTO.class);
				ResponseEntity<?> response = validateDogData(dogDTO);
				if (Objects.nonNull(response)) return response;
			} catch (IOException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hibás JSON formátum");
			}
			// Save the updated dog entity
			try {
				if (mpf != null && !mpf.isEmpty()) {
					dogService.editDog(id, dogDTO, mpf);
				} else {
					dogService.editDog(id, dogDTO, null);
				}
			} catch (IOException e) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Hibás képformátum");
			}

			return ResponseEntity.ok("Kutya sikeresen módosítva");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/dogs/{id}")
	public ResponseEntity<?> deleteDog(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
		if (inValidToken(token)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		dogService.deleteDog(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO request) {
		// Validating and registering user inside a single function
		if (request == null || request.getUsername() == null || request.getPassword() == null)
			return ResponseEntity.badRequest().body("Érvénytelen kérés");

		ResponseEntity<?> response = validateRegister(request);
		if (Objects.nonNull(response)) return response;

		// Save the user entity
		userService.registerUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).body("Felhasználó sikeresen regisztrálva");
	}

	@PostMapping("/login")
	public ResponseEntity<UserDTO> login(@Valid @RequestBody UserDTO request) {
		if (!isValidLoginRequest(request)) {
			return ResponseEntity.badRequest().body(null);
		}

		UserDTO userDTO = userService.login(request, jwtTokenProvider);

		if (userDTO == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(userDTO);
	}


	/*--------  Independent validation functions  --------*/

	@Nullable
	public ResponseEntity<?> validateDogData(@Valid @RequestBody DetailedDogDTO request) {
		if (request.getName().length() < 2 || request.getName().length() > 20)
			return ResponseEntity.badRequest().body("A kutyanév 2 és 20 karakter között legyen");
		if (!Pattern.matches("^[a-zA-ZáéíóöőúüűÁÉÍÓÖŐÚÜŰ ]*$",request.getName()))
			return ResponseEntity.badRequest().body("A kutyanév kis- és nagybetűkből álljon");
		if (request.getAge() < 0)
			return ResponseEntity.badRequest().body("A kutya életkora nem lehet negatív");
		return null;
	}
	@Nullable
	private ResponseEntity<?> validateRegister(@Valid @RequestBody UserDTO request) {
		// Check if the username is already taken
		if (userService.isUserExists(request.getUsername()))
			return ResponseEntity.badRequest().body("Felhasználónév már létezik");

		if(!Pattern.matches("^\\w{5,20}$", request.getUsername()))
			return ResponseEntity.badRequest().body("A felhasználónév csak betűket és számokat tartalmazhat");

		if(request.getUsername().length() < 5 || request.getUsername().length() > 20)
			return ResponseEntity.badRequest().body("A felhasználónév hossza 5 és 20 karakter között legyen");

		if(request.getPassword().length() < 8 || request.getPassword().length() > 20)
			return ResponseEntity.badRequest().body("A jelszó hossza 5 és 20 karakter között legyen");

		if(!Pattern.matches("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$", request.getPassword()))
			return ResponseEntity.badRequest().body("A jelszó tartalmazzon nagybetűt és számot");
		return null;
	}

	private boolean isValidLoginRequest(UserDTO request) {
		if (request == null) {
			return false;
		}

		String username = request.getUsername();
		String password = request.getPassword();
		return username != null && !username.isEmpty() && password != null && !password.isEmpty();

		// If all checks pass, return true
	}
	private boolean inValidToken(String token) {
		return token == null || !verifyTokenSignature(token);
	}

	private boolean verifyTokenSignature(String token) {
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
			return jwtTokenProvider.validateToken(token);
		} else {
			return false;
		}

	}

}
