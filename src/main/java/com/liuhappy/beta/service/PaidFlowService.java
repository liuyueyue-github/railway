package com.liuhappy.beta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhappy.beta.vo.PaidFlow;
import com.liuhappy.beta.vo.User;

import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
public interface PaidFlowService extends IService<PaidFlow> {
    PaidFlow addPaidFlow(PaidFlow pf);

    boolean deletePaidFlow(PaidFlow pf);

    boolean updatePaidFlow(PaidFlow pf);

    List<PaidFlow> selectPaidFlowList();

    List<PaidFlow> selectPaidFlowByCnd(PaidFlow pf);
}
