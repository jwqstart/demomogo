package com.atguigu;

import com.atguigu.entity.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@SpringBootTest
class DemomogoApplicationTests {

    //注入MongoTemplate
    @Autowired
    private MongoTemplate mongoTemplate;


    //添加操作
    @Test
    public void create() {
        User user = new User();
        user.setAge(30);
        user.setEmail("1234@qq.com");
        user.setName("lisi");

        User insert = mongoTemplate.insert(user);

        System.out.println(insert);


        System.out.println("aa");
    }

    //查询所有
    @Test
    public void findUser() {
        List<User> userList = mongoTemplate.findAll(User.class);
        System.out.println(userList);
    }

    //根据id查询
    @Test
    public void getById() {

        User user = mongoTemplate.findById("62087353c4b57a7dd54b8a1e", User.class);
        System.out.println(user);
    }


    //条件查询
    @Test
    public void findUserList() {
//        Query query = new Query(Criteria
//                .where("name").is("test")
//                .and("age").is(20));
//        List<User> userList = mongoTemplate.find(query, User.class);

        Query query = new Query(Criteria.where("name").is("lisi"));
        List<User> users = mongoTemplate.find(query, User.class);
        System.out.println(users);
    }


    //模糊查询
    @Test
    public void findUsersLikeName() {
        String name = "st";
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        //regex：正则   CASE_INSENSITIVE：常量
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<User> userList = mongoTemplate.find(query, User.class);
        System.out.println(userList);
    }

    //分页查询
    @Test
    public void findUsersPage() {
        String name = "est";
        int pageNo = 1;
        int pageSize = 10;

        //条件构造
        Query query = new Query();
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("name").regex(pattern));
        //分页
        int totalCount = (int) mongoTemplate.count(query, User.class);
        //query.skip((pageNo - 1) * pageSize):表示跳过记录数，当前页为1则跳过0条，当前页为2则跳过10条
        List<User> userList = mongoTemplate.find(query.skip((pageNo - 1) * pageSize).limit(pageSize), User.class);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("list", userList);
        pageMap.put("totalCount",totalCount);
        System.out.println(pageMap);
    }

    //修改
    @Test
    public void updateUser() {
        User user = mongoTemplate.findById("62087353c4b57a7dd54b8a1e", User.class);
        user.setName("test_1");
        user.setAge(25);
        user.setEmail("493220990@qq.com");
        Query query = new Query(Criteria.where("_id").is(user.getId()));
        Update update = new Update();
        update.set("name", user.getName());
        update.set("age", user.getAge());
        update.set("email", user.getEmail());
        UpdateResult result = mongoTemplate.upsert(query, update, User.class);

        long count = result.getModifiedCount();
        System.out.println(count);
    }

    //删除操作
    @Test
    public void delete() {

        Query query = new Query(Criteria.where("_id").is("62087353c4b57a7dd54b8a1e"));

        DeleteResult remove = mongoTemplate.remove(query, User.class);
        long deletedCount = remove.getDeletedCount();
        System.out.println(deletedCount);

    }
}
