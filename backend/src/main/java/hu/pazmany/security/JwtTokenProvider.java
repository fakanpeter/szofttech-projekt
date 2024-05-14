package hu.pazmany.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtTokenProvider {
    private static final JwtTokenProvider INSTANCE = new JwtTokenProvider();
    private JwtTokenProvider() {
        // private constructor to prevent instantiation
    }
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static JwtTokenProvider getInstance() {
        return INSTANCE;
    }

    public String generateToken(Integer userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        System.out.println("Parser token:" + token +"|");
        try {
            // Parse the token and extract the username
            String userId = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            // Check if the username is not null or empty
            return userId != null && !userId.isEmpty();
        } catch (JwtException | IllegalArgumentException e) {
            // Token is invalid or malformed
            return false;
        }
    }
}