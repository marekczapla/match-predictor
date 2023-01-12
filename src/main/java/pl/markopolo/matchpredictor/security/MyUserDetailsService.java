package pl.markopolo.matchpredictor.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.markopolo.matchpredictor.UserRepository;
import pl.markopolo.matchpredictor.models.User;

@RequiredArgsConstructor
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(username + " not found"));

        return new MyUserDetails(user);
    }
}
