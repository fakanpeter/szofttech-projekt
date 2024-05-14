package hu.pazmany.service;


import hu.pazmany.dto.UserDTO;
import hu.pazmany.jpe.UserEntity;
import hu.pazmany.jpe.UserRepository;
import hu.pazmany.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserDTO userDTO) {
        // Hash the password
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());

        // Create a new UserEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(hashedPassword);

        // Save the user to the database
        userRepository.save(userEntity);
    }

    public UserDTO login(UserDTO request, JwtTokenProvider jwtTokenProvider) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();
            // Check if the password matches
            if (passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
                // Password matches, generate token using user's ID
                String token = jwtTokenProvider.generateToken(userEntity.getId());
                // Create and return UserDTO with token
                UserDTO userDTO = new UserDTO();
                userDTO.setUsername(request.getUsername());
                userDTO.setToken(token);
                System.out.println("login: " + token);
                return userDTO;
            }
        }
        // Invalid credentials
        return null;
    }

    private String generateToken(Integer userId, JwtTokenProvider jwtTokenProvider) {
        return jwtTokenProvider.generateToken(userId);
    }

    public boolean isUserExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
