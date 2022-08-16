package com.liuhappy.BugDemo.controller;

import com.liuhappy.BugDemo.enums.impl.ResultCode;
import com.liuhappy.BugDemo.service.ProductService;
import com.liuhappy.BugDemo.vo.Product;
import com.liuhappy.BugDemo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Grin
 * @Date 2022/7/26
 * @Description
 */
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("product/addProduct")
    @ResponseBody
    public ResultVo<Boolean> addProduct(@RequestBody Product product) {
        boolean result = productService.addProduct(product);
        return new ResultVo<>(ResultCode.SUCCESS, result);
    }
}
