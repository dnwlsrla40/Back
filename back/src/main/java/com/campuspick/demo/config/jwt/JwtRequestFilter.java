package com.campuspick.demo.config.jwt;

import com.campuspick.demo.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.access.header}")
    private String accessHeader;
    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        String accessTokenHeader = request.getHeader(accessHeader);
        String refreshTokenHeader = request.getHeader(refreshHeader);
        System.out.println("accessTokenHeader" + accessTokenHeader);
        System.out.println("refreshTokenHeader" + refreshTokenHeader);

        String username = null;
        String accessToken = null;
        String refreshToken;

        // header에 Token 있는 지 확인
        if(accessTokenHeader != null){
            accessToken = accessTokenHeader;
            try{
                username = jwtTokenUtil.getUsernameFromToken(accessToken);
            } catch (IllegalArgumentException e){
                logger.warn("Unable to get JWT Token!!!");
            } catch (ExpiredJwtException e){
                logger.warn("JWT Access Token has Expired!!!");
            }
            if(refreshTokenHeader != null){
                // refreshToken 확인
                ValueOperations<String, String> vop = redisTemplate.opsForValue();
                refreshToken = vop.get("refreshToken:"+username);

                // refreshToken 만료 확인
                if(!jwtTokenUtil.isTokenExpired(refreshToken)){
                    // accessToken 만기 시간 연장
                    jwtTokenUtil.updateTokenExpired(accessToken);
                    logger.info("Access Token Expire Time is update!!!");
                }else{
                    // 만료가 되었으면 redis에 refresh 토큰 삭제
                    redisTemplate.delete("refreshToken"+username);
                    logger.info("this Refresh Token is Expired!!!");
                }
            }
        } else{
            logger.warn("JWT Access Token does not Exist!!!");
        }

        // accessToken 만료 안됐고, username이 존재하면 token 정보로 인증 객체 생성
        if(username != null && !jwtTokenUtil.isTokenExpired(accessToken)){
            // 인증받을 객체 parsing
            Authentication authentication = getAuthentication(accessToken);
            //security 인증
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else{
            logger.info("this Access Token is Expired!!!");
        }
        logger.info("CONTEXT : " + SecurityContextHolder.getContext().getAuthentication());

        filterChain.doFilter(request,response);
    }

    private Authentication getAuthentication(String jwtToken) {
        Map<String, Object> parseInfo = jwtTokenUtil.getUserParseInfo(jwtToken);
//        System.out.println("parseinfo: " + parseInfo);
//        List<String> rs =(List)parseInfo.get("role");
//        Collection<GrantedAuthority> tmp= new ArrayList<>();
//        for (String a: rs) {
//            tmp.add(new SimpleGrantedAuthority(a));
//        }
//        UserDetails userDetails = User.builder().username(String.valueOf(parseInfo.get("username"))).authorities(tmp).build();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                String.valueOf(parseInfo.get("username")), null, AuthorityUtils.createAuthorityList("ROLE_USER"));
        return usernamePasswordAuthenticationToken;
    }
}
