package freelance.new_syria.services.registration;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import freelance.new_syria.email.EmailSender;
import freelance.new_syria.entities.Registration;
import freelance.new_syria.entities.security.role.Role;
import freelance.new_syria.entities.security.token.TokenConfirmation;
import freelance.new_syria.entities.security.user.User;
import freelance.new_syria.repositories.token.TokenHibrtnateRepository;
import freelance.new_syria.services.token.TokenConfirmationService;
import freelance.new_syria.services.user.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {

	private final UserService service;
	private final EmailValidator validator;
	private final TokenConfirmationService tokenService;
	private final UserService userService;
	private final EmailSender emailSender;
	
	public String register(Registration request) {
		System.out.println(request.getEmail());
		boolean isValidateEmail = this.validator.test(request.getEmail());	
		if(!isValidateEmail) {
			throw new IllegalStateException("THE EMAIL IN NOT VALIDATED");
		}
		String token= this.service.signUp(new User(request.getUserName(), request.getPassword(), request.getEmail(), Role.USER));
			
		
		String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
		emailSender.send(request.getEmail(),buildEmail(request.getUserName(),link) );
		
		return token;
	}

	public String confirm(String token) {
		System.out.println(token);
		TokenConfirmation tokenFounded = this.tokenService.findByToken(token);
		if(tokenFounded==null) {
			throw new IllegalStateException("token not found");
		}
		if(tokenFounded.getConfirmedAt()!=null) {
			throw new IllegalStateException("eamil already confirmed");
		}
		LocalDateTime expiresAt= tokenFounded.getExpiresAt();
		if(expiresAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("token expired");
		}
		this.tokenService.updateTokenConfirmation(token, LocalDateTime.now());
		this.userService.enableAppUser(tokenFounded.getUser().getEmail());
		
		return "confirmed";
	}
	
	
	private String buildEmail(String name, String link) {
	    return """
	        <!DOCTYPE html>
	        <html>
	        <head>
	            <meta charset="UTF-8">
	            <meta name="viewport" content="width=device-width, initial-scale=1.0">
	            <style>
	                body {
	                    font-family: Arial, Helvetica, sans-serif;
	                    background-color: #f4f4f4;
	                    margin: 0;
	                    padding: 0;
	                }
	                .container {
	                    max-width: 580px;
	                    margin: auto;
	                    background: #ffffff;
	                    padding: 20px;
	                    border-radius: 8px;
	                    box-shadow: 0 0 10px rgba(0,0,0,0.1);
	                }
	                h1 {
	                    color: #0b0c0c;
	                    font-size: 22px;
	                }
	                p {
	                    color: #0b0c0c;
	                    font-size: 16px;
	                    line-height: 1.5;
	                }
	                .btn {
	                    display: inline-block;
	                    padding: 12px 24px;
	                    margin-top: 20px;
	                    background-color: #1D70B8;
	                    color: #ffffff;
	                    text-decoration: none;
	                    border-radius: 5px;
	                    font-size: 16px;
	                }
	                .footer {
	                    margin-top: 20px;
	                    font-size: 12px;
	                    color: #888888;
	                    text-align: center;
	                }
	            </style>
	        </head>
	        <body>
	            <div class="container">
	                <h1>Confirm your email</h1>
	                <p>Hi %s,</p>
	                <p>Thank you for registering. Please click the button below to activate your account:</p>
	                <a href="%s" class="btn">Activate Now </a>
	                <p>This link will expire in <b>15 minutes</b>.</p>
	                <p>See you soon!</p>
	                <div class="footer">
	                    &copy; %d Your Company. All rights reserved.
	                </div>
	            </div>
	        </body>
	        </html>
	    """.formatted(name, link, java.time.Year.now().getValue());
	}

}
