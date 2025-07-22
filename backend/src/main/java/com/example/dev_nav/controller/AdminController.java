package com.example.dev_nav.controller;

import com.example.dev_nav.dto.ArticleDTO;
import com.example.dev_nav.dto.request.ArticleRequest;
import com.example.dev_nav.service.AdminService;
import com.example.dev_nav.service.ArticleService;
import com.example.dev_nav.service.FirebaseAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final FirebaseAuthService firebaseAuthService;
    private final ArticleService articleService;
    @GetMapping("/articles") ResponseEntity<Page<ArticleDTO>> getAllArticles(@RequestHeader(name = "Authorization")String token,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10")int size)
    {
        Pageable pageable = PageRequest.of(page,size);
        String adminEmail = firebaseAuthService.verifyAdminAndGetEmail(token);
        Page<ArticleDTO> articles = articleService.findAllArticles(adminEmail, pageable);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@RequestHeader(name = "Authorization")String token,
                                                     @PathVariable Long id)
    {
        String adminEmail = firebaseAuthService.verifyAdminAndGetEmail(token);
        ArticleDTO article = adminService.findById(adminEmail,id);
        return ResponseEntity.ok(article);
    }

    @PostMapping("/add-article")
    public ResponseEntity<?> postArticle(@RequestHeader(name = "Authorization")String token,
                                         @RequestParam("image") MultipartFile imageFile,
                                         @ModelAttribute ArticleRequest request)
    {

        //1. 管理者認証＆メール取得
        String adminEmail = firebaseAuthService.verifyAdminAndGetEmail(token);
        // 2. 保存ディレクトリの準備
        // uploadsはプロジェクトのカレントディレクトリ直下で作成される
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";
        File dir = new File(uploadDir);
        if (!dir.exists() && !dir.mkdirs()) {
            return ResponseEntity.status(500).body("ディレクトリ作成に失敗しました");
        }
        // 3. 画像ファイル名の決定
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        File dest = new File(dir, fileName);
        // 4. 画像ファイルの保存
        try {
            imageFile.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("画像保存エラー");
        }

        // 5. 公開用画像URLの生成（実運用ではNginxや静的公開パスでマッピングを意識）
        String imageUrl = "/uploads/" + fileName;

        // 6. サービス層に投稿処理を依頼（imageUrlを渡す）
        adminService.postArticle(adminEmail, request, imageUrl);



        return ResponseEntity.ok("投稿完了");

    }
    @PutMapping("/articles/{id}")
    public ResponseEntity<?> putArticle(@RequestHeader(name = "Authorization")String token,
                                         @RequestParam("image") MultipartFile imageFile,
                                         @PathVariable Long id,
                                         @ModelAttribute ArticleRequest request)
    {

        //1. 管理者認証＆メール取得
        String adminEmail = firebaseAuthService.verifyAdminAndGetEmail(token);
        // 2. 保存ディレクトリの準備
        // uploadsはプロジェクトのカレントディレクトリ直下で作成される
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";
        File dir = new File(uploadDir);
        if (!dir.exists() && !dir.mkdirs()) {
            return ResponseEntity.status(500).body("ディレクトリ作成に失敗しました");
        }
        // 3. 画像ファイル名の決定
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        File dest = new File(dir, fileName);
        // 4. 画像ファイルの保存
        try {
            imageFile.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("画像保存エラー");
        }

        // 5. 公開用画像URLの生成（実運用ではNginxや静的公開パスでマッピングを意識）
        String imageUrl = "/uploads/" + fileName;

        // 6. サービス層に投稿処理を依頼（imageUrlを渡す）
        adminService.putArticle(adminEmail,id, request, imageUrl);

        return ResponseEntity.ok("更新完了");

    }
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<?> deleteArticle(@RequestHeader(name = "Authorization") String token,
                                           @PathVariable Long id)
    {
        String adminEmail = firebaseAuthService.verifyAdminAndGetEmail(token);
        adminService.deleteArticle(id);
        return  ResponseEntity.ok("削除完了");
    }
    @PutMapping("/articles/toggle/{id}")
    public ResponseEntity<?> togglePublish(@RequestHeader(name = "Authorization") String token,
                                           @PathVariable Long id)
    {
        String adminEmail = firebaseAuthService.verifyAdminAndGetEmail(token);
        adminService.togglePublish(id);
        return  ResponseEntity.ok("公開状態更新");
    }
}
