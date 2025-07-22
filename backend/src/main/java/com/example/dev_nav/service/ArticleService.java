package com.example.dev_nav.service;

import com.example.dev_nav.dto.ArticleDTO;
import com.example.dev_nav.entity.ArticleEntity;
import com.example.dev_nav.entity.UserEntity;
import com.example.dev_nav.repository.ArticleRepository;
import com.example.dev_nav.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    public Page<ArticleDTO> findAllArticles(String adminEmail, Pageable pageable) {
        // 1. 全記事をページングで取得
        Page<ArticleEntity> entities = articleRepository.findAll(pageable);
        // 2. 管理者ユーザー情報の取得（管理画面での制御など必要な場合だけ）
        UserEntity user = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません。"));
        return entities.map(this::convertToArticleDTO);
    }

    private ArticleDTO convertToArticleDTO(ArticleEntity entity)
    {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setSlug(entity.getSlug());
        dto.setTitle(entity.getTitle());
        dto.setUserEmail(entity.getUser().getEmail());
        dto.setAuthorName(entity.getAuthorName());
        dto.setCategory(entity.getCategory());
        dto.setContent(entity.getContent());
        dto.setImageUrl(entity.getImageUrl());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setPublished(entity.isPublished());
        return dto;
    }
}
