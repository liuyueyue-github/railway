package com.liuhappy.BugDemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhappy.BugDemo.mapper.CashFlowMapper;
import com.liuhappy.BugDemo.service.CashFlowService;
import com.liuhappy.BugDemo.vo.CashFlow;
import org.springframework.stereotype.Service;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
@Service("cashFlowService")
public class CashFlowServiceImpl extends ServiceImpl<CashFlowMapper, CashFlow> implements CashFlowService {
}
