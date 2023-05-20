package com.liuhappy.beta.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
/**
 * @author Grin
 * @Date 2023/5/20
 * @Description
 */
public class InquireFlowInfoIn {
    private List<String> paidCategoryList;

    private List<String> incomeCategoryList;

    private String startDt;

    private String endDt;
}
