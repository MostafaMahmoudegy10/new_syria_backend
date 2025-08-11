package freelance.new_syria.entities.security.user;

import freelance.new_syria.entities.security.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor 
@Entity
@Table(name = "users")  
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_name")    
    private String userName;

    @Column(name = "password")    
    private String password;

    @Column(name = "email", unique = true)    
    private String email;
    
    @Column(name = "enabled")
    private boolean isEnabled=false;
    
    @Column(name = "locked")
    private boolean isLocked=false;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private Role userRole;

    public User(String userName, String password, String email, Role userRole) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }
}
