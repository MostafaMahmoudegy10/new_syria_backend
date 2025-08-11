package freelance.new_syria.services.token;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import freelance.new_syria.entities.security.token.TokenConfirmation;
import freelance.new_syria.repositories.token.TokenHibrtnateRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenConfirmationService {

	private final TokenHibrtnateRepository tokenRepository;

	public TokenConfirmation save(TokenConfirmation token) {
		TokenConfirmation savedToken = this.tokenRepository.save(token);
		return savedToken;
	}
	public TokenConfirmation findByToken(String token) {
		return this.tokenRepository.findByToken(token);
	}
	
	public int updateTokenConfirmation(String token,LocalDateTime dateTime) {
		return this.tokenRepository.tokenUpdateConfirmation(token, dateTime);
	}
}
