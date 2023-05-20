package com.liuhappy.beta.controller;

import com.liuhappy.beta.service.IncomeFlowService;
import com.liuhappy.beta.service.dto.InquireFlowInfoIn;
import com.liuhappy.beta.service.dto.InquireFlowInfoOut;
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
    private IncomeFlowService incomeFlowService;

    @PostMapping("/currentInfo")
    public InquireFlowInfoOut getCurrentInfo(@RequestBody InquireFlowInfoIn in) {
        return incomeFlowService.getCurrentInfo(in);
    }
}
