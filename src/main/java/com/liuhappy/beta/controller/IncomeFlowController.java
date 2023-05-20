package com.liuhappy.beta.controller;

import com.liuhappy.beta.service.IncomeFlowService;
import com.liuhappy.beta.vo.IncomeFlow;
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
@RequestMapping("/inf")
public class IncomeFlowController {
    @Autowired
    private IncomeFlowService IncomeFlowService;

    @PostMapping("/addIncomeFlow")
    @Transactional
    public IncomeFlow addIncomeFlow(@RequestBody IncomeFlow inf) {
        return IncomeFlowService.addIncomeFlow(inf);
    }

    @PostMapping("/deleteIncomeFlow")
    @Transactional
    public boolean deleteIncomeFlow(@RequestBody IncomeFlow inf) {
        return IncomeFlowService.deleteIncomeFlow(inf);
    }

    @PostMapping("/updateIncomeFlow")
    @Transactional
    public boolean updateIncomeFlow(@RequestBody IncomeFlow inf) {
        return IncomeFlowService.updateIncomeFlow(inf);
    }

    @PostMapping("/selectIncomeFlowByCnd")
    public List<IncomeFlow> selectIncomeFlowByCnd(@RequestBody IncomeFlow inf) {
        return IncomeFlowService.selectIncomeFlowByCnd(inf);
    }

    @PostMapping("/selectIncomeFlowList")
    public List<IncomeFlow> selectIncomeFlowList() {
        return IncomeFlowService.selectIncomeFlowList();
    }
}
