package com.example.Validation.controller;

import com.example.Validation.Util.Validation;

import com.example.Validation.model.Post;

import com.example.Validation.service.PostService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/v1/post")
public class PostController {
    @Autowired
    Validation validation;
    @Autowired
    PostService postService;

    @PostMapping("/addPost")
    public ResponseEntity<String> saveuser(@RequestBody String post){
        JSONObject j1 = new JSONObject(post);
        if(validation.isUserPresent(j1)) {
            Post newPost = postService.set(j1);
            postService.addPost(newPost);
            return new ResponseEntity<>("Post saved with post_id - "+j1.getInt("post_id"), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Post associated with user not found "+j1.getInt("user_id"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPost")
    public ResponseEntity<String> getPost(@Nullable @RequestParam("p") Integer post_id,
                                          @Nullable @RequestParam("u") Integer user_id){
        JSONArray postaArray = postService.getPosts(post_id,user_id);

        return new ResponseEntity<>(postaArray.toString(),HttpStatus.OK);
    }

    @DeleteMapping("/deletePost")
    public ResponseEntity<String> deletePost(@RequestParam("id") Integer id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted with id - "+id, HttpStatus.OK);
    }

}
