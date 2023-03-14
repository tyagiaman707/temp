package com.example.Validation.service;

//import com.example.Validation.exception.RecordNotFound;
import com.example.Validation.exception.RecordNotFound;
import com.example.Validation.model.User;
import com.example.Validation.repository.IUserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserRepository iUserRepository;
    public Integer saveUser(User user) {
        iUserRepository.save(user);
        return user.getUser_id();
    }

    public User setUser(JSONObject j1) {
        User newuser = new User();
        newuser.setUser_id(j1.getInt("user_id"));
        newuser.setFirst_name(j1.getString("first_name"));
        newuser.setLast_name(j1.getString("last_name"));
        newuser.setEmail(j1.getString("email"));
        newuser.setAge(j1.getInt("age"));
        newuser.setPhone_number(j1.getString("phone_number"));
        return newuser;
    }

    public List<User> getALlUsers(Integer id) throws RecordNotFound {
        if(id!=null && iUserRepository.findById(id).get()!=null){
            List<User>findById = new ArrayList<>();
            findById.add(iUserRepository.findById(id).get());
            return findById;
        }
        throw new RecordNotFound("User_id not found");
//        return iUserRepository.findAll();
    }

    public void deleteUser(Integer id) {
        iUserRepository.deleteById(id);
    }

    public void updateUser(JSONObject j1) {
        User newuser = iUserRepository.findById(j1.getInt("user_id")).get();
        newuser.setFirst_name(j1.getString("first_name"));
        newuser.setLast_name(j1.getString("last_name"));
        newuser.setEmail(j1.getString("email"));
        newuser.setAge(j1.getInt("age"));
        newuser.setPhone_number(j1.getString("phone_number"));
        iUserRepository.save(newuser);
    }
}
