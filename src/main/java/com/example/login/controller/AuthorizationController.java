package com.example.login.controller;

import com.example.login.dto.MemberDTO;
import com.example.login.service.RegisterMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final RegisterMemberService registerMemberService;

    public AuthorizationController(RegisterMemberService registerMemberService) {
        this.registerMemberService = registerMemberService;
    }


    @PostMapping("/join")
    public ResponseEntity<String> join(MemberDTO dto, @RequestParam("file") MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

            Path filepath = Paths.get(projectPath, filename);
            Files.copy(file.getInputStream(), filepath);
            registerMemberService.join(dto.getId(), dto.getPw(), dto.getName(), dto.getEmail(), filename, filepath.toString());
            return ResponseEntity.ok("join success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

//회원가입을 처리하는 애