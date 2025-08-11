package freelance.new_syria.dto.user;

import freelance.new_syria.entities.security.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UserDto {
	private String userName;
	private String password;
	private String email;
	private Role userRole; 

}
