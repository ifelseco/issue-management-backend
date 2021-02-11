package com.ifelseco.issueapp.security;

import com.auth0.jwt.JWT;
import com.ifelseco.issueapp.config.JWTConstants;
import com.ifelseco.issueapp.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * Created by Moon on 2/10/2021
 */
public class JwtGenerator {
    public static String generateAccessJWT(String subject, int expiration, String secret )
    {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .withClaim("type", "ACCESS")
                .sign(HMAC512(secret.getBytes()));


    }

    public static String generateRefreshToken(String subject, int expiration, String secret ) {

        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .withClaim("type", "REFRESH")
                .sign(HMAC512(secret.getBytes()));
    }
}
