package com.TP.Spring.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class JwtDTO {
    private final String token;
    private final String type = "Bearer";
    private final String username;
    private final List<String> roles;
}
