package pl.markopolo.matchpredictor.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.markopolo.matchpredictor.models.Response;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.markopolo.matchpredictor.security.SecurityConstant.ACCESS_TOKEN_EXPIRES_TIME;
import static pl.markopolo.matchpredictor.security.SecurityConstant.getSecretKey;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            MyUserDetails credentials = new ObjectMapper().readValue(request.getInputStream(), MyUserDetails.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(), credentials.getPassword()));

        } catch (IOException e) {
            log.info("IOException occurred while getting credentials: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        MyUserDetails user = (MyUserDetails) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(getSecretKey());

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("roles", user.getAuthorities().stream()
                        .map((GrantedAuthority::getAuthority)).collect(Collectors.toList()))
                .withExpiresAt(ACCESS_TOKEN_EXPIRES_TIME)
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        Response customResponse = Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Retrieved access token")
                .data(Map.of("access_token", accessToken))
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().registerModule(new JavaTimeModule())
                .writeValue(response.getOutputStream(), customResponse);
    }
}
