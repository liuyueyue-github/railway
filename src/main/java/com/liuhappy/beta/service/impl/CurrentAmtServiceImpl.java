package com.liuhappy.beta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhappy.beta.common.EmptyUtil;
import com.liuhappy.beta.controller.dto.CurrentAmtInfo;
import com.liuhappy.beta.enums.impl.CommonErrorCode;
import com.liuhappy.beta.exception.ExceptionCast;
import com.liuhappy.beta.mapper.CurrentAmtMapper;
import com.liuhappy.beta.mapper.IncomeCategoryMapper;
import com.liuhappy.beta.service.CurrentAmtService;
import com.liuhappy.beta.service.IncomeCategoryService;
import com.liuhappy.beta.vo.CurrentAmt;
import com.liuhappy.beta.vo.IncomeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
@Service("currentCategoryService")
public class CurrentAmtServiceImpl extends ServiceImpl<CurrentAmtMapper, CurrentAmt> implements CurrentAmtService {

    @Autowired
    @Lazy
    private IncomeCategoryService incomeCategoryService;

    @Override
    public CurrentAmt addCurrentAmt(CurrentAmt ca) {
        vaildAddOrUpdateCurrentAmt(ca);
        this.save(ca);
        return ca;
    }

    private void vaildAddOrUpdateCurrentAmt(CurrentAmt ca) {
        String acctNbr = ca.getAcctNbr();
        String incomeCategoryId = ca.getIncomeCategoryId();
        BigDecimal currentAmt = ca.getCurrentAmt();
        if (EmptyUtil.isNullOrEmpty(acctNbr) || EmptyUtil.isNullOrEmpty(incomeCategoryId)
                || (EmptyUtil.isNullOrEmpty(currentAmt))
        ) {
            ExceptionCast.castWithCodeAndDesc(CommonErrorCode.USER_DEFINED.getCode(), "新增或修改当前金额时候,账户/卡编号/当前金额必传");
        }
    }

    private void vaildDeleteCurrentAmt(CurrentAmt ca) {
        String acctNbr = ca.getAcctNbr();
        String incomeCategoryId = ca.getIncomeCategoryId();
        if (EmptyUtil.isNullOrEmpty(acctNbr) || EmptyUtil.isNullOrEmpty(incomeCategoryId)
        ) {
            ExceptionCast.castWithCodeAndDesc(CommonErrorCode.USER_DEFINED.getCode(), "删除当前金额时候,账户/卡编号必传");
        }
    }

    @Override
    public boolean deleteCurrentAmt(CurrentAmt ca) {
        vaildDeleteCurrentAmt(ca);
        LambdaQueryWrapper<CurrentAmt> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(CurrentAmt::getAcctNbr, ca.getAcctNbr());

        if (!EmptyUtil.isNullOrEmpty(ca.getIncomeCategoryId())) {
            wrapper.eq(CurrentAmt::getIncomeCategoryId, ca.getIncomeCategoryId());
        }

        return this.baseMapper.delete(wrapper) == 1;
    }

    @Override
    public boolean updateCurrentAmt(CurrentAmt ca) {
        vaildAddOrUpdateCurrentAmt(ca);
        LambdaUpdateWrapper<CurrentAmt> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CurrentAmt::getAcctNbr, ca.getAcctNbr())
                .eq(CurrentAmt::getIncomeCategoryId, ca.getIncomeCategoryId())
                .set(CurrentAmt::getCurrentAmt, ca.getCurrentAmt());

        return this.update(wrapper);
    }

    @Override
    public List<CurrentAmt> selectCurrentAmtByCnd(CurrentAmt ca) {

        LambdaQueryWrapper<CurrentAmt> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(CurrentAmt::getAcctNbr, ca.getAcctNbr());

        if (!EmptyUtil.isNullOrEmpty(ca.getIncomeCategoryId())) {
            wrapper.eq(CurrentAmt::getIncomeCategoryId, ca.getIncomeCategoryId());
        }

        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public CurrentAmtInfo selectCurrentAmtList() {

        CurrentAmtInfo currentAmtInfo = new CurrentAmtInfo();

        List<CurrentAmt> list = this.list();

        handleResult(list);

        currentAmtInfo.setCurrentAmtList(list);
        BigDecimal totalAmt = BigDecimal.ZERO;
        if (!EmptyUtil.isNullOrEmpty(list)) {
            for (CurrentAmt currentAmt : list) {
                totalAmt = totalAmt.add(currentAmt.getCurrentAmt());
            }
        }
        currentAmtInfo.setTotalAmt(totalAmt);
        return currentAmtInfo;
    }

    private void handleResult(List<CurrentAmt> list) {
        if (!EmptyUtil.isNullOrEmpty(list)) {
            for (CurrentAmt currentAmt : list) {
                String incomeCategoryId = currentAmt.getIncomeCategoryId();
                IncomeCategory inf = new IncomeCategory();
                inf.setAcctNbr(currentAmt.getAcctNbr());
                inf.setIncomeCategoryId(incomeCategoryId);
                List<IncomeCategory> incomeCategories = incomeCategoryService.selectIncomeCategoryByCnd(inf);
                currentAmt.setIncomeCategoryNm(incomeCategories.get(0).getIncomeCategoryNm());
            }
        }
    }
}
