package com.TP.Spring.controller;

import com.TP.Spring.model.Article;
import com.TP.Spring.model.Utilisateur;
import com.TP.Spring.repository.ArticleRepository;
import com.TP.Spring.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<Article> createArticles(@RequestBody List<Article> articles) {
        for (Article article : articles) {
            Utilisateur auteur = article.getAuteur();
            if (auteur != null && auteur.getId() == null) {
                utilisateurRepository.save(auteur);
            }
            article.setDatePublication(LocalDateTime.now());
            articleRepository.save(article);
        }
        return articles;
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id) {
        articleRepository.deleteById(id);
    }
}