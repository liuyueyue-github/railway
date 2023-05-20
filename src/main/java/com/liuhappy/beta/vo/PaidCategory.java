package com.liuhappy.beta.vo;

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
@TableName("PAID_CATEGORY_M")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaidCategory {
    private String acctNbr;
    private String paidCategoryId;
    private String paidCategoryNm;
}
