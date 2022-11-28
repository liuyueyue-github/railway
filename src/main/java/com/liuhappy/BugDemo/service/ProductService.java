package com.liuhappy.BugDemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhappy.BugDemo.common.Result;
import com.liuhappy.BugDemo.vo.Product;

import java.util.List;

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
    Product addProduct(Product product);

    /**
     * deleteProduct
     * @param product -
     * @return -
     */
    boolean deleteProduct(Product product);

    /**
     * updateProduct
     * @param product -
     * @return -
     */
    boolean updateProduct(Product product);

    /**
     * selectProductByPdId
     * @param product -
     * @return -
     */
    Product selectProductByPdId(Product product);

    /**
     * selectProductList
     * @return -
     */
    List<Product> selectProductList();
}
