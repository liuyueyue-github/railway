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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/26
 * @Description
 */
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("product/addProduct")
    public ResultVo<Boolean> addProduct(@RequestBody Product product) {
        boolean result = productService.addProduct(product);
        return new ResultVo<>(ResultCode.SUCCESS, result);
    }

    @PostMapping("product/deleteProduct")
    public ResultVo<Boolean> deleteProduct(@RequestBody Product product) {
        boolean result = productService.deleteProduct(product);
        return new ResultVo<>(ResultCode.SUCCESS, result);
    }

    @PostMapping("product/updateProduct")
    public ResultVo<Boolean> updateProduct(@RequestBody Product product) {
        boolean result = productService.updateProduct(product);
        return new ResultVo<>(ResultCode.SUCCESS, result);
    }

    @PostMapping("product/selectProductByPdId")
    public ResultVo<Product> selectProductByPdId(@RequestBody Product product) {
        Product result = productService.selectProductByPdId(product);
        return new ResultVo<>(ResultCode.SUCCESS, result);
    }

    @PostMapping("product/selectProductList")
    public ResultVo<List<Product>> selectProductList() {
        List<Product> result = productService.selectProductList();
        return new ResultVo<>(ResultCode.SUCCESS, result);
    }
}
