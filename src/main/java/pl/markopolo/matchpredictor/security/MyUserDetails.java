package pl.markopolo.matchpredictor.security;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.markopolo.matchpredictor.models.User;

import java.util.Collection;
import java.util.stream.Collectors;

@NoArgsConstructor
public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean isActive;
    private Collection<? extends GrantedAuthority> authorities;

    private final static String ROLE_PREFIX = "ROLE_";

    public MyUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isActive = user.isActive();
        this.authorities = user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(ROLE_PREFIX + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
