package com.trio.challenge.controller;

import com.trio.challenge.dto.WelcomeDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TrioBackendChallengeRESTController {

  @GetMapping
  public ResponseEntity<WelcomeDTO> hello() {
    var welcomeDTO = WelcomeDTO.builder().success(true).message("Hello, World!").build();
    return ResponseEntity.ok(welcomeDTO);
  }
}
