package com.luliwind.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    int id;
    String username;
    String password;
    String realName;
    String phone;
    String email;
    String address;
    int role_id;
    Date created_time;
    Date updated_time;
    int is_active;
}
