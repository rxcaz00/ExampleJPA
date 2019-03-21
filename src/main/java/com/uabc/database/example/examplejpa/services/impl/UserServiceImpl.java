package com.uabc.database.example.examplejpa.services.impl;

import com.uabc.database.example.examplejpa.components.UserConverter;
import com.uabc.database.example.examplejpa.entity.User;
import com.uabc.database.example.examplejpa.model.UserModel;
import com.uabc.database.example.examplejpa.respository.UserRepository;
import com.uabc.database.example.examplejpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("userConverter")
    private UserConverter userConverter;

    @Override
    public UserModel addUser(UserModel userModel) {
        User temp = userConverter.convertUserModel2User(userModel);
        User user = userRepository.save(temp);
        return userConverter.convertUser2UserModel(user);
    }

    @Override
    public List<UserModel> listAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserModel> usersModel = new ArrayList();
        for(User user : users)
            usersModel.add(userConverter.convertUser2UserModel(user));
        return usersModel;
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id);
    }

    public UserModel findUserByIdModel(int id) {
        return userConverter.convertUser2UserModel(findUserById(id));
    }

    @Override
    public void removeUser(int id) {
        User user = findUserById(id);
        if(user != null)
            userRepository.delete(findUserById(id));
    }
}
