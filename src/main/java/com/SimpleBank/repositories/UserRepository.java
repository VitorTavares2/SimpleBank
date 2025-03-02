package com.SimpleBank.repositories;

import com.SimpleBank.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> FindUserByDocument (String document); //Query on sql, that finds the user by document, optinal means that the user can or cant exists.

    Optional<User> FindUserById (Long id);
}
