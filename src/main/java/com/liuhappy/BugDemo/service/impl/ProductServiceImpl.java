package com.liuhappy.BugDemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhappy.BugDemo.mapper.UserMapper;
import com.liuhappy.BugDemo.service.UserService;
import com.liuhappy.BugDemo.vo.User;
import org.springframework.stereotype.Service;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}