package com.liuhappy.BugDemo.controller;

import com.liuhappy.BugDemo.common.Result;
import com.liuhappy.BugDemo.service.ProductService;
import com.liuhappy.BugDemo.vo.Product;
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
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PostMapping("product/deleteProduct")
    public boolean deleteProduct(@RequestBody Product product) {
        return productService.deleteProduct(product);
    }

    @PostMapping("product/updateProduct")
    public boolean updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @PostMapping("product/selectProductByPdId")
    public Product selectProductByPdId(@RequestBody Product product) {
        return productService.selectProductByPdId(product);
    }

    @PostMapping("product/selectProductList")
    public List<Product> selectProductList() {
        return productService.selectProductList();
    }
}
