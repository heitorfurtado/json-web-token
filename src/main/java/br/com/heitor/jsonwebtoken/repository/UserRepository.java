package br.com.heitor.jsonwebtoken.repository;

import br.com.heitor.jsonwebtoken.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserByNameIgnoreCase(String nome);

}
