package pl.markopolo.matchpredictor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.markopolo.matchpredictor.models.MyUserDetails;
import pl.markopolo.matchpredictor.models.User;

import java.util.Optional;

public class MyUserDataService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);

        user.orElseThrow(() -> new UsernameNotFoundException(userName + " not found"));
        return user.map(MyUserDetails::new).get();
    }

}
