package com.example.invoke.serviceimpl;

import com.example.invoke.service.UserTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service("userTokenService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserTokenServiceImpl implements UserTokenService {

    public static final String AUTHHEADERAUTHORIZATION = "Authorization";
    public static final String AUDIENCEWEB = "web";
    public static final String SECRET = "leetomlee123";
    public static final String HEADERBEAR = "Bearer ";
    public static final String TOKENEXPIERD = "0";


    @Override
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Date getExpireTimeFromToken(String token) {
        Date time;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            time = claims.getExpiration();
        } catch (Exception e) {
            time = null;
        }
        return time;
    }

    @Override
    public Boolean validTokenExpire(String token) {
        Date tokenExpire = this.getExpireTimeFromToken(token);
        Date now = new Date(System.currentTimeMillis());

        if (tokenExpire.compareTo(now) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String refreshToken(String token, String expireTime) {
        String refreshedToken;
        Date a = new Date();
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            claims.setIssuedAt(a);
            refreshedToken = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate(expireTime))
                    .signWith(SignatureAlgorithm.HS512, SECRET)
                    .compact();
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    @Override
    public String generateToken(String username, String expireTime) {
        String audience = generateAudience();
        return Jwts.builder()
                .setIssuer("APP_NAME")
                .setSubject(username)
                .setAudience(audience)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(expireTime))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    private String generateAudience() {
        String audience = AUDIENCEWEB;
        return audience;
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


    private Date generateExpirationDate(String expireTime) {
        int expire = new Integer(expireTime).intValue();
        return new Date(System.currentTimeMillis() + expire * 1000);
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);

        return (
                username != null &&
                        username.equals(userDetails.getUsername())
        );
    }

    @Override
    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith(HEADERBEAR)) {
            return authHeader.substring(7);
        }
        return null;
    }

    private String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTHHEADERAUTHORIZATION);
    }

    @Override
    public void deleteToken(String token) {
        refreshToken(token, TOKENEXPIERD);
    }


}