package freelance.new_syria.services.user;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import freelance.new_syria.entities.Registration;
import freelance.new_syria.entities.security.role.Role;
import freelance.new_syria.entities.security.token.TokenConfirmation;
import freelance.new_syria.entities.security.user.CustomeUserDetils;
import freelance.new_syria.entities.security.user.User;
import freelance.new_syria.repositories.token.TokenHibrtnateRepository;
import freelance.new_syria.repositories.user.UserHibernateRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder encoder;
    private final UserHibernateRepository userRepository;
    private static final String MSG="The email you try to login with %s is not available";
    private final TokenHibrtnateRepository tokenRepository;
    
    
   

    public UserService(PasswordEncoder encoder, UserHibernateRepository userRepository,
    		TokenHibrtnateRepository tokenRepository ) {
		super();
		this.encoder = encoder;
		this.userRepository = userRepository;
		this.tokenRepository=tokenRepository;
	}


	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if(user==null) {
        	throw new UsernameNotFoundException(String.format(MSG,email));
        }
        return new CustomeUserDetils(user); 
    }

	
	@Transactional
	public String signUp(User user) {
		User userExist = this.userRepository.findUserByEmail(user.getEmail());
		if(userExist !=null) {
			throw new IllegalStateException("user with this email is present");
		}
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		User savedUser = this.userRepository.save(user);
		System.out.println(user.getEmail()+" "+user.getUserRole()+" "+user.getUserRole());
		// TODO:WE SHOULD SEND A TOKEN HERE
		String token=UUID.randomUUID().toString();
		TokenConfirmation tokenSending= new TokenConfirmation(
				token,LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15)
				,savedUser
				);
		this.tokenRepository.save(tokenSending);
		// TODO: SENDING AN EMAIL VERFECATION
		
		return token;
	}
	public void enableAppUser(String email){
		this.userRepository.enableUser(email);
	}


}
