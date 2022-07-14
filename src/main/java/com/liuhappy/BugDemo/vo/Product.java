package com.liuhappy.BugDemo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("pd_m")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @TableId(type = IdType.ASSIGN_UUID)
    private String pdId;
    private String pdNm;
}
