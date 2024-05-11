package hu.pazmany.controller;

import hu.pazmany.dto.DetailedDogDTO;
import hu.pazmany.dto.DogDTO;
import hu.pazmany.dto.UserDTO;
import hu.pazmany.security.JwtTokenProvider;
import hu.pazmany.service.DogService;
import hu.pazmany.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://vau-vau.web.app/")
public class Controller {
	private final DogService dogService;
	private final UserService userService;

	@Autowired
	public Controller(DogService dogService, UserService userService) {
		this.dogService = dogService;
		this.userService = userService;
	}


	@GetMapping("/dogs")
	public List<DogDTO> getAllDogs(){
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

	@PostMapping("/newdog")
	public ResponseEntity<?> addNewDog(@RequestBody DetailedDogDTO dto, @RequestHeader("Authorization") String token) {
		if(isValidToken(token)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		dogService.addNewDog(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();

	}

	@PostMapping("/dogs/{id}/edit")
	public ResponseEntity<?> editDog(@PathVariable Integer id, @RequestBody DetailedDogDTO dto, @RequestHeader("Authorization") String token) {
		if (isValidToken(token)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		// Retrieve the dog entity from the database
		Optional<DetailedDogDTO> optionalDog = dogService.get(id);
		if (optionalDog.isPresent()) {

			// Save the updated dog entity
			dogService.editDog(id,dto);

			return ResponseEntity.ok("Dog attributes updated successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/dogs/{id}")
	public ResponseEntity<?> deleteDog(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
		if (isValidToken(token)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		dogService.deleteDog(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO request) {
		// Validate the registration request
		if (!isValidRegisterRequest(request)) {
			return ResponseEntity.badRequest().body("Invalid registration request");
		}

		// Check if the username is already taken
        if (userService.isUserExists(request.getUsername())) {
			return ResponseEntity.badRequest().body("Username already exists");
		}

		// Save the user entity
		userService.registerUser(request);

		return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<UserDTO> login(@Valid @RequestBody UserDTO request) {
		if (!isValidLoginRequest(request)) {
			return ResponseEntity.badRequest().body(null);
		}

		UserDTO userDTO = userService.login(request);

		if (userDTO == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(userDTO);
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

	private boolean isValidRegisterRequest(UserDTO request) {
		return request != null && request.getUsername() != null && request.getPassword() != null;
	}

	private boolean isValidToken(String token) {
		return token == null || !verifyTokenSignature(token);
	}

	private boolean verifyTokenSignature(String token) {
		JwtTokenProvider tokenProvider = new JwtTokenProvider();
		return tokenProvider.validateToken(token);
	}
}
