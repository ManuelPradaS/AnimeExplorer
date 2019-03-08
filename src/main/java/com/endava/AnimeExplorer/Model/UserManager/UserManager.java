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


    private CurrentState currentState = new CurrentState();


    @Autowired
    private UserRepository usersRepository;

    public CurrentState getCurrentState() {
        return currentState;
    }

    public ResponseEntity<User> addUser(UserLogin newUser) {

        List<User> same = usersRepository.find(newUser.getUser());

        User createdUser = new User();
        if (same.size() == 0) {
            System.out.println("User added");
            createdUser.setPassword(newUser.getPassword());
            createdUser.setUser(newUser.getUser());
            usersRepository.save(createdUser);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        } else {
            System.out.println("It already exist!!");
            return new ResponseEntity<>(createdUser, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<UserLogin> logUser(UserLogin currentUser) {

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

    public ResponseEntity<String> updateInformation(ProfileInformation currentInformation, int userId) {

        if (userId == 0) {
            return new ResponseEntity<>("Please login, for update the information", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            boolean updateName = currentInformation.getName() != null;
            boolean updateEmail = currentInformation.getEmail() != null;
            boolean updateDescription = currentInformation.getDescription() != null;

//            boolean updateImage = currentInformation.getImage() != null;

            User currentUser = usersRepository.findById(userId).get();


            if (updateName) {
                currentUser.setName(currentInformation.getName());
            }
            if (updateEmail) {
                currentUser.setEmail(currentInformation.getEmail());
            }
            if (updateDescription) {
                currentUser.setDescription(currentInformation.getDescription());
            }
//            if (updateImage) {
//                currentInformationP.setImage(currentInformation.getImage());
//            }

            usersRepository.save(currentUser);

            return new ResponseEntity<>("Your information has been updated", HttpStatus.OK);
        }

    }

    public ResponseEntity<ProfileInformation> viewProfile(int id) {
        ProfileInformation response = new ProfileInformation();
        if (id == 0) {
            ProfileInformation empty = new ProfileInformation();
            return new ResponseEntity<>(empty, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            User currentUser = usersRepository.findById(id).get();

            response.setName(currentUser.getName());
            response.setDescription(currentUser.getDescription());
            response.setEmail(currentUser.getEmail());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }


    }


    public void init() {


    }


}