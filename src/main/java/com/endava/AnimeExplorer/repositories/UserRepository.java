package com.endava.AnimeExplorer.repositories;

import com.endava.AnimeExplorer.model.userManager.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Integer> {


    @Query("SELECT u FROM User u WHERE u.user =:userName")
    public List<User> find(@Param("userName") String userName);

}

