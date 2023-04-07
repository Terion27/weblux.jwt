package weblux.jwt.auth.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import weblux.jwt.auth.utils.AuthUserDetailsService;
import weblux.jwt.auth.utils.JwtService;
import weblux.jwt.models.auth.req_resp.AuthReqResp;
import weblux.jwt.models.auth.dto.UserRegLoginDto;

@Service
public class AuthService {
    private final AuthUserDetailsService authUserDetailsService;
    private final AccessDbService accessDbService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AuthUserDetailsService authUserDetailsService, AccessDbService accessDbService,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authUserDetailsService = authUserDetailsService;
        this.accessDbService = accessDbService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Mono<ResponseEntity<AuthReqResp<String>>> login(UserRegLoginDto userLogin) {
        if (!(userLogin.getPassword().isEmpty() || userLogin.getUsername().isEmpty())) {
            return authUserDetailsService
                    .findByUsername(userLogin.getUsername().toLowerCase())
                    .filter(u -> u.getUsername().equals(userLogin.getUsername()) &&
                            passwordEncoder.matches(userLogin.getPassword(), u.getPassword()))
                    .filter(UserDetails::isEnabled)  // is Status optional
                    .map(u -> ResponseEntity.ok()
                            .header(HttpHeaders.AUTHORIZATION, jwtService.generate(u.getUsername()))
                            .body(new AuthReqResp<>("", "Success")))
                    .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new AuthReqResp<>("", "Credentials are incorrect")));
        }
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AuthReqResp<>("", "Fields must not be empty")));
    }

    public Mono<ResponseEntity<AuthReqResp<String>>> reg(UserRegLoginDto userReg) {
        if (!(userReg.getUsername().isEmpty() || userReg.getPassword().isEmpty())) {
            return accessDbService.createUser(userReg)
                    .map(u -> {
                        if (u.getId() == 0)
                            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                                    .body(new AuthReqResp<>("", "Login is incorrect"));
                        return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new AuthReqResp<>("OK", "User " + u.getId() + " is registered"));
                    })
                    .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new AuthReqResp<>("", "BAD_REQUEST")));
        }
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AuthReqResp<>("", "Fields must not be empty")));
    }
}