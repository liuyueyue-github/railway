package com.liuhappy.BugDemo;

import com.liuhappy.BugDemo.mapper.UserMapper;
import com.liuhappy.BugDemo.vo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ControllerDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void MapperSelectTest(){
        userMapper.insert(User.builder().username("Happiness").password("12345").build());
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }
}
