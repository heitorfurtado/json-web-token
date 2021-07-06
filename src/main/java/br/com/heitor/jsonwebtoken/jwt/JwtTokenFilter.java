package br.com.heitor.jsonwebtoken.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtAuthenticationService jwtConfig = new JwtAuthenticationService();

    private final AuthenticationManager authenticationManager;

    public JwtTokenFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(jwtConfig.getHeader());
        if(Objects.isNull(header)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtConfig.getTokenFromHeader(header);

        try {
        Claims body = Jwts.parser().setSigningKey(jwtConfig.getTokenSecretKey()).parseClaimsJws(token).getBody();

        String user = body.getSubject();

        List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");

        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                .map(m -> new SimpleGrantedAuthority(m.get("authority"))).collect(Collectors.toSet());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, simpleGrantedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            throw new IllegalStateException((String.format("Token %s cannot be trusted", token)));
        }

        filterChain.doFilter(request, response);

    }
}
