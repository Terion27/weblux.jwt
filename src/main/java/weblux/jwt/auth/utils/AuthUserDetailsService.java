package weblux.jwt.auth.utils;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import weblux.jwt.auth.services.AccessDbService;

@Component
public class AuthUserDetailsService implements ReactiveUserDetailsService {

    private final AccessDbService accessDbService;

    public AuthUserDetailsService(AccessDbService accessDbService) {
        this.accessDbService = accessDbService;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return accessDbService.userDetailsByUsername(username).cast(UserDetails.class);
    }

}
