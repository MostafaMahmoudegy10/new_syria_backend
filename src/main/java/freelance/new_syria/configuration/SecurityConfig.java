package freelance.new_syria.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import freelance.new_syria.services.user.UserService;




@Configuration
public class SecurityConfig {


	    @Bean
	    public PasswordEncoder encoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public DaoAuthenticationProvider authenticationProvider(UserService userService, PasswordEncoder encoder) {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userService);
	        authProvider.setPasswordEncoder(encoder);
	        return authProvider;
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }

	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
	        return security
	                .csrf(csrf -> csrf.disable())
	                .authorizeHttpRequests(r -> r
	                    .requestMatchers("/api/v*/registration/**").permitAll()
	                    .anyRequest().authenticated()
	                )
	                .formLogin().
	                and().httpBasic()
	                .and()
	                .build();
	    }
	}


