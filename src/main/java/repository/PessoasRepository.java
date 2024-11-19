package repository;

import model.Pessoas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;

import java.util.Collections;

@Service
public class PessoasRepository implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Carrega o usuário do banco de dados e converte para UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
             .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singletonList(() -> "ROLE_USER"))
                .build();
    }
    public interface UserRepository extends JpaRepository<User, Long> {
        User findByUsername(String username);
    }

}