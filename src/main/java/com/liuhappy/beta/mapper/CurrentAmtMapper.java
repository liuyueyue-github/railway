package com.liuhappy.beta.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhappy.beta.vo.CurrentAmt;
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
public interface CurrentAmtMapper extends BaseMapper<CurrentAmt> {

}
