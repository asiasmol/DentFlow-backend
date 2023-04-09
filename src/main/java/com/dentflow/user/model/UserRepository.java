package com.dentflow.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
//    @Query("SELECT u FROM clinics c JOIN c.personnel u JOIN c.owner o WHERE o.email = :email")
//    Set<User> findAllByEmail(String email);
}
