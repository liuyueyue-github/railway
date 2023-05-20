package com.liuhappy.beta.controller;

import com.liuhappy.beta.service.IncomeCategoryService;
import com.liuhappy.beta.vo.IncomeCategory;
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
@RequestMapping("/ic")
public class IncomeCategoryController {
    @Autowired
    private IncomeCategoryService IncomeCategoryService;

    @PostMapping("/addIncomeCategory")
    @Transactional
    public IncomeCategory addIncomeCategory(@RequestBody IncomeCategory ic) {
        return IncomeCategoryService.addIncomeCategory(ic);
    }

    @PostMapping("/deleteIncomeCategory")
    @Transactional
    public boolean deleteIncomeCategory(@RequestBody IncomeCategory ic) {
        return IncomeCategoryService.deleteIncomeCategory(ic);
    }

    @PostMapping("/updateIncomeCategory")
    @Transactional
    public boolean updateIncomeCategory(@RequestBody IncomeCategory ic) {
        return IncomeCategoryService.updateIncomeCategory(ic);
    }

    @PostMapping("/selectIncomeCategoryByCnd")
    public List<IncomeCategory> selectIncomeCategoryByCnd(@RequestBody IncomeCategory ic) {
        return IncomeCategoryService.selectIncomeCategoryByCnd(ic);
    }

    @PostMapping("/selectIncomeCategoryList")
    public List<IncomeCategory> selectIncomeCategoryList() {
        return IncomeCategoryService.selectIncomeCategoryList();
    }
}
