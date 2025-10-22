package com.iychay.be.auth.token;

import com.iychay.be.user.model.User;

public interface TokenService {

    String generateToken(User user);
}
