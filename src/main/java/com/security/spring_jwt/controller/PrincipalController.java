package com.security.spring_jwt.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.spring_jwt.DTO.CreateUserDTO;
import com.security.spring_jwt.Entity.ERole;
import com.security.spring_jwt.Entity.RoleEntity;
import com.security.spring_jwt.Entity.UserEntity;
import com.security.spring_jwt.Repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
public class PrincipalController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World Not Secured";
    }

    @GetMapping("/hello-secured")
    public String helloSecured() {
        return "Hello World Secured";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {

        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();

        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);

    }

    @DeleteMapping("/delete-user")
  
    public String deleteUser(@RequestParam String id) {
        userRepository.deleteById(Long.parseLong(id));
        return "Se ha eliminado el usuario con id " + id;
    }

}
