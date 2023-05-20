package com.liuhappy.beta.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhappy.beta.vo.IncomeCategory;
import com.liuhappy.beta.vo.PaidCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
@Mapper
public interface IncomeCategoryMapper extends BaseMapper<IncomeCategory> {

    /**
     * 查询最大收入类别编号
     * @param icId 收入类别编号
     * @return icId
     */
    int selectMaxIcId(@Param("icId") String icId);
}
