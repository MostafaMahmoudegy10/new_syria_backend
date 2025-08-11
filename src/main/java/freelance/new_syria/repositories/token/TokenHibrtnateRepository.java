package freelance.new_syria.repositories.token;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import freelance.new_syria.entities.security.token.TokenConfirmation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class TokenHibrtnateRepository {

	private final EntityManager manager;
	
	@Transactional
	public TokenConfirmation save(TokenConfirmation token) {
			return this.manager.merge(token);
	}
	
	public TokenConfirmation findByToken(String token) {
		  String query = "select tc from TokenConfirmation tc where tc.token = :token";
		    List<TokenConfirmation> results = manager.createQuery(query, TokenConfirmation.class)
		                                             .setParameter("token", token)
		                                             .getResultList();
		    return results.isEmpty() ? null : results.get(0);
	}
	
	@Transactional
	public int tokenUpdateConfirmation(String token,LocalDateTime dateTime) {
		String query="""
				update TokenConfirmation t
				set t.confirmedAt=:dataTime
				where t.token=:token
				""";
	 int executeUpdate = this.manager.createQuery(query).setParameter("token", token)
		.setParameter("dataTime", dateTime).executeUpdate();
		return executeUpdate;
	}
}
