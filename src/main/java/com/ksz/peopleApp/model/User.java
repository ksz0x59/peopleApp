package com.ksz.peopleApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {

    private final UUID uid;
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final Integer age;
    private final String email;

    public User(@JsonProperty("uid") UUID uid, @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName,
                @JsonProperty("gender") Gender gender, @JsonProperty("age") Integer age, @JsonProperty("email") String email) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    public static User newUser(UUID userId, User user)
    {
        return new User(userId, user.getFirstName(), user.getLastName(),
                user.getGender(), user.getAge(), user.getEmail());
    }

    public UUID getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
