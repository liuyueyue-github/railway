package com.liuhappy.beta.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhappy.beta.mapper.UserMapper;
import com.liuhappy.beta.service.UserService;
import com.liuhappy.beta.vo.User;
import org.springframework.stereotype.Service;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
