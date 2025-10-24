package com.iychay.be.auth.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.iychay.be.common.config.JwtProperties;
import com.iychay.be.user.model.User;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtTokenService implements TokenService {

    private final JwtProperties jwtProperties;
    private final Algorithm algorithm;

    public JwtTokenService(JwtProperties jwtProperties) {
        if (!StringUtils.hasText(jwtProperties.getSecret())) {
            throw new IllegalStateException("La clave secreta para firmar JWT no está configurada");
        }
        if (jwtProperties.getExpirationMinutes() == null) {
            throw new IllegalStateException("La expiración de JWT no está configurada");
        }
        this.jwtProperties = jwtProperties;
        this.algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
    }

    @Override
    public String generateToken(User user) {
        Objects.requireNonNull(user, "El usuario no puede ser nulo");

        Instant now = Instant.now();
        Instant expiresAt = now.plus(jwtProperties.getExpirationMinutes(), ChronoUnit.MINUTES);

        return JWT.create()
                .withSubject(resolveSubject(user))
                .withClaim("email", user.getEmail())
                .withClaim("rol", user.getRol() != null ? user.getRol().name() : null)
                .withClaim("nombre", user.getNombre())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiresAt))
                .sign(algorithm);
    }

    private String resolveSubject(User user) {
        if (user.getId() != null) {
            return user.getId().toString();
        }
        if (StringUtils.hasText(user.getEmail())) {
            return user.getEmail();
        }
        throw new IllegalStateException("No se puede determinar el sujeto del token JWT");
    }
}
