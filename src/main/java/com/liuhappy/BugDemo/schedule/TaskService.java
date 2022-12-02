package com.liuhappy.BugDemo.schedule;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liuhappy.BugDemo.mapper.ProductMapper;
import com.liuhappy.BugDemo.service.ProductService;
import com.liuhappy.BugDemo.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Grin
 * @Date 2022/6/27
 * @Description
 */
@Component
public class TaskService {

    @Autowired
    private ProductMapper productMapper;

    //定时删除名称为空的产品
    @Scheduled(cron = "0 0/1 * * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void task() {
        LambdaQueryWrapper<Product> queryPdNmNull = Wrappers.lambdaQuery(Product.class);
        queryPdNmNull.eq(Product::getPdNm, "").or().eq(Product::getPdNm, null);
        for (Product product : productMapper.selectList(queryPdNmNull)) {
            productMapper.deleteById(product.getPdId());
        }
    }
}
