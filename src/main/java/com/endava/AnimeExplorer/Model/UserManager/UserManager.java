package com.endava.AnimeExplorer.Model.UserManager;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.endava.AnimeExplorer.Model.UserManager.User;
import com.endava.AnimeExplorer.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Service
public class UserManager {

    @Autowired
    private UserRepository usersRepository;


    public User addUser(User newUser){

        List<User> same=usersRepository.find(newUser.getUser());
        if(same.size()==0){
            System.out.println("User added");
            usersRepository.save(newUser);
        }else{
            System.out.println("It already exist!!");
        }

        return newUser;
    }

}