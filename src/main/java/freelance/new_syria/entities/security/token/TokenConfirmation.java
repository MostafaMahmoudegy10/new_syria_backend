package freelance.new_syria.entities.security.token;

import java.time.LocalDateTime;

import freelance.new_syria.entities.security.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "token")
@Setter
@Getter
@NoArgsConstructor
public class TokenConfirmation {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id; 
	@Column(nullable = false)
	private String token;
	@Column(nullable = false)
	private LocalDateTime createdAt;
	@Column(nullable = false)
	private LocalDateTime expiresAt;
	private LocalDateTime confirmedAt;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(nullable = false,
		name="user_id"
			)
	private User user;

	public TokenConfirmation(String token, LocalDateTime createdAt, LocalDateTime expiresAt,
			User user) {
		super();
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.user = user;
	}
	
	
	


}
