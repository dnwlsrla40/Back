package com.campuspick.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_ACCESS_TOKEN_VALIDITY = 24 * 60 * 60; // 하루
//            10* 60; //10분
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 24 * 60 * 60 * 7; //일주일

    @Value("${jwt.secret}")
    private String secret;
    
    // Jwt 토큰 username 검색
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Jwt 토큰 만기일 검색
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    // 형태에 맞는 검색
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    // Jwt 토큰 모든 정보 가져오기
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public void updateTokenExpired(String token){
        final Claims claims = getAllClaimsFromToken(token);
        claims.setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY * 1000));
    }

    // user 정보 가져오기
    public Map<String, Object> getUserParseInfo(String token) {
        Claims parseInfo = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        Map<String, Object> result = new HashMap<>();
        result.put("username", parseInfo.getSubject());
        result.put("role", parseInfo.get("role", List.class));
        return result;
    }

    // 만료 체크
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    // JwtToken 생성
    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
//        List<String> li = new ArrayList<>();
//        for (GrantedAuthority a: userDetails.getAuthorities()) {
//            li.add(a.getAuthority());
//        }
//        claims.put("role",li);
        String accessToken = Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

        return accessToken;
    }

    // refreshToken 생성
    public String generateRefreshToken(String username) {
        String refreshToken = Jwts.builder().setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set("refreshToken:"+username, refreshToken, 3L, TimeUnit.DAYS);
        return refreshToken;
    }

//    // token 유효성 검사
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
}
