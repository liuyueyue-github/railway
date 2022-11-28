package com.liuhappy.BugDemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhappy.BugDemo.common.Result;
import com.liuhappy.BugDemo.enums.impl.CommonErrorCode;
import com.liuhappy.BugDemo.exception.ExceptionCast;
import com.liuhappy.BugDemo.mapper.ProductMapper;
import com.liuhappy.BugDemo.service.ProductService;
import com.liuhappy.BugDemo.vo.Product;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    private static final String STR_INIT_SEQ = "0001";

    private static final String PD = "PD";

    @Override
    public Product addProduct(Product product) {
        int maxPdId = this.baseMapper.selectMaxPdId(Calendar.getInstance().get(Calendar.YEAR) + STR_INIT_SEQ);
        String pdId = PD + maxPdId;
        product.setPdId(pdId);
        boolean save = this.save(product);
        if (!save) {
            ExceptionCast.cast(CommonErrorCode.PD_ERROR_00);
        }
        return product;
    }

    @Override
    public boolean deleteProduct(Product product) {
        boolean remove = this.removeById(product.getPdId());
        if (!remove) {
            ExceptionCast.cast(CommonErrorCode.PD_ERROR_01);
        }
        return true;
    }

    @Override
    public boolean updateProduct(Product product) {
        boolean update = this.updateById(product);
        if (!update) {
            ExceptionCast.cast(CommonErrorCode.PD_ERROR_02);
        }
        return true;
    }

    @Override
    public Product selectProductByPdId(Product product) {
        Product pd = this.baseMapper.selectById(product.getPdId());
        if (pd == null) {
            ExceptionCast.cast(CommonErrorCode.PD_ERROR_03);
        }
        return pd;
    }

    @Override
    public List<Product> selectProductList() {
        List<Product> productList = this.list();
        if (productList == null) {
            ExceptionCast.cast(CommonErrorCode.PD_ERROR_03);
        }
        return productList;
    }
}
