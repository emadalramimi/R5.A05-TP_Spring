package com.TP.Spring.controller;

import com.TP.Spring.model.Article;
import com.TP.Spring.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleRepository repository;

    @GetMapping
    public List<Article> getAllArticles() {
        return repository.findAll();
    }

    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        article.setDatePublication(LocalDateTime.now());
        return repository.save(article);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
