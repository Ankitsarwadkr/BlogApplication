package com.example.BlogApplication.Controller;

import com.example.BlogApplication.Dto.UserRequestDto;
import com.example.BlogApplication.Dto.UserResponseDto;
import com.example.BlogApplication.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto dto)
    {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id)
    {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUser()
    {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
     public ResponseEntity<String> deleteUsers(@PathVariable Long id)
    {
        userService.deleteUser(id);
        return  ResponseEntity.ok("User Deleted Succesfully");
    }


}
