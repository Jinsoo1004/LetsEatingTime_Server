package com.example.let;

import com.example.let.domain.TokenInfo;
import com.example.let.exception.GlobalException;
import com.example.let.module.RandomStringGenerator;
import com.example.let.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JwtTokenProvider {
    private final Key accessKey;
    private final Key refreshKey;

    public JwtTokenProvider(@Value("${jwt.access.secret}") String accessSecretKey,
                            @Value("${jwt.refresh.secret}") String refreshSecretKey) {
        byte[] accessKeyBytes = Decoders.BASE64.decode(accessSecretKey);
        byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshSecretKey);
        this.accessKey = Keys.hmacShaKeyFor(accessKeyBytes);
        this.refreshKey = Keys.hmacShaKeyFor(refreshKeyBytes);
    }

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public TokenInfo generateToken(Authentication authentication, String refreshCode) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + (60000 * 30           * 2 * 24));  // 30분 + 하루
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("code", refreshCode)
                .setExpiration(new Date(now + (60000 * 60 * 24 * 7)))   // 1주
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = getAccessAllClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        // UserDetails 객체를 만들어서 Authentication 리턴
        new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);
        UserDetails principal = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(accessKey).parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new GlobalException(HttpStatus.UNAUTHORIZED, "Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            throw new GlobalException(HttpStatus.UNAUTHORIZED, "Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            throw new GlobalException(HttpStatus.UNAUTHORIZED, "Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            throw new GlobalException(HttpStatus.UNAUTHORIZED, "JWT claims string is empty.");
        }
    }
    // refresh 토큰 정보를 검증하는 메서드
    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser().setSigningKey(refreshKey).parseClaimsJws(token).getBody();
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }

    /**
     * refresh 토큰의 Claim 디코딩
     */
    private Claims getRefreshAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(refreshKey)
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * access 토큰의 Claim 디코딩
     */
    private Claims getAccessAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(accessKey)
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * refresh token의 Claim 에서 sub 가져오기
     */
    public String getRefreshSubFromToken(String token) {
        log.info("ref");
        log.info(token);
        log.info(getRefreshAllClaims(token));
        log.info(getRefreshAllClaims(token).getSubject());
        String username = getRefreshAllClaims(token).getSubject();
        return username;
    }

    /**
     * access token의 Claim 에서 sub 가져오기
     */
    public String getAccessSubFromToken(String token) {
        log.info("acc");
        log.info(token);
        log.info(getAccessAllClaims(token).getSubject());
        String username = getAccessAllClaims(token).getSubject();
        return username;
    }

    /**
     * refresh token의 Claim 에서 code 가져오기
     */
    public String getRefreshCodeFromToken(String token) {
        String code = String.valueOf(getRefreshAllClaims(token).get("code"));
        return code;
    }
}