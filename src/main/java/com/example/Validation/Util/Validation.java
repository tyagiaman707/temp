package com.example.Validation.Util;

import com.example.Validation.model.User;
import com.example.Validation.repository.IUserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validation {
    @Autowired
    IUserRepository UserRepository;
    public  boolean isUserPresent(JSONObject j1){
        Integer id = j1.getInt("user_id");
        List<String>errors = new ArrayList<>();
        if(j1.getString("postData").length()==0){
            errors.add("postData: postData connot be empty");
        }

        User user = UserRepository.findById(id).get();

        return user!=null && errors.isEmpty();

    }

    public List<String> validateUser(JSONObject j1) {
        Integer user_id = j1.getInt("user_id");
        List<String>errors = new ArrayList<>();
        if(UserRepository.findById(user_id).get()!=null){
            errors.add("user_id : User with user_id - "+user_id+" already exists");
            return errors;
        }

        if(j1.getString("first_name")==null){
            errors.add("user_name : user_name cannot be empty");
        }
        if(j1.getString("last_name")==null){
            errors.add("last_name : last_name cannot be empty");
        }
        if(!(j1.getInt("age")>=10)){
            errors.add("age : age cannot be less than 10");
        }
        if(j1.getString("email")==null){
            errors.add("email : email is mandatory");
        }else if(!Validation.isValidEmail(j1.getString("email"))){
            errors.add("email : Please provide valid email");
        }
        if(j1.getString("phone_number")==null){
            errors.add("phone_number : phone_number is mandatory");
        }
        else if(!Validation.isValidMobileNo(j1.getString("phone_number"))){
            errors.add("phone_number : Please provide valid phone_number");
        }
        return errors;

    }

    public static boolean isValidEmail(String value){
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        String out = ((matcher.matches() ? "valid" : "invalid"));
        return out.equals("valid");
    }

    public static boolean isValidMobileNo(String str)
    {
        Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        Matcher match = ptrn.matcher(str);

        return (match.find() && match.group().equals(str));
    }
}
