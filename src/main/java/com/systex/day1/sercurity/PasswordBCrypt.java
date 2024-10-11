package com.systex.day1.sercurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordBCrypt {
	 private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	    public static String encryptPassword(String rawPassword) {
	        return passwordEncoder.encode(rawPassword);
	    }

	    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
	        return passwordEncoder.matches(rawPassword, encodedPassword);
	    }
}
