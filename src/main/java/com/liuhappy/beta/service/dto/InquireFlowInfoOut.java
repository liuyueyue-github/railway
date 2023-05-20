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

    private Map<String, BigDecimal> currentPaidAmtDetailMap;

    private Map<String, BigDecimal> currentIncomeAmtDetailMap;

    private Map<String, BigDecimal> currentPaidAmtMap;

    private Map<String, BigDecimal> currentIncomeAmtMap;


    private Map<String, BigDecimal> totalPaidAmtMap;

    private Map<String, BigDecimal> totalIncomeAmtMap;

    private BigDecimal totalPaidAmt;

    private BigDecimal totalIncomeAmt;
}
