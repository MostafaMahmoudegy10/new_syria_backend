package freelance.new_syria.entities.security.user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import freelance.new_syria.entities.security.role.CustomeGrantedAuthority;

public class CustomeUserDetils implements UserDetails {

    private final User user;

    public CustomeUserDetils(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        CustomeGrantedAuthority authority = new CustomeGrantedAuthority(user.getUserRole());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked(); 
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; 
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
