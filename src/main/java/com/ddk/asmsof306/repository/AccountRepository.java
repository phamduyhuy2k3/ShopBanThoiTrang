package com.ddk.asmsof306.repository;

import com.ddk.asmsof306.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
//    @Query("SELECT DISTINCT ar.account  FROM Authority ar WHERE ar.role IN ('ADMIN', 'STAFF')")
//    List<Account> getAdministrators();

    @Query("SELECT a from Account a where a.username = ?1 or a.email = ?1")
    Optional<Account> findByUsernameOrEmail(String s);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
