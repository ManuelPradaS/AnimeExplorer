package com.endava.AnimeExplorer.Model.UserManager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    private CurrentState currentState= new CurrentState();


    @Autowired
    private UserRepository usersRepository;


    public ResponseEntity<User> addUser(User newUser) {

        List<User> same = usersRepository.find(newUser.getUser());

        if (same.size() == 0) {
            System.out.println("User added");
            usersRepository.save(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } else {
            System.out.println("It already exist!!");
            return new ResponseEntity<>(newUser, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<User> logUser(User currentUser) {

        List<User> result = usersRepository.find(currentUser.getUser());

        if (result.size() == 0) {
            return new ResponseEntity<>(currentUser, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            boolean correctPassword = result.get(0).getPassword().equals(currentUser.getPassword());
            if (correctPassword) {
                currentState.setUserActive(true);
                currentState.setUserId(result.get(0).getUserId());
                currentState.setUserName(result.get(0).getUser());
                return new ResponseEntity<>(currentUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(currentUser, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }

    public String logOut() {
        if (currentState.isUserActive()) {
            currentState.setUserActive(false);
            currentState.setUserId(0);
            currentState.setUserName("none");
            return "Logout successful";
        } else {
            return "There not active user";
        }

    }

    public void init(){


    }


}