package weblux.jwt.db.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import weblux.jwt.models.db.User;


public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    // Authentication Login
    @Query("""
                SELECT users.id, users.username, users.password, users.role, users.status
                FROM users WHERE users.username = :username
            """)
    Mono<User> findAuthByUsername(String username);

    //Registration Create
    @Query("""
                SELECT users.username, users.password, users.email, users.role, users.status
                FROM users WHERE (users.username = :username or users.email = :username) limit 1
            """)
    Mono<User> findAuthByUsernameEmail(String username);
}
