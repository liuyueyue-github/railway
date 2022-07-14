package com.liuhappy.BugDemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhappy.BugDemo.vo.Product;
import com.liuhappy.BugDemo.vo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
