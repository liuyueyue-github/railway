package com.liuhappy.BugDemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhappy.BugDemo.vo.Product;
import com.liuhappy.BugDemo.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 查询最大产品编号
     * @param pdId 产品编号
     * @return pdId
     */
    int selectMaxPdId(@Param("pdId") String pdId);
}
