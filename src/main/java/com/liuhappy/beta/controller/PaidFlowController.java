package com.liuhappy.beta.controller;

import com.liuhappy.beta.service.PaidCategoryService;
import com.liuhappy.beta.service.PaidFlowService;
import com.liuhappy.beta.vo.PaidCategory;
import com.liuhappy.beta.vo.PaidFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/26
 * @Description
 */
@RestController
@RequestMapping("/pf")
public class PaidFlowController {
    @Autowired
    private PaidFlowService paidFlowService;

    @PostMapping("/addPaidFlow")
    @Transactional
    public PaidFlow addPaidFlow(@RequestBody PaidFlow pf) {
        return paidFlowService.addPaidFlow(pf);
    }

    @PostMapping("/deletePaidFlow")
    @Transactional
    public boolean deletePaidFlow(@RequestBody PaidFlow pf) {
        return paidFlowService.deletePaidFlow(pf);
    }

    @PostMapping("/updatePaidFlow")
    @Transactional
    public boolean updatePaidFlow(@RequestBody PaidFlow pf) {
        return paidFlowService.updatePaidFlow(pf);
    }

    @PostMapping("/selectPaidFlowByCnd")
    public List<PaidFlow> selectPaidFlowByCnd(@RequestBody PaidFlow pf) {
        return paidFlowService.selectPaidFlowByCnd(pf);
    }

    @PostMapping("/selectPaidFlowList")
    public List<PaidFlow> selectPaidFlowList() {
        return paidFlowService.selectPaidFlowList();
    }
}
