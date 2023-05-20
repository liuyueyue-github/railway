package com.liuhappy.beta.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhappy.beta.mapper.io.InquireFlowInfoIO;
import com.liuhappy.beta.service.dto.InquireFlowInfoIn;
import com.liuhappy.beta.vo.IncomeFlow;
import com.liuhappy.beta.vo.PaidFlow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
@Mapper
public interface IncomeFlowMapper extends BaseMapper<IncomeFlow> {
    List<InquireFlowInfoIO> getCurrentInfo(InquireFlowInfoIn in);
}
