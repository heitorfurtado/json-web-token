package br.com.heitor.jsonwebtoken.auth;

import br.com.heitor.jsonwebtoken.model.entities.UserEntity;
import br.com.heitor.jsonwebtoken.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<UserEntity> user = repository.findUserByNameIgnoreCase(s);
        if(!user.isPresent()) {
            throw new UsernameNotFoundException(String.format("User %s not found", s));
        }
        return new CustomUserDetails(user.get());
    }
}
