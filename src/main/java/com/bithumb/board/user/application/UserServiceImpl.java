package com.bithumb.board.user.application;

import com.bithumb.board.common.response.ErrorCode;
import com.bithumb.board.user.domain.User;
import com.bithumb.board.user.repository.UserRepository;
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
        User user = userRepository.findById(user_no).orElseThrow(()-> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
        return user;
    }
}
