package com.liuhappy.BugDemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhappy.BugDemo.vo.Product;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
public interface ProductService extends IService<Product > {
    /**
     * addProduct
     * @param product -
     * @return -
     */
    boolean addProduct(Product product);
}
