package com.example.booksharingapp_java;

public class User {

    public String name, age, email;
     public User()
     {

     }

     public User( String name, String age, String email)
     {
         this.age = age;
         this.name = name;
         this.email = email;

     }
     public User(String email)
     {
         this.email = email;
     }

}
