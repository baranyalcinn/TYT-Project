package tyt.auth.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final String SECRET_KEY = "gizli_anahtar";

    public static String generateToken(UserDetails userDetails) {
        // Oluşturulma tarihi
        Date createdDate = new Date();
        // Süre (1 saat)
        Date expirationDate = new Date(createdDate.getTime() + 3600000);

        return Jwts.builder().subject(userDetails.getUsername())
                .issuedAt(createdDate)
                .expiration(expirationDate)
                .signWith(SignatureAlgorithm
                        .HS512, SECRET_KEY)
                .compact();

    }
}

