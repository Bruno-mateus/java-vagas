package br.com.brunomateus.gestao_vagas.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public static String ObjectToJson(Object object) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String GenerateToken(UUID id, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create()
                .withExpiresAt(expiresIn)
                .withIssuer("javagas")
                .withClaim("roles", Arrays.asList("COMPANY"))
                .withSubject(id.toString()).sign(algorithm);
       return token;
    }
}
