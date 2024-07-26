package site.cleanfree.be_main.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${JWT.SECRET_KEY}")
    private String SECRET_KEY;

    @Value("${JWT.ACCESS_EXPIRATION_TIME}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    public String getUuid(String token) {
        try {
            if (token.contains("Bearer ")) {
                token = token.substring(7).trim();
            }
            log.info("token: {}", token);
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            log.info("Error parsing JWT: {}, error message: {}", token, e.getMessage());
            return null;
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails);
    }

    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        log.info("generateToken {}", userDetails);
        Map<String, Object> modifiableExtractClaims = new HashMap<>(extractClaims);
        modifiableExtractClaims.put("TokenType", "access");
        return Jwts.builder()
                .setClaims(modifiableExtractClaims) //정보저장
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) //토근 발행 시간
                .setExpiration(
                        new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME)) //토큰 만료 시간
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 토큰의 유효성 및 만료일자 확인
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = extractAllClaims(token);
            String uuid = claims.getSubject();
            return (uuid.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            return false;
        }
    }

    // JWT 토큰의 만료일자 확인
    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }
}
