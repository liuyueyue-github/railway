package com.liuhappy.beta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhappy.beta.common.EmptyUtil;
import com.liuhappy.beta.enums.impl.CommonErrorCode;
import com.liuhappy.beta.exception.ExceptionCast;
import com.liuhappy.beta.mapper.IncomeCategoryMapper;
import com.liuhappy.beta.mapper.PaidCategoryMapper;
import com.liuhappy.beta.service.CurrentAmtService;
import com.liuhappy.beta.service.IncomeCategoryService;
import com.liuhappy.beta.service.IncomeFlowService;
import com.liuhappy.beta.service.PaidCategoryService;
import com.liuhappy.beta.vo.CurrentAmt;
import com.liuhappy.beta.vo.IncomeCategory;
import com.liuhappy.beta.vo.IncomeFlow;
import com.liuhappy.beta.vo.PaidCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
@Service("incomeCategoryService")
public class IncomeCategoryServiceImpl extends ServiceImpl<IncomeCategoryMapper, IncomeCategory> implements IncomeCategoryService {
    private static final String STR_INIT_SEQ = "0001";

    private static final String PD = "IC";

    @Autowired
    private IncomeFlowService incomeFlowService;
    @Autowired
    private CurrentAmtService currentAmtService;

    @Override
    @Transactional
    public IncomeCategory addIncomeCategory(IncomeCategory ic) {
        vaildAddIncomeCategory(ic);
        List<IncomeCategory> incomeCategories = this.selectIncomeCategoryByCnd(ic);

        if (!EmptyUtil.isNullOrEmpty(incomeCategories)) {
            ExceptionCast.cast(CommonErrorCode.IC_ALREADY_EXIST);
        }

        int maxPdId = this.baseMapper.selectMaxIcId(Calendar.getInstance().get(Calendar.YEAR) + STR_INIT_SEQ);
        String pcId = PD + maxPdId;
        ic.setIncomeCategoryId(pcId);

        boolean save = this.save(ic);
        if (!save) {
            ExceptionCast.cast(CommonErrorCode.IC_ADD);
        }
        return ic;
    }

    private void vaildAddIncomeCategory(IncomeCategory ic) {
        String incomeCategoryNm = ic.getIncomeCategoryNm();
        String acctNbr = ic.getAcctNbr();
        if (EmptyUtil.isNullOrEmpty(acctNbr) || EmptyUtil.isNullOrEmpty(incomeCategoryNm)
        ) {
            ExceptionCast.castWithCodeAndDesc(CommonErrorCode.USER_DEFINED.getCode(), "新增收入种类,账户/收入名称必传");
        }
    }

    private void vaildDeleteIncomeCategory(IncomeCategory ic) {
        String incomeCategoryId = ic.getIncomeCategoryId();
        String acctNbr = ic.getAcctNbr();
        if (EmptyUtil.isNullOrEmpty(acctNbr) || EmptyUtil.isNullOrEmpty(incomeCategoryId)
        ) {
            ExceptionCast.castWithCodeAndDesc(CommonErrorCode.USER_DEFINED.getCode(), "删除收入种类,账户/收入编号必传");
        }
    }
    private void vaildUpdateIncomeCategory(IncomeCategory ic) {
        String incomeCategoryId = ic.getIncomeCategoryId();
        String acctNbr = ic.getAcctNbr();
        if (EmptyUtil.isNullOrEmpty(acctNbr) || EmptyUtil.isNullOrEmpty(incomeCategoryId)
        ) {
            ExceptionCast.castWithCodeAndDesc(CommonErrorCode.USER_DEFINED.getCode(), "修改收入种类,账户/收入编号必传");
        }
    }

    @Override
    public boolean deleteIncomeCategory(IncomeCategory ic) {
        vaildDeleteIncomeCategory(ic);
        LambdaQueryWrapper<IncomeCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IncomeCategory::getAcctNbr, ic.getAcctNbr())
                .eq(IncomeCategory::getIncomeCategoryId, ic.getIncomeCategoryId());
        int delete = this.baseMapper.delete(wrapper);
        if (delete == 0) {
            ExceptionCast.cast(CommonErrorCode.IC_DELETE);
        }

        IncomeFlow inf = new IncomeFlow();
        inf.setAcctNbr(ic.getAcctNbr());
        inf.setIncomeCategoryId(ic.getIncomeCategoryId());

        incomeFlowService.deleteIncomeFlow(inf);

        CurrentAmt currentAmt = new CurrentAmt();
        currentAmt.setAcctNbr(inf.getAcctNbr());
        currentAmt.setIncomeCategoryId(ic.getIncomeCategoryId());
        List<CurrentAmt> currentAmts = currentAmtService.selectCurrentAmtByCnd(currentAmt);

        if (!EmptyUtil.isNullOrEmpty(currentAmts)) {
            currentAmtService.deleteCurrentAmt(currentAmt);
        }


        return true;
    }

    @Override
    public boolean updateIncomeCategory(IncomeCategory ic) {
        vaildUpdateIncomeCategory(ic);
        LambdaUpdateWrapper<IncomeCategory> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(IncomeCategory::getIncomeCategoryNm, ic.getIncomeCategoryNm())
                .eq(IncomeCategory::getIncomeCategoryId, ic.getIncomeCategoryId())
                .eq(IncomeCategory::getAcctNbr, ic.getAcctNbr());

        boolean update = this.update(wrapper);

        if (!update) {
            ExceptionCast.cast(CommonErrorCode.IC_UPDATE);
        }
        return true;
    }

    @Override
    public List<IncomeCategory> selectIncomeCategoryByCnd(IncomeCategory ic) {
        LambdaQueryWrapper<IncomeCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IncomeCategory::getAcctNbr, ic.getAcctNbr());
        if (!EmptyUtil.isNullOrEmpty(ic.getIncomeCategoryId())) {
            wrapper.eq(IncomeCategory::getIncomeCategoryId, ic.getIncomeCategoryId());
        }
        if (!EmptyUtil.isNullOrEmpty(ic.getIncomeCategoryNm())) {
            wrapper.eq(IncomeCategory::getIncomeCategoryNm, ic.getIncomeCategoryNm());
        }
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public List<IncomeCategory> selectIncomeCategoryList() {

        return this.list();
    }
}
