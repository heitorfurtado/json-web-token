package br.com.heitor.jsonwebtoken;

import br.com.heitor.jsonwebtoken.auth.CustomUserDetails;
import br.com.heitor.jsonwebtoken.auth.CustomUserDetailsService;
import br.com.heitor.jsonwebtoken.model.RoleEntity;
import br.com.heitor.jsonwebtoken.model.UserEntity;
import br.com.heitor.jsonwebtoken.repository.UserRepository;
import br.com.heitor.jsonwebtoken.security.PasswordConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
public class IntegrationJpaUserTest {

    @InjectMocks
    private CustomUserDetailsService service;

    @Mock
    private UserRepository repository;

    PasswordConfig passwordConfig = new PasswordConfig();

    @Test
    public void testValidUserLoadUserByUsername() {

        RoleEntity role_admin = RoleEntity.builder().id(1L).role("ADMIN").build();
        UserEntity michael = UserEntity.builder().id(1L).name("Michael").password(passwordConfig.passwordEncoder().encode("12345")).build();
        role_admin.setUsers(Set.of(michael));
        michael.setRoles(Set.of(role_admin));
        CustomUserDetails michaelUserDetails = new CustomUserDetails(michael);

        Mockito.when(repository.findUserByNameIgnoreCase(Mockito.any())).thenReturn(Optional.of(michael));

        UserDetails result = service.loadUserByUsername("michael");

        Assert.assertEquals(result, michaelUserDetails);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testUserLoadUserByUsernameNotFound() {

        UserDetails result = service.loadUserByUsername("john");

    }

}
