package com.liuhappy.beta.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
/**
 * @author Grin
 * @Date 2023/5/20
 * @Description
 */
public class InquireFlowInfoOut {

    //支出分类,支出金额
    private Map<String, BigDecimal> currentPaidAmtDetailMap;

    //收入分类,收入金额
    private Map<String, BigDecimal> currentIncomeAmtDetailMap;

    //当前支出金额
    private BigDecimal currentTotalPaidAmt;

    //当前收入金额
    private BigDecimal currentTotalIncomeAmt;
}
