package com.example.Validation.controller;

import com.example.Validation.Util.Validation;
import com.example.Validation.exception.RecordNotFound;
import com.example.Validation.exception.RecordNotFound;
import com.example.Validation.model.User;
import com.example.Validation.service.UserService;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    Validation validation;
    @PostMapping("/addUserTo")
    public ResponseEntity<String> saveuser(@RequestBody String user){
        JSONObject j1 = new JSONObject(user);
        List<String>errors = validation.validateUser(j1);
        if(errors.isEmpty()){
            User u1 = userService.setUser(j1);
            Integer id = userService.saveUser(u1);
            return new ResponseEntity<>("User saved with user_id -"+id, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUsers")
    public void getAllUsers(@Nullable @RequestParam("user_id") Integer id) throws RecordNotFound {
        userService.getALlUsers(id);

    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteUserById(@RequestParam("id") Integer id){
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted with id - "+id, HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUserDetails(@RequestBody String user){
        JSONObject jsonObject = new JSONObject(user);
        userService.updateUser(jsonObject);
        return new ResponseEntity<>("User updated",HttpStatus.OK);
    }


}
