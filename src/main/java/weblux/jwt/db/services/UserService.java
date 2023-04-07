package weblux.jwt.db.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import weblux.jwt.db.repositories.UserRepository;
import weblux.jwt.models.auth.dto.UserAuthDto;
import weblux.jwt.models.auth.dto.UserRegLoginDto;
import weblux.jwt.models.db.User;

import java.time.LocalDateTime;

@Slf4j
@Component
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<UserAuthDto> userLoginByUsername(String username) {
        return userRepository
                .findAuthByUsername(username)
                .map(u -> new UserAuthDto(u.getId(), u.getUsername(), u.getPassword(), u.getRole(), u.isStatus()))
                .switchIfEmpty(Mono.empty());
    }

    public Mono<UserAuthDto> createUser(UserRegLoginDto userReg) {
        return userRepository
                .findAuthByUsername(userReg.getUsername())
                .map(u -> new UserAuthDto(0L, "", "", "", false))
                .switchIfEmpty(userRepository.save(new User(
                                        userReg.getUsername(),
                                        passwordEncoder.encode(userReg.getPassword()),
                                        LocalDateTime.now(),
                                        true,
                                        true,
                                        "ROLE_USER")
                                )
                                .map(u -> new UserAuthDto(u.getId(), u.getUsername(), u.getPassword(), u.getRole(), u.isStatus()))
                                .doOnSuccess(u -> log.info("Created new user with ID = " + u.getId()))
                        // TODO обработать error save
                );
    }
}
