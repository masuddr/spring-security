package com.masud.springsecurity.service;

import com.masud.springsecurity.domain.Role;
import com.masud.springsecurity.domain.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity saveUser(UserEntity userEntity);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    UserEntity getUser(String username);
    List<UserEntity> getUsers();
}
