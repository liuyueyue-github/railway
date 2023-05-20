package com.liuhappy.beta.controller;

import com.liuhappy.beta.service.IncomeFlowService;
import com.liuhappy.beta.service.dto.InquireFlowInfoIn;
import com.liuhappy.beta.vo.IncomeFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Grin
 * @Date 2023/5/20
 * @Description
 */
@RestController
@RequestMapping("/getInfo")
public class InquireFlowInfoController {
    @Autowired
    private IncomeFlowService IncomeFlowService;

    @PostMapping("/currentInfo")
    public IncomeFlow getCurrentInfo(@RequestBody InquireFlowInfoIn in) {
        return IncomeFlowService.getCurrentInfo(in);
    }
}
