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
@TableName("CURRENT_AMT_M")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentAmt {
    private String acctNbr;
    private String incomeCategoryId;
    private BigDecimal currentAmt;
}
