package com.board.wanted.user.controller;

import com.board.wanted._core.util.ApiResponse;
import com.board.wanted.user.dto.UserRequest;
import com.board.wanted.user.service.UserService;
import com.board.wanted.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse.Result<User>> signup(@RequestBody @Valid UserRequest.UserDTO userDTO, Errors errors) {

        userService.saveUser(userDTO);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse.Result<String>> signIn(@RequestBody @Valid UserRequest.SignInDTO signInDTO, Errors errors) {

        String jwt = userService.signIn(signInDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);

        log.info("{}", ResponseEntity.ok().headers(headers).body(null));

        return ResponseEntity.ok().headers(headers).body(null);
    }
}