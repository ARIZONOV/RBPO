package com.antondemin.rbpo_demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

class User {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

@RestController
public class UserController {

    @PostMapping("/api/user")
    public String createUser(@RequestBody User user) {
        return "User created: " + user.getName() + ", age " + user.getAge();
    }
}
