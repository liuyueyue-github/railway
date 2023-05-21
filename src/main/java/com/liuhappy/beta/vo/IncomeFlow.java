package com.liuhappy.beta.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Grin
 * @Date 2022/7/14
 * @Description
 */
@Data
@TableName("INCOME_M")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeFlow {
    private String acctNbr;
    private String incomeCategoryId;
    private String incomeCategoryNm;
    private BigDecimal incomeAmt;
    private String remark;
    private String incomeYear;
    private String incomeMonth;
    private String incomeDt;
    private String incomeHms;
}
