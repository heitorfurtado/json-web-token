package br.com.heitor.jsonwebtoken.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

import java.util.Date;

@NoArgsConstructor
@Data
//@ConfigurationProperties(prefix = "jwt.config")
public class JwtAuthenticationService {

    //    @Value("${jwt.config.secretKey}")
    private static final String TOKEN_SECRET_KEY = "SgtPeppersSecret";

//    @Value("${jwt.config.tokenPrefix}")
    private static final String TOKEN_PREFIX = "Bearer ";

//    @Value("${jwt.config.header}")
    private static final String TOKEN_HEADER = "Authorization";

//    @Value("${jwt.config.authorities}")
    private static final String AUTHORITIES = "authorities";

//    @Value("${jwt.config.tokenExpirationAfterMinutes}")
//    private static final Long TOKEN_EXPIRATION_TIME_MINUTES = 15L;

    //    @Value("${jwt.config.tokenExpirationAfterMinutes}")
    private static final long TOKEN_EXPIRATION_TIME_MINUTES = 900_000; //15 min

    public static String generateToken(Authentication auth) {
        return TOKEN_PREFIX + Jwts.builder().
                setSubject(auth.getName())
                .claim(AUTHORITIES, auth.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME_MINUTES))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET_KEY)
                .compact();
    }

    public String getTokenFromHeader(String header) {
        return header.replace(TOKEN_PREFIX, "");
    }

    public static String getHeader() {
        return TOKEN_HEADER;
    }

    public static String getBearer() {
        return TOKEN_PREFIX;
    }

    public static String getTokenSecretKey() {
        return TOKEN_SECRET_KEY;
    }

}
