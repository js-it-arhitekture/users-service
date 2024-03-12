package com.example.nakup_vstopnic.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "users")
@Data // avtomatsko generiranje  getters, setters,constructors, toString
public class User {
    @Id //ID in mongodb
    private String id;
    private String username;
    private String password;

    private String name;

    private int age;

    public User(String username, String password, String name, int age){
        this.username = username;
        this.password = password;
        this.name = name;
        this.age= age;
    }

}
