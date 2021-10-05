package io.reddist.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.reddist.services.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component public class JWTUtil {

    @Value("${jwt.key}") private String KEY;
    @Value("${jwt.validity}") private long VALIDITY;
    @Autowired private UserDetailsServiceImpl userDetails;

    public String generateToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(new Date(now.getTime() + VALIDITY))
                .signWith(SignatureAlgorithm.HS512, KEY).compact();
    }

    public boolean validateToken(String token) {
        return !Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    private boolean isTokenExpired(String token) { return getExpirationDate(token).before(new Date()); }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        return (bearerToken != null && bearerToken.startsWith("Bearer ")) ? bearerToken.substring(7) : null;
    }

    public Authentication getAuthentication(String token) {
        UserDetails details = this.userDetails.loadUserByUsername(getUsername(token));
        try {
            return new UsernamePasswordAuthenticationToken(details, "", details.getAuthorities());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getUsername(String token) { return getClaim(token, Claims::getSubject); }

    public Date getExpirationDate(String token) { return getClaim(token, Claims::getExpiration); }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody());
    }

    public String decode(HttpServletRequest req) {
        String token = this.resolveToken(req);
        return token == null? null: this.getUsername(token); }
}