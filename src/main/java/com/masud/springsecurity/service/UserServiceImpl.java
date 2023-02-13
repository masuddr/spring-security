package com.masud.springsecurity.service;

import com.masud.springsecurity.domain.Role;
import com.masud.springsecurity.domain.UserEntity;
import com.masud.springsecurity.repo.RoleRepo;
import com.masud.springsecurity.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {


    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username);
        if(user == null)
        {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found in database");
        }else{
            log.info("user found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(user.getUsername(),user.getPassword(), authorities);
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        log.info("saving user: {} to db", userEntity.getName());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepo.save(userEntity);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving role: {} to db",role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding role to user");
        UserEntity userEntity = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        userEntity.getRoles().add(role);
    }

    @Override
    public UserEntity getUser(String username) {
        log.info("fetching user: {}",username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<UserEntity> getUsers() {
        log.info("fetching all users");
        return userRepo.findAll();
    }


}
