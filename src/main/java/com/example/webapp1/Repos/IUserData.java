package com.example.webapp1.Repos;


import com.example.webapp1.Users.Domain.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserData extends CrudRepository<User, Long> { }
