package freelance.new_syria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import freelance.new_syria.entities.Registration;
import freelance.new_syria.services.registration.RegistrationService;

@RestController
@RequestMapping(path = "api/v1/registration")
public class RegistrationController {
	
	
	private final RegistrationService service;
	
	public RegistrationController(RegistrationService service) {
		super();
		this.service = service;
	}
	
	@PostMapping()
	public ResponseEntity<String>register(@RequestBody Registration request){
		System.out.println(request.getEmail());
		String msg= this.service.register(request);
		return new ResponseEntity<String>(msg,HttpStatus.ACCEPTED);
	}
	@GetMapping(path = "confirm")
	public String confirm(@RequestParam("token")String token) {
		System.out.println("it is called");
		return this.service.confirm(token);
	}
	@GetMapping()
	public String hello() {
		return "hello";
	}
}
