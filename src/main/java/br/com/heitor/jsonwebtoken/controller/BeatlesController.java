package br.com.heitor.jsonwebtoken.controller;

import br.com.heitor.jsonwebtoken.model.entities.RoleEntity;
import br.com.heitor.jsonwebtoken.model.entities.UserEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/beatles")
public class BeatlesController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getBeatles() {
        UserEntity build = UserEntity.builder().build();
        Optional<RoleEntity> any = build.getRoles().stream().filter(roleEntity -> roleEntity.getId() == 1).findAny();
        return ResponseEntity.ok().body(Arrays.asList("John", "Paul", "Ringo", "George"));
    }

}
