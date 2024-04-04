package com.example.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusCheckController {

    @GetMapping
    public ResponseEntity<String> serverStatusCheck() {
        return ResponseEntity.ok("ok");
    }
}


//서버 상태를 확인하는 친구