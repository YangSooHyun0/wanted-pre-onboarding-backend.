package com.board.wanted.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRequest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    @Getter
    public static class UserDTO {

        @NotBlank
        @Email(regexp = ".*@.*", message = "유효하지 않은 이메일 형식입니다.")
        private String email;

        @NotBlank
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        private String password;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    @Getter
    public static class SignInDTO {

        @NotBlank
        @Email(regexp = ".*@.*", message = "유효하지 않은 이메일 형식입니다.")
        private String email;

        @NotBlank
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        private String password;
    }
}
