package com.bithumb.board.user.service;

import com.bithumb.board.user.domain.User;
import com.bithumb.board.user.repository.UserRepository;
import com.bithumb.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    @Override
    public User getById(long user_no){
        return userRepository.getById(user_no);
    }
}
