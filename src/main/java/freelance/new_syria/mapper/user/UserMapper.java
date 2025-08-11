package freelance.new_syria.mapper.user;

import freelance.new_syria.dto.user.UserDto;
import freelance.new_syria.entities.security.user.User;

public class UserMapper {

	public User toUser(UserDto dto) {
		User user=new User( dto.getUserName(),dto.getPassword(), dto.getEmail(), dto.getUserRole());
		return user;
	}
	public UserDto toUserDto(User user) {
		UserDto userDto=new UserDto( user.getUserName(),user.getPassword(), user.getEmail(), user.getUserRole());
		return userDto;
	}
	
}
