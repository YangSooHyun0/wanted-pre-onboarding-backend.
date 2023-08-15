package com.board.wanted.user.service;

import com.board.wanted._core.errors.ErrorMessage;
import com.board.wanted._core.errors.exception.Exception500;
import com.board.wanted.user.dto.UserRequest;
import com.board.wanted.user.model.User;
import com.board.wanted.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void saveUser(UserRequest.UserDTO userDTO) {
        if (userDTO == null) throw new Exception500(ErrorMessage.EMPTY_DATA_TO_SIGNUP);
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(encodedPassword)
                .build();
        userRepository.save(user);
    }
}