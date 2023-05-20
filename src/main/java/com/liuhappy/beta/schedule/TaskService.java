package com.liuhappy.beta.schedule;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liuhappy.beta.mapper.PaidCategoryMapper;
import com.liuhappy.beta.vo.PaidCategory;
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
    private PaidCategoryMapper productMapper;

    //定时删除名称为空的产品
    @Scheduled(cron = "0 0/1 * * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void task() {
        LambdaQueryWrapper<PaidCategory> queryPdNmNull = Wrappers.lambdaQuery(PaidCategory.class);
        queryPdNmNull.eq(PaidCategory::getPaidCategoryNm, "").or().eq(PaidCategory::getPaidCategoryNm, null);
        for (PaidCategory product : productMapper.selectList(queryPdNmNull)) {
            productMapper.deleteById(product.getPaidCategoryId());
        }
    }
}
