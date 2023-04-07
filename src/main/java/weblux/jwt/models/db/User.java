package weblux.jwt.models.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("users")
public class User {

    @Id
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String nickname;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    private String telephone;
    @Column("registration_date")
    private LocalDateTime registrationDate;
    private boolean status;
    private boolean visibility;
    private String role;

    public User(){}

    public User(String username, String password, LocalDateTime registrationDate, boolean status, boolean visibility, String role) {
        this.username = username;
        this.password = password;
        this.registrationDate = registrationDate;
        this.status = status;
        this.visibility = visibility;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isStatus() {
        return status;
    }

    public String getRole() {
        return role;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTelephone() {
        return telephone;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public boolean isVisibility() {
        return visibility;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
