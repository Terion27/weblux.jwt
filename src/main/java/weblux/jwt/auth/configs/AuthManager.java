package weblux.jwt.auth.configs;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import weblux.jwt.auth.utils.AuthUserDetailsService;
import weblux.jwt.auth.utils.JwtService;

@Log4j2
@Component
public class AuthManager implements ReactiveAuthenticationManager {
    final JwtService jwtService;
    final AuthUserDetailsService authUserDetailsService;

    public AuthManager(JwtService jwtService, AuthUserDetailsService authUserDetailsService) {
        this.jwtService = jwtService;
        this.authUserDetailsService = authUserDetailsService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication.getCredentials().toString())
                .flatMap(authToken -> authUserDetailsService
                        .findByUsername(jwtService.getUserName(authToken).toLowerCase())
                        .filter(u -> (!u.getUsername().equals("") && (jwtService.isValid(authToken, u.getUsername()))))
                        .flatMap(u -> Mono.just(new UsernamePasswordAuthenticationToken(
                                u.getUsername(),
                                u.getPassword(),
                                u.getAuthorities())))
                        .defaultIfEmpty(new UsernamePasswordAuthenticationToken("", "")));
    }
}
