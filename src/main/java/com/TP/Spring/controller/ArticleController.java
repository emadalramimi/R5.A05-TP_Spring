package com.TP.Spring.controller;

import com.TP.Spring.model.Article;
import com.TP.Spring.model.Utilisateur;
import com.TP.Spring.repository.ArticleRepository;
import com.TP.Spring.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_WRITER')")
    public ResponseEntity<?> createArticles(@RequestBody List<Article> articles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByNomUtilisateur(username);

        if (utilisateurOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Utilisateur utilisateur = utilisateurOpt.get();

        for (Article article : articles) {
            article.setUtilisateur(utilisateur);
            article.setDatePublication(LocalDateTime.now());
            articleRepository.save(article);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        if (!articleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        articleRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}