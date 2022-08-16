package com.liuhappy.BugDemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhappy.BugDemo.mapper.ProductMapper;
import com.liuhappy.BugDemo.service.ProductService;
import com.liuhappy.BugDemo.vo.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Override
    public boolean addProduct(Product product) {
        int insert = this.baseMapper.insert(product);
        return insert > 0;
    }

    @Override
    public boolean deleteProduct(Product product) {
        int insert = this.baseMapper.deleteById(product.getPdId());
        return insert > 0;
    }

    @Override
    public boolean updateProduct(Product product) {
        int insert = this.baseMapper.updateById(product);
        return insert > 0;
    }

    @Override
    public Product selectProductByPdId(Product product) {
        return this.baseMapper.selectById(product.getPdId());
    }

    @Override
    public List<Product> selectProductList() {
        return this.list();
    }
}
