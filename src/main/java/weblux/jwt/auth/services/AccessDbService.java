package weblux.jwt.auth.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import weblux.jwt.db.services.UserService;
import weblux.jwt.models.auth.dto.UserAuthDto;
import weblux.jwt.models.auth.dto.UserRegLoginDto;

@Service
public class AccessDbService {
    private final UserService userService;

    public AccessDbService(UserService userService) {
        this.userService = userService;
    }

    public Mono<UserAuthDto> userDetailsByUsername(String username) {
        return userService.userLoginByUsername(username);
    }

    public Mono<UserAuthDto> createUser(UserRegLoginDto userReg) {
        return userService.createUser(userReg);
    }
}
