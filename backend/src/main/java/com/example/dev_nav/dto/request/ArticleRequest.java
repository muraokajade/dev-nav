package com.example.dev_nav.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {
    private String slug;
    private String title;
    private String adminEmail;
    private String category;
    private String content;
    private MultipartFile image;
}
