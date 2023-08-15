package com.board.wanted.user.service;

import com.board.wanted._core.errors.ErrorMessage;
import com.board.wanted._core.errors.exception.Exception500;
import com.board.wanted._core.security.JwtTokenProvider;
import com.board.wanted._core.security.PrincipalUserDetail;
import com.board.wanted.user.dto.UserRequest;
import com.board.wanted.user.model.User;
import com.board.wanted.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public void saveUser(UserRequest.UserDTO userDTO) {
        if (userDTO == null) throw new Exception500(ErrorMessage.EMPTY_DATA_TO_SIGNUP);
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(encodedPassword)
                .build();
        userRepository.save(user);
    }

    public String signIn(UserRequest.SignInDTO signInDTO) {
        if (signInDTO == null) throw new Exception500(ErrorMessage.EMPTY_DATA_TO_SIGNIN);
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        PrincipalUserDetail userDetail = (PrincipalUserDetail) authentication.getPrincipal();
        final User user = userDetail.getUser();
        return JwtTokenProvider.create(user);
    }
}