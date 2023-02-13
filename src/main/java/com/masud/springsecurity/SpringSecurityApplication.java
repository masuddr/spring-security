package com.masud.springsecurity;

import com.masud.springsecurity.domain.Role;
import com.masud.springsecurity.domain.UserEntity;
import com.masud.springsecurity.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SpringSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Bean
   CommandLineRunner run (UserService userService)
   {
       return args -> {
         userService.saveRole(new Role(null,"ROLE_USER"));
         userService.saveRole(new Role(null,"ROLE_MANAGER"));
         userService.saveRole(new Role(null,"ROLE_ADMIN"));
         userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));

         userService.saveUser(new UserEntity(null,"Jason Todd","RedHood","batmobile",new ArrayList<>()));
         userService.saveUser(new UserEntity(null,"Dick Grayson","NightWing","oldrobin",new ArrayList<>()));
         userService.saveUser(new UserEntity(null,"Raven","Raven","darkness12",new ArrayList<>()));
         userService.saveUser(new UserEntity(null,"Koriandr","StarFire","firewave",new ArrayList<>()));

         userService.addRoleToUser("RedHood","ROLE_ADMIN");
         userService.addRoleToUser("RedHood","ROLE_SUPER_ADMIN");
         userService.addRoleToUser("NightWing","ROLE_SUPER_ADMIN");
         userService.addRoleToUser("StarFire","ROLE_USER");
         userService.addRoleToUser("Raven","ROLE_MANAGER");
       };
   }

   @Bean
    PasswordEncoder passwordEncoder()
   {
       return new BCryptPasswordEncoder();
   }
}
