package hu.pazmany.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;

public class JwtTokenProvider {

    private final String secretKey = "cci5lQJvw4TX6vSdR2PIcTx4n9w2cE8F";

    public String generateToken(Integer userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            // Parse the token and extract the username
            String username = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            // Check if the username is not null or empty
            return username != null && !username.isEmpty();
        } catch (JwtException | IllegalArgumentException e) {
            // Token is invalid or malformed
            return false;
        }
    }
}