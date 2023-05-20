package com.liuhappy.beta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhappy.beta.service.dto.InquireFlowInfoIn;
import com.liuhappy.beta.service.dto.InquireFlowInfoOut;
import com.liuhappy.beta.vo.IncomeFlow;

import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
public interface IncomeFlowService extends IService<IncomeFlow> {
    IncomeFlow addIncomeFlow(IncomeFlow inf);

    boolean deleteIncomeFlow(IncomeFlow inf);

    boolean updateIncomeFlow(IncomeFlow inf);

    List<IncomeFlow> selectIncomeFlowList();

    List<IncomeFlow> selectIncomeFlowByCnd(IncomeFlow inf);

    InquireFlowInfoOut getCurrentInfo(InquireFlowInfoIn in);

    List<IncomeFlow> inquirePaidFlowByCnd(InquireFlowInfoIn in);
}
