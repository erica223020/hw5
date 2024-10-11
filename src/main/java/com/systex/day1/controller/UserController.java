package com.systex.day1.controller;

import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.systex.day1.model.User;
import com.systex.day1.sercurity.PasswordBCrypt;
import com.systex.day1.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    private UserService  userService ;

    private static final Pattern VALID_USERNAME_PASSWORD_REGEX = Pattern.compile("^[a-zA-Z0-9]{6,10}$");
    
    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, String username, String password, Model model) {
        User user = userService.findByUsername(username);
        if (user == null || !PasswordBCrypt.matchesPassword(password, user.getPassword())) {
            model.addAttribute("error", "帳號或密碼錯誤");
            return "login";
        }
        request.getSession().setAttribute("user", user);
        return "redirect:/lottery";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(String username, String password, Model model) {
        //1.檢查是否有重複的帳號
        if (userService .findByUsername(username) != null) {
            model.addAttribute("error", "帳號已存在");
            return "register";
        }
        
        userService.registerUser(username,password);

        return "redirect:/login";
    }
    
    @PostMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        //無效化session
        request.getSession().invalidate();
        return "redirect:/ajaxlogin";
    }

    
    @GetMapping("/lottery")
    public String showLotteryPage(HttpServletRequest request, Model model) {
        // 看一下使用者怎麼稱呼
    	User user = (User) request.getSession().getAttribute("user");
        
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        
        return "lottery"; 
    }
    
    @GetMapping("/ajaxlogin")
    public String showAjaxLoginPage() {
        return "ajaxlogin";
    }
    
    @GetMapping("/ajaxregister")
    public String showAjaxRegisterPage() {
        return "ajaxregister";
    }
    
    // Ajax處理的部分
    @PostMapping("/ajaxlogin")
    @ResponseBody
    public ResponseEntity<?> ajaxLogin(@RequestBody Map<String, String> loginRequest, HttpServletRequest request) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // 檢查帳密是否符合規定
        if (!VALID_USERNAME_PASSWORD_REGEX.matcher(username).matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("帳號必須由6~10個字母與數字組成!");
        }

        if (!VALID_USERNAME_PASSWORD_REGEX.matcher(password).matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密碼必須由6~10個字母與數字組成!");
        }
        
        User user = userService.findByUsername(username);
        if (user == null || !PasswordBCrypt.matchesPassword(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帳號或密碼錯誤");
        }

        // 設定 session
        request.getSession().setAttribute("user", user);

        // 返回成功的 JSON 回應
        return ResponseEntity.ok().body("登入成功");
    }
    @PostMapping("/ajaxregister")
    @ResponseBody
    public ResponseEntity<?> ajaxRegister(@RequestBody Map<String, String> registerRequest) {
        String username = registerRequest.get("username");
        String password = registerRequest.get("password");
                
        // 檢查帳密是否符合規定
        if (!VALID_USERNAME_PASSWORD_REGEX.matcher(username).matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("帳號必須由6~10個字母與數字組成!");
        }

        if (!VALID_USERNAME_PASSWORD_REGEX.matcher(password).matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密碼必須由6~10個字母與數字組成!");
        }
        
        // 檢查是否重複
        if (userService.findByUsername(username) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("帳號已存在");
        }

        // 註冊用戶
        userService.registerUser(username, password);
        return ResponseEntity.ok().body("註冊成功");
    }


}
