package com.example.Validation.service;

import com.example.Validation.Util.Validation;

import com.example.Validation.model.Post;
import com.example.Validation.model.User;
import com.example.Validation.repository.IPostRepository;
import com.example.Validation.repository.IUserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PostService {
    @Autowired
    IUserRepository UserRepository;
    @Autowired
    IPostRepository PostRepository;

    @Autowired
    Validation validation;

    public Post set(JSONObject j1) {
        Post post = new Post();
        post.setPostData(j1.getString("postData"));
        post.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        post.setUpdatedDate(null);
        User user = UserRepository.findById(j1.getInt("user_id")).get();
        post.setUser(user);
        return post;
    }

    public void addPost(Post newPost) {
        PostRepository.save(newPost);
    }

    public JSONArray getPosts(Integer postId, Integer userId) {
        JSONArray posts = new JSONArray();
        if(null!=postId ){

            Post p1 = PostRepository.findById(postId).get();
            JSONObject j1 = new JSONObject();
            j1.put("post_id",p1.getPost_id());
            j1.put("postData",p1.getPostData());
            j1.put("createdDate",p1.getCreatedDate());
            j1.put("updatedDate",p1.getUpdatedDate());
//            j1.put("user_id",p1.getUser().getUser_id());
            JSONObject j2 = new JSONObject();
            User user = p1.getUser();
            j2.put("user_id",user.getUser_id());
            j2.put("first_name",user.getFirst_name());
            j2.put("last_name",user.getLast_name());
            j2.put("age",user.getAge());
            j2.put("email",user.getEmail());
            j2.put("phone_number",user.getPhone_number());
            j1.put("user",j2);
            posts.put(j1);

        }else if(null!=userId && UserRepository.findById(userId).isPresent()){

            List<Post>allPosts = PostRepository.findAll();
            for(Post p1: allPosts){
                if(p1.getUser().getUser_id()==userId){
                    JSONObject j1 = new JSONObject();
                    j1.put("post_id",p1.getPost_id());
                    j1.put("postData",p1.getPostData());
                    j1.put("createdDate",p1.getCreatedDate());
                    j1.put("updatedDate",p1.getUpdatedDate());
                    j1.put("user_id",p1.getUser().getUser_id());
                    JSONObject j2 = new JSONObject();
                    User user = p1.getUser();
                    j2.put("user_id",user.getUser_id());
                    j2.put("first_name",user.getFirst_name());
                    j2.put("last_name",user.getLast_name());
                    j2.put("age",user.getAge());
                    j2.put("email",user.getEmail());
                    j2.put("phone_number",user.getPhone_number());
                    j1.put("user",j2);
                    posts.put(j1);
                }
            }
        }
        return posts;
    }

    public void deletePost(Integer id) {
        PostRepository.deleteById(id);
    }

//    public void savePost(PostDto postDto) {
//        Post post = new Post();
//        post.setPostData(postDto.getPostData());
//        post.setUser(UserRepository.findById(postDto.getUser_id()).get());
//        post.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//
//    }
}
