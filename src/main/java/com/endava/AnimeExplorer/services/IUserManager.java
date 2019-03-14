package com.endava.AnimeExplorer.services;

import com.endava.AnimeExplorer.model.userManager.CurrentState;
import com.endava.AnimeExplorer.model.userManager.ProfileInformation;
import com.endava.AnimeExplorer.model.userManager.User;
import com.endava.AnimeExplorer.model.userManager.UserLogin;
import org.springframework.http.ResponseEntity;

public interface IUserManager {

     ResponseEntity<User> addUser(UserLogin newUser);

    ResponseEntity<UserLogin> logUser(UserLogin currentUser);
    String logOut();
    ResponseEntity<String> updateInformation(ProfileInformation currentInformation, int userId);
    ResponseEntity<ProfileInformation> viewProfile(int id);
    CurrentState getCurrentState();
}
