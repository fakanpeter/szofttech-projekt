package hu.pazmany.dto;

import jakarta.validation.constraints.Size;

public class UserDTO {
    @Size(min = 5, message = "Username must be at least 5 characters long")
    private String username;
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
