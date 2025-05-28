package com.examly.springbank.model;
import jakarta.persistence.Entity;

@Entity
public class User{
    @Id
    @GeneratedValue(stratergy=GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String phone;

    public User(){}
    public User(Long id,String username, String email,String phone){
        this.id=id;
        this.username=username;
        this.email=email;
        this.phone=phone;

    }
    public Long getId(){
        return id;

    }
    public void setId(Long Id){
        this.id=id;

    }
    public String getUsername(String username){
        this.username=username;

    }
    public String getEmail(){
        return email;

    }
    public String setEmail(String email){
        this.email=email;

    }
    public String getPhone(){
        return phone;

    }
    public String setPhone(String phone){
        this.phone =phone;
    }
}