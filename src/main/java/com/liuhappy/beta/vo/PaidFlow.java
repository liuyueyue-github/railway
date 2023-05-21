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
@TableName("PAID_M")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaidFlow {
    private String acctNbr;
    private String paidCategoryId;
    private String paidCategoryNm;
    private BigDecimal paidAmt;
    private String remark;
    private String paidYear;
    private String paidMonth;
    private String paidDt;
    private String paidHms;
    private String paidMethod;
}
