package com.liuhappy.BugDemo.schedule;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liuhappy.BugDemo.service.ProductService;
import com.liuhappy.BugDemo.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Grin
 * @Date 2022/6/27
 * @Description
 */
@Component
public class TaskService {

    @Autowired
    private ProductService productService;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void task() {
        LambdaQueryWrapper<Product> queryPdNmNull = Wrappers.lambdaQuery(Product.class);
        queryPdNmNull.eq(Product::getPdNm, "").or().eq(Product::getPdNm, null);
        for (Product product : productService.list(queryPdNmNull)) {
            productService.removeById(product.getPdId());
        }
    }
}
