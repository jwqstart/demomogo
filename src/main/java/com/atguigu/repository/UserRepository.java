package com.atguigu.repository;

import com.atguigu.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @auther 111
 * @create 2022-02-13
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

}