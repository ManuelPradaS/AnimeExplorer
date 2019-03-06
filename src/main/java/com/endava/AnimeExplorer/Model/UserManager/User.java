package com.endava.AnimeExplorer.Model.UserManager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @Id
    private int userId;

    @NotNull
    private String user;

    @NotNull
    private String password;

}
