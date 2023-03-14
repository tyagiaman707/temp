package com.example.Validation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer user_id;

    @NotBlank(message = "student name cannot be empty")
    private String first_name;

    @NotBlank(message = "student's city name cannot be empty")
    private String last_name;
    @NotBlank(message = "Age is mandatory")
    private Integer age;
    @NotBlank(message = "Email cannot be empty")
    @Length(message = "length must be between 2 - 10 ",min=2, max = 10)
    @Email
    private String email;
    @NotBlank(message = "phone number is mandatory")

    private String phone_number;



}

