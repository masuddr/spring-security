package com.masud.springsecurity.repo;

import com.masud.springsecurity.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity,Long> {

    UserEntity findByUsername(String username);
}
