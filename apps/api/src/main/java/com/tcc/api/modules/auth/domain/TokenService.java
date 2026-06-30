package com.tcc.api.modules.auth.domain;

import com.tcc.api.modules.users.domain.User;

public interface TokenService {
    String generateToken(User user);
    String validateToken(String token);
}
