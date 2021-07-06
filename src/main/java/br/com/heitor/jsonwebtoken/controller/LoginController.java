package br.com.heitor.jsonwebtoken.controller;

import br.com.heitor.jsonwebtoken.auth.CustomUserDetails;
import br.com.heitor.jsonwebtoken.jwt.JwtAuthenticationService;
import br.com.heitor.jsonwebtoken.model.dto.LoginRequestDTO;
import br.com.heitor.jsonwebtoken.model.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/logina")
public class LoginController {

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJwtToken(HttpServletResponse response, HttpServletRequest request, @RequestBody @Valid LoginRequestDTO dto) {

//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            dto.getName(), dto.getPassword()
//                    )
//            );
//
//            UserEntity userEntity = (UserEntity) authentication.getPrincipal();
//
//            return ResponseEntity.ok().header(
//                    HttpHeaders.AUTHORIZATION,
//                    jwtAuthenticationService.generateToken(authentication)
//            ).body("");
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
        return null;
    }
}
