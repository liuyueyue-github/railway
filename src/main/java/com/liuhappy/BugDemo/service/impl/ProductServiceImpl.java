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
    public boolean addProduct(Product product){
        return this.save(product);
    }

    @Override
    public boolean deleteProduct(Product product){
        return this.removeById(product.getPdId());
    }

    @Override
    public boolean updateProduct(Product product){
        return this.updateById(product);
    }

    @Override
    public Product selectProductByPdId(Product product){
        return this.baseMapper.selectById(product.getPdId());
    }

    @Override
    public List<Product> selectProductList(){
        return this.list();
    }
}
