package hu.pazmany.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.pazmany.dto.DetailedDogDTO;
import hu.pazmany.dto.DogDTO;
import hu.pazmany.dto.UserDTO;
import hu.pazmany.security.JwtTokenProvider;
import hu.pazmany.service.DogService;
import hu.pazmany.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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

    @PostMapping("/newdog")
    public ResponseEntity<?> addNewDog(@RequestBody DetailedDogDTO dto, @RequestHeader("Authorization") String token) {
        if (!isValidToken(token)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Save the dog and picture
        try {
            dogService.addNewDog(dto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Wrong picture format, vagy nem tudom én ide csak feljárok");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Dog created successfully");
    }

    @PostMapping(value = "/dogs/{id}/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editDog(@PathVariable Integer id, @RequestHeader("Authorization") String token, @RequestParam("dog") String stringDogDTO, @RequestParam("picture") MultipartFile mpf) {
        if (!isValidToken(token)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Retrieve the dog entity from the database
        Optional<DetailedDogDTO> optionalDog = dogService.get(id);
        ObjectMapper objectMapper = new ObjectMapper();
        DetailedDogDTO dogDTO;
        if (optionalDog.isPresent()) {
            try {
                dogDTO = objectMapper.readValue(stringDogDTO, DetailedDogDTO.class);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid DogDTO JSON format");
            }
            // Save the updated dog entity
            try {
                dogService.editDog(id, dogDTO, mpf);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Wrong picture format, vagy nem tudom én ide csak feljárok");
            }

            return ResponseEntity.ok("Dog attributes updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/dogs/{id}")
    public ResponseEntity<?> deleteDog(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        if (!isValidToken(token)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        dogService.deleteDog(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO request) {
        // Validate the registration request
        ValidationError validationError = validateRegisterRequest(request);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError.getMessage());
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

        UserDTO userDTO = userService.login(request, jwtTokenProvider);

        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(userDTO);
    }


    /*--------  Validation functions  --------*/

    private boolean isValidLoginRequest(UserDTO request) {
        if (request == null) {
            return false;
        }

        String username = request.getUsername();
        String password = request.getPassword();
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();

        // If all checks pass, return true
    }

    private ValidationError validateRegisterRequest(UserDTO request) {
        String username_regex = "^\\w{5,20}$";
        // username can contain numbers, upper and lowercase characters
        String password_regex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$";

        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            return new RequestValidationError();
        }
        if (!Pattern.matches(username_regex, request.getUsername())) {
            return new UsernameValidationError();
        }
        if (!Pattern.matches(password_regex, request.getPassword())) {
            return new PasswordValidationError();
        }

        return null;
    }

    private boolean isValidToken(String token) {
        return token != null && verifyTokenSignature(token);
    }

    private boolean verifyTokenSignature(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtTokenProvider.validateToken(token);
        } else {
            return false;
        }

    }

    private abstract static class ValidationError {
        private final String message;

        public String getMessage() {
            return message;
        }

        ValidationError(String m) {
            message = m;
        }
    }

    private static class RequestValidationError extends ValidationError {
        RequestValidationError() {
            super("Érvénytelen kérés");
        }
    }

    private static class UsernameValidationError extends ValidationError {
        UsernameValidationError() {
            super("Érvénytelen felhasználónév");
        }
    }

    private static class PasswordValidationError extends ValidationError {
        PasswordValidationError() {
            super("Érvénytelen jelszó");
        }
    }
}
