package com.example.dev_nav.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Table(name = "articles")
@NoArgsConstructor
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String slug;
    private String title;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String userEmail;
    private String authorName;  // ←追加
    private String category;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String imageUrl;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "is_published", nullable = false)
    private boolean published = true;

    public boolean published() {
        return this.published;
    }
}
