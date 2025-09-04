package com.example.BlogApplication.Controller;

import com.example.BlogApplication.Dto.PostResponseDto;
import com.example.BlogApplication.Dto.UserResponseDto;
import com.example.BlogApplication.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers()
    {
        return  ResponseEntity.ok(adminService.getAllUsers());
   }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id)
    {
        adminService.deleteUser(id);
        return ResponseEntity.ok("User Deleted SuccesFully");
    }
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>>getAllPosts()
    {
        return ResponseEntity.ok(adminService.getPosts());
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletepost(@PathVariable Long id)
    {
        adminService.deletePost(id);
        return ResponseEntity.ok("Post Deleted Successfully");
    }



}
