package freelance.new_syria.entities.security.role;

import org.springframework.security.core.GrantedAuthority;

public class CustomeGrantedAuthority implements GrantedAuthority{

	private final Role role;
	
	public CustomeGrantedAuthority(Role role) {
		super();
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role.name();
	}

}
