package comidev.apicerseufisi.jwt;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import comidev.apicerseufisi.exceptions.HttpException;

import org.springframework.http.HttpStatus;

@Service
public class JwtService {
    private static final String SECRET = "apicerseufisi";
    private static final String EXPIRES_IN_SECOND = "1800";
    private static final String BEARER = "Bearer ";
    private static final String ISSUER = "apicerseufisi";

    public Tokens createTokens(Payload payload) {
        String accessToken = createToken(payload, 0);
        String resfreshToken = createToken(payload, 1800);
        return new Tokens(accessToken, resfreshToken);
    }

    private String createToken(Payload payload, long addExpiresInSecond) {
        long expiresAtMilliSecond = (Long.parseLong(EXPIRES_IN_SECOND) + addExpiresInSecond) * 1000;

        return JWT.create()
                .withIssuer(ISSUER) // empresa
                .withIssuedAt(new Date()) // fecha de creación
                .withNotBefore(new Date()) // valido desde
                .withExpiresAt(new Date(System.currentTimeMillis() + expiresAtMilliSecond))
                .withClaim("payload", payload.toMap())
                .sign(Algorithm.HMAC256(SECRET));
    }

    public boolean isBearer(String authorization) {
        return authorization != null
                && authorization.toLowerCase().startsWith(BEARER.toLowerCase())
                && authorization.length() > (BEARER.length() * 2)
                && authorization.split("\\.").length == 3;
    }

    public Payload verify(String authorization) {
        if (!isBearer(authorization)) {
            String message = "No es 'Bearer': " + authorization;
            throw new HttpException(HttpStatus.UNAUTHORIZED, message);
        }
        try {
            Map<String, Object> payloadMap = JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer(ISSUER).build()
                    .verify(authorization.substring(BEARER.length()))
                    .getClaim("payload").asMap();

            return new Payload(payloadMap);

        } catch (Exception e) {
            String message = "Token incorrecto: " + e.getMessage();
            throw new HttpException(HttpStatus.UNAUTHORIZED, message);
        }
    }
}
