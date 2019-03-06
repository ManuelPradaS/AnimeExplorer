package com.endava.AnimeExplorer.Repositories;

import com.endava.AnimeExplorer.Model.UserManager.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer>{

}

