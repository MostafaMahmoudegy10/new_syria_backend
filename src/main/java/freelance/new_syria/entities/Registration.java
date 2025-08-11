package freelance.new_syria.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Registration {
	private String userName;
	private String password;
	private String email;
}
