package com.liuhappy.beta.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhappy.beta.vo.PaidCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
@Mapper
public interface PaidCategoryMapper extends BaseMapper<PaidCategory> {

    /**
     * 查询最大支出类别编号
     * @param pcId 支出类别编号
     * @return pcId
     */
    int selectMaxPcId(@Param("pcId") String pcId);
}
