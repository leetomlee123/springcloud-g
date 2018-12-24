package com.example.user.serviceimpl;

import com.example.user.service.UserTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("userTokenService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserTokenServiceImpl implements UserTokenService {

    public String AUTHHEADERAUTHORIZATION = "Authorization";
    public String AUDIENCEWEB = "web";
    public String SECRET = "leetomlee123";
    public String HEADERBEAR = "Bearer ";
    public String TOKENEXPIERD = "0";

    private static final String PERMISSION_CLAIMS = "permission";

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

    @Override
    public Date getExpireTimeFromToken(String token) {
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
    public String generateToken(String username, List<GrantedAuthority> permission, String expireTime) {
        String audience = generateAudience();
        HashMap<String, Object> map = new HashMap<>();
        map.put(PERMISSION_CLAIMS, permission);
        return Jwts.builder()
                .setIssuer("APP_NAME")
                .setClaims(map)
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


