package com.liuhappy.BugDemo.controller;

import com.liuhappy.BugDemo.enums.impl.ResultEnum;
import com.liuhappy.BugDemo.service.ProductService;
import com.liuhappy.BugDemo.vo.Product;
import com.liuhappy.BugDemo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Result<Boolean> addProduct(@RequestBody Product product) {
        boolean result = productService.addProduct(product);
        return Result.success(result);
    }

    @PostMapping("product/deleteProduct")
    public Result<Boolean> deleteProduct(@RequestBody Product product) {
        boolean result = productService.deleteProduct(product);
        return Result.success(result);
    }

    @PostMapping("product/updateProduct")
    public Result<Boolean> updateProduct(@RequestBody Product product) {
        boolean result = productService.updateProduct(product);
        return Result.success(result);
    }

    @PostMapping("product/selectProductByPdId")
    public Result<Product> selectProductByPdId(@RequestBody Product product) {
        Product result = productService.selectProductByPdId(product);
        return Result.success(result);
    }

    @PostMapping("product/selectProductList")
    public Result<List<Product>> selectProductList() {
        List<Product> result = productService.selectProductList();
        return Result.success(result);
    }
}
