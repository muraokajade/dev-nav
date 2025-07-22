package com.example.dev_nav.service;

import com.example.dev_nav.dto.ArticleDTO;
import com.example.dev_nav.dto.request.ArticleRequest;
import com.example.dev_nav.entity.ArticleEntity;
import com.example.dev_nav.entity.UserEntity;
import com.example.dev_nav.repository.AdminRepository;
import com.example.dev_nav.repository.ArticleRepository;
import com.example.dev_nav.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    public void postArticle(String adminEmail, ArticleRequest request, String imageURL) {
        // 1. Entityをnew
        ArticleEntity entity = new ArticleEntity();
        // 2. ユーザー取得（adminEmailで検索）
        UserEntity user = userRepository.findByEmail(adminEmail)
                        .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません。"));


        // 3. 各プロパティをセット
        entity.setSlug(request.getSlug());
        entity.setTitle(request.getTitle());
        entity.setUser(user);// リレーションはUserEntity型でセット
        entity.setUserEmail(user.getEmail());
        entity.setAuthorName(user.getDisplayName());
        entity.setCategory(request.getCategory());
        entity.setContent(request.getContent());
        entity.setImageUrl(imageURL);
        entity.setPublished(true);

        articleRepository.save(entity);
        
    }

    public ArticleDTO findById(String adminEmail, Long id) {
        ArticleEntity entity = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("記事が見つかりません。"));

        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setSlug(entity.getSlug());
        dto.setTitle(entity.getTitle());
        dto.setUserEmail(adminEmail);
        dto.setAuthorName(entity.getAuthorName());
        dto.setCategory(entity.getCategory());
        dto.setContent(entity.getContent());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public void putArticle(String adminEmail, Long id, ArticleRequest request, String imageUrl) {
        //1.Articleの存在確認 entity化
        ArticleEntity entity = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("記事が見つかりません。"));

        UserEntity user = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません。"));

        entity.setSlug(request.getSlug());
        entity.setTitle(request.getTitle());
        entity.setUser(user);// リレーションはUserEntity型でセット
        entity.setUserEmail(user.getEmail());
        entity.setAuthorName(user.getDisplayName());
        entity.setCategory(request.getCategory());
        entity.setContent(request.getContent());
        entity.setImageUrl(imageUrl);
        entity.setPublished(true);

        articleRepository.save(entity);

    }

    public void deleteArticle(Long id) {
        ArticleEntity entity = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("記事が見つかりません。"));

        articleRepository.deleteById(id);
    }

    public void togglePublish(Long id) {
        ArticleEntity entity = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("記事が見つかりません。"));

        entity.setPublished(!entity.isPublished());
        articleRepository.save(entity);
    }
}
