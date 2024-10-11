package com.systex.day1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.systex.day1.model.User;
import com.systex.day1.repository.UserRepository;
import com.systex.day1.sercurity.PasswordBCrypt;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public void registerUser(String username, String password) {
        // 1. 加密密碼
        String encodedPassword = PasswordBCrypt.encryptPassword(password);
        
        // 2. 新增用戶到資料庫
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        
        // 3. 保存用戶到資料庫
        userRepository.save(user);
    }
}
