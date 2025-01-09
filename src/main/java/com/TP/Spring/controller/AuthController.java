package com.TP.Spring.controller;

import com.TP.Spring.model.Utilisateur;
import com.TP.Spring.repository.UtilisateurRepository;
import com.TP.Spring.security.JwtDTO;
import com.TP.Spring.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getNomUtilisateur(), 
                    loginRequest.getMotDePasse()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenGenerator.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtDTO(
            jwt, 
            userDetails.getUsername(), 
            roles
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        // Check if username already exists
        if (utilisateurRepository.findByNomUtilisateur(signupRequest.getNomUtilisateur()).isPresent()) {
            return ResponseEntity
                .badRequest()
                .body("Error: Username is already taken!");
        }

        // Create new user
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomUtilisateur(signupRequest.getNomUtilisateur());
        utilisateur.setMotDePasse(signupRequest.getMotDePasse());
        utilisateur.setRole(signupRequest.getRole());

        utilisateurRepository.save(utilisateur);

        return ResponseEntity.ok("User registered successfully!");
    }

    // Inner classes for request bodies
    public static class LoginRequest {
        private String nomUtilisateur;
        private String motDePasse;

        public String getNomUtilisateur() { return nomUtilisateur; }
        public void setNomUtilisateur(String nomUtilisateur) { this.nomUtilisateur = nomUtilisateur; }
        public String getMotDePasse() { return motDePasse; }
        public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    }

    public static class SignupRequest {
        private String nomUtilisateur;
        private String motDePasse;
        private String role;

        public String getNomUtilisateur() { return nomUtilisateur; }
        public void setNomUtilisateur(String nomUtilisateur) { this.nomUtilisateur = nomUtilisateur; }
        public String getMotDePasse() { return motDePasse; }
        public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}
