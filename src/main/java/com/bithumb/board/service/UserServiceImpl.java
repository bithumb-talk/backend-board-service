package com.bithumb.board.service;

import com.bithumb.board.domain.User;
import com.bithumb.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private final UserRepository userRepository;

    @Override
    public User getById(long user_no){
        return userRepository.getById(user_no);
    }
}
