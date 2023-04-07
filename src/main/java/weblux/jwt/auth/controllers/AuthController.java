package weblux.jwt.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import weblux.jwt.auth.services.AuthService;
import weblux.jwt.models.auth.req_resp.AuthReqResp;
import weblux.jwt.models.auth.dto.UserRegLoginDto;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/reg")
    public Mono<ResponseEntity<AuthReqResp<String>>> reg(@RequestBody UserRegLoginDto userReg) {
        return authService.reg(userReg);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthReqResp<String>>> login(@RequestBody UserRegLoginDto userLogin) {
        return authService.login(userLogin);
    }
}