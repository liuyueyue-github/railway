package com.liuhappy.beta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhappy.beta.controller.dto.CurrentAmtInfo;
import com.liuhappy.beta.vo.CurrentAmt;

import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
public interface CurrentAmtService extends IService<CurrentAmt> {
  
    CurrentAmt addCurrentAmt(CurrentAmt ca);

   
    boolean deleteCurrentAmt(CurrentAmt ca);

    
    boolean updateCurrentAmt(CurrentAmt ca);


    List<CurrentAmt> selectCurrentAmtByCnd(CurrentAmt ca);


    CurrentAmtInfo selectCurrentAmtList();
}
