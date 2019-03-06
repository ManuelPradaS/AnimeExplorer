package com.endava.AnimeExplorer.Model.UserManager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @Id
    @GeneratedValue
    private int userId;

    @NotNull
    private String user;

    @NotNull
    private String password;

    public String getUser() {
        return user;
    }
}
