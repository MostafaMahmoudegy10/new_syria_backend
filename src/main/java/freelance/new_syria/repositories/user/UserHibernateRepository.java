package freelance.new_syria.repositories.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import freelance.new_syria.entities.security.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public class UserHibernateRepository {

    @Autowired
    private EntityManager manager;

    public User findUserByEmail(String email) {
        String query = "SELECT u FROM User u WHERE u.email = :email";
        List<User> results = manager
            .createQuery(query, User.class)
            .setParameter("email", email)
            .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }
    public User save(User user) {
    	return this.manager.merge(user);
    }
    @Transactional
    public void enableUser(String email){
    	String query="""
    			update User u
    			set u.isEnabled=true
    			where u.email=:email
    			""";
    	this.manager.createQuery(query).setParameter("email", email).executeUpdate();
  
    }
}
