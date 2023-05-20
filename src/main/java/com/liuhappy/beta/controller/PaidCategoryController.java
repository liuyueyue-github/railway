package com.liuhappy.beta.controller;

import com.liuhappy.beta.service.PaidCategoryService;
import com.liuhappy.beta.vo.PaidCategory;
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
@RequestMapping("/pc")
public class PaidCategoryController {
    @Autowired
    private PaidCategoryService paidCategoryService;

    @PostMapping("/addPaidCategory")
    @Transactional
    public PaidCategory addPaidCategory(@RequestBody PaidCategory pc) {
        return paidCategoryService.addPaidCategory(pc);
    }

    @PostMapping("/deletePaidCategory")
    @Transactional
    public boolean deletePaidCategory(@RequestBody PaidCategory pc) {
        return paidCategoryService.deletePaidCategory(pc);
    }

    @PostMapping("/updatePaidCategory")
    @Transactional
    public boolean updatePaidCategory(@RequestBody PaidCategory pc) {
        return paidCategoryService.updatePaidCategory(pc);
    }

    @PostMapping("/selectPaidCategoryByCnd")
    public List<PaidCategory> selectPaidCategoryByCnd(@RequestBody PaidCategory pc) {
        return paidCategoryService.selectPaidCategoryByCnd(pc);
    }

    @PostMapping("/selectPaidCategoryList")
    public List<PaidCategory> selectPaidCategoryList() {
        return paidCategoryService.selectPaidCategoryList();
    }
}
