package br.weg.net.security.repository;

import br.weg.net.security.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    User findByUsernameDetails_Username(String username);
}
