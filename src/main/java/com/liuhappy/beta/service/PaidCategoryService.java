package com.liuhappy.beta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhappy.beta.vo.PaidCategory;

import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
public interface PaidCategoryService extends IService<PaidCategory> {
    /**
     * addProduct
     * @param product -
     * @return -
     */
    PaidCategory addPaidCategory(PaidCategory product);

    /**
     * deleteProduct
     * @param product -
     * @return -
     */
    boolean deletePaidCategory(PaidCategory product);

    /**
     * updateProduct
     * @param product -
     * @return -
     */
    boolean updatePaidCategory(PaidCategory product);

    /**
     * selectProductByPdId
     * @param product -
     * @return -
     */
    List<PaidCategory> selectPaidCategoryByCnd(PaidCategory product);

    /**
     * selectProductList
     * @return -
     */
    List<PaidCategory> selectPaidCategoryList();
}
