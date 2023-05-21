package com.liuhappy.beta.controller.dto;

import com.liuhappy.beta.vo.CurrentAmt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Grin
 * @Date 2023/5/21
 * @Description
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CurrentAmtInfo {
    private List<CurrentAmt> currentAmtList;

    private BigDecimal totalAmt;
}
