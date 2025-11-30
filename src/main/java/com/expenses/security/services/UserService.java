package com.expenses.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.expenses.security.dto.UserDTO;
import com.expenses.security.entities.User;
import com.expenses.security.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User register(UserDTO dto) {
    	User user = new User();
    	
    	if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
    		throw new RuntimeException("E-mail já cadastrado!");
    	}
    	
    	user.setEmail(dto.getEmail());
    	user.setName(dto.getName());
        user.setPassword(encoder.encode(dto.getPassword()));
        
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean verifyPassword(String senhaDigitada, String senhaHash) {
        return encoder.matches(senhaDigitada, senhaHash);
    }

}
