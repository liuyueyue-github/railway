package com.liuhappy.BugDemo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Grin
 * @Date 2022/7/14
 * @Description
 */
@Data
@TableName("cash_flow_d")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CashFlow {
    private String userId;
    private String pdId;
    private String txAmt;
    private String txDt;
}
