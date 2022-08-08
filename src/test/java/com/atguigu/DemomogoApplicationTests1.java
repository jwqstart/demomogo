package com.atguigu;

import com.atguigu.entity.User;
import com.atguigu.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * @auther 111
 * @create 2022-02-13
 */
@SpringBootTest
public class DemomogoApplicationTests1 {

    @Autowired
    private UserRepository userRepository;


    //添加
    @Test
    public void createUser() {
        User user = new User();
        user.setAge(20);
        user.setName("张三");
        user.setEmail("3332200@qq.com");
        User user1 = userRepository.save(user);
        System.out.println(user1);
    }

    //查询所有
    @Test
    public void findUser() {
        List<User> all = userRepository.findAll();
        System.out.println(all);
    }

    //id查询
    @Test
    public void getById() {
        Optional<User> byId = userRepository.findById("62087405dfb35027f5ed0209");
        System.out.println(byId);
    }


    //条件查询
    @Test
    public void findUserList() {
        User user = new User();
        user.setName("张三");
        user.setAge(20);

        Example<User> of = Example.of(user);
        List<User> byId = userRepository.findAll(of);
        System.out.println(byId);
    }


    //模糊查询
    @Test
    public void findUsersLikeName() {
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matching = ExampleMatcher.matching().//构建对象
                withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)//改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写;

        User user = new User();

        user.setName("张");
        Example<User> of = Example.of(user,matching);
        List<User> byId = userRepository.findAll(of);

        System.out.println(byId);
    }


    //分页查询
    @Test
    public void findUsersPage() {
        Sort sort = Sort.by(Sort.Direction.DESC, "age");
        //0为第一页
        Pageable pageable = PageRequest.of(0, 10, sort);
        //创建匹配器，即如何使用查询条件
//        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
//                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
//        User user = new User();
//        user.setName("三");
//        Example<User> userExample = Example.of(user, matcher);
//        //创建实例
//        Example<User> example = Example.of(user, matcher);
        User user = new User();
        Example<User> example = Example.of(user);
        Page<User> pages = userRepository.findAll(example, pageable);
        System.out.println(pages.getTotalPages());//总页数
        System.out.println(pages.getContent());//每页数据集合
        System.out.println(pages.getPageable());


    }
    //修改
    @Test
    public void updateUser() {
        User user = userRepository.findById("6208a0d39c6b222a4601c793").get();
        user.setName("张三_1");
        user.setAge(25);
        user.setEmail("883220990@qq.com");
        User save = userRepository.save(user);
        System.out.println(save);
    }

    //删除
    @Test
    public void delete() {
        userRepository.deleteById("62087405dfb35027f5ed0209");
    }


    @Test
    public void test() {
        byte a = 127;
        byte b = 127;
        //b = a + b; // error : cannot convert from int to byte
        b += a; // ok 
    }
}
