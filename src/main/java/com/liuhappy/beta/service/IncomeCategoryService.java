package com.liuhappy.beta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhappy.beta.vo.IncomeCategory;
import com.liuhappy.beta.vo.PaidCategory;

import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
public interface IncomeCategoryService extends IService<IncomeCategory> {
    /**
     * addProduct
     * @param product -
     * @return -
     */
    IncomeCategory addIncomeCategory(IncomeCategory inf);

    /**
     * deleteProduct
     * @param product -
     * @return -
     */
    boolean deleteIncomeCategory(IncomeCategory inf);

    /**
     * updateProduct
     * @param product -
     * @return -
     */
    boolean updateIncomeCategory(IncomeCategory inf);

    /**
     * selectProductByPdId
     * @param product -
     * @return -
     */
    List<IncomeCategory> selectIncomeCategoryByCnd(IncomeCategory inf);

    /**
     * selectProductList
     * @return -
     */
    List<IncomeCategory> selectIncomeCategoryList();
}
