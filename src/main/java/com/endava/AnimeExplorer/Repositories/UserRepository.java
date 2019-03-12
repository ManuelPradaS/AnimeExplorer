package com.endava.AnimeExplorer.Repositories;

import com.endava.AnimeExplorer.Model.UserManager.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.Future;


public interface UserRepository extends CrudRepository<User, Integer> {


    @Query("SELECT u FROM User u WHERE u.user =:userName")
    public List<User> find(@Param("userName") String userName);

}

