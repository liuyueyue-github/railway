package com.liuhappy.beta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhappy.beta.common.EmptyUtil;
import com.liuhappy.beta.enums.impl.CommonErrorCode;
import com.liuhappy.beta.exception.ExceptionCast;
import com.liuhappy.beta.mapper.IncomeFlowMapper;
import com.liuhappy.beta.mapper.PaidFlowMapper;
import com.liuhappy.beta.service.*;
import com.liuhappy.beta.service.dto.InquireFlowInfoIn;
import com.liuhappy.beta.utils.DateUtils;
import com.liuhappy.beta.vo.*;
import com.sun.tools.javac.comp.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/13
 * @Description
 */
@Service("incomeFlowService")
public class IncomeFlowServiceImpl extends ServiceImpl<IncomeFlowMapper, IncomeFlow> implements IncomeFlowService {

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    @Autowired
    @Lazy
    private IncomeCategoryService incomeCategoryService;

    @Autowired
    private CurrentAmtService currentAmtService;

    @Override
    public IncomeFlow addIncomeFlow(IncomeFlow inf) {
        String incomeCategoryId = inf.getIncomeCategoryId();

        IncomeCategory incomeCategory = new IncomeCategory();
        incomeCategory.setIncomeCategoryId(incomeCategoryId);
        incomeCategory.setAcctNbr(inf.getAcctNbr());

        List<IncomeCategory> incomeCategories = incomeCategoryService.selectIncomeCategoryByCnd(incomeCategory);

        if (EmptyUtil.isNullOrEmpty(incomeCategories)) {
            ExceptionCast.cast(CommonErrorCode.IC_SELECT);
        }

        String currentDt = DateUtils.dateTimeNow(YYYYMMDDHHMMSS);
        inf.setIncomeYear(currentDt.substring(0, 4));
        inf.setIncomeMonth(currentDt.substring(4, 6));
        inf.setIncomeDt(currentDt.substring(4, 8));
        inf.setIncomeHms(currentDt.substring(8, 14));
        this.save(inf);

        CurrentAmt currentAmt = new CurrentAmt();
        currentAmt.setAcctNbr(inf.getAcctNbr());
        currentAmt.setIncomeCategoryId(incomeCategoryId);
        List<CurrentAmt> currentAmts = currentAmtService.selectCurrentAmtByCnd(currentAmt);
        if (EmptyUtil.isNullOrEmpty(currentAmts)) {
            currentAmt.setCurrentAmt(inf.getIncomeAmt());
            currentAmtService.addCurrentAmt(currentAmt);
            return inf;
        }

        BigDecimal currentAmtInfo = currentAmts.get(0).getCurrentAmt();

        currentAmt.setCurrentAmt(currentAmtInfo.add(inf.getIncomeAmt()));

        currentAmtService.updateCurrentAmt(currentAmt);

        return inf;
    }

    @Override
    public boolean deleteIncomeFlow(IncomeFlow inf) {
        QueryWrapper<IncomeFlow> wrapper = new QueryWrapper<>();

        wrapper.lambda().eq(IncomeFlow::getAcctNbr, inf.getAcctNbr())
                .eq(IncomeFlow::getIncomeCategoryId, inf.getIncomeCategoryId())
                .eq(IncomeFlow::getIncomeYear, inf.getIncomeYear())
                .eq(IncomeFlow::getIncomeMonth, inf.getIncomeMonth())
                .eq(IncomeFlow::getIncomeDt, inf.getIncomeDt())
                .eq(IncomeFlow::getIncomeHms, inf.getIncomeHms());

        boolean remove = this.remove(wrapper);

        if (!remove) {
            ExceptionCast.cast(CommonErrorCode.IF_DELETE);
        }

        IncomeFlow inquire = new IncomeFlow();
        inquire.setAcctNbr(inf.getAcctNbr());
        inquire.setIncomeCategoryId(inf.getIncomeCategoryId());

        List<IncomeFlow> incomeFlows = this.selectIncomeFlowByCnd(inquire);
        CurrentAmt ca = new CurrentAmt();
        if (EmptyUtil.isNullOrEmpty(incomeFlows)) {

            ca.setAcctNbr(inf.getAcctNbr());
            ca.setIncomeCategoryId(inf.getIncomeCategoryId());
            currentAmtService.deleteCurrentAmt(ca);
            return true;
        }

        List<IncomeFlow> incomeFlowsInfo = this.baseMapper.selectList(wrapper);

        List<CurrentAmt> currentAmts = currentAmtService.selectCurrentAmtByCnd(ca);

        BigDecimal currentAmt = currentAmts.get(0).getCurrentAmt();
        ca.setCurrentAmt(currentAmt.subtract(incomeFlowsInfo.get(0).getIncomeAmt()));

        currentAmtService.updateCurrentAmt(ca);
        return true;
    }

    @Override
    public boolean updateIncomeFlow(IncomeFlow inf) {
        IncomeFlow inquire = new IncomeFlow();
        inquire.setAcctNbr(inf.getAcctNbr());
        inquire.setIncomeCategoryId(inf.getIncomeCategoryId());
        inquire.setIncomeYear(inf.getIncomeYear());
        inquire.setIncomeMonth(inquire.getIncomeMonth());
        inquire.setIncomeDt(inquire.getIncomeDt());
        inquire.setIncomeHms(inquire.getIncomeHms());

        List<IncomeFlow> incomeFlows = this.selectIncomeFlowByCnd(inquire);

        LambdaUpdateWrapper<IncomeFlow> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(IncomeFlow::getIncomeAmt, inf.getIncomeAmt())
                .set(IncomeFlow::getRemark, inf.getRemark())
                .eq(IncomeFlow::getAcctNbr, inf.getAcctNbr())
                .eq(IncomeFlow::getIncomeCategoryId, inf.getIncomeCategoryId())
                .eq(IncomeFlow::getIncomeYear, inf.getIncomeYear())
                .eq(IncomeFlow::getIncomeMonth, inf.getIncomeMonth())
                .eq(IncomeFlow::getIncomeDt, inf.getIncomeDt())
                .eq(IncomeFlow::getIncomeHms, inf.getIncomeHms());

        boolean update = this.update(wrapper);
        if (!update) {
            ExceptionCast.cast(CommonErrorCode.IF_UPDATE);
        }

        BigDecimal incomeAmt = incomeFlows.get(0).getIncomeAmt();

        CurrentAmt ca = new CurrentAmt();
        ca.setAcctNbr(inf.getAcctNbr());
        ca.setIncomeCategoryId(inf.getIncomeCategoryId());

        List<CurrentAmt> currentAmts = currentAmtService.selectCurrentAmtByCnd(ca);

        ca.setCurrentAmt(currentAmts.get(0).getCurrentAmt().subtract(incomeAmt).add(inf.getIncomeAmt()));

        currentAmtService.updateCurrentAmt(ca);
        return true;
    }

    @Override
    public List<IncomeFlow> selectIncomeFlowList() {
        return this.list();
    }

    @Override
    public List<IncomeFlow> selectIncomeFlowByCnd(IncomeFlow inf) {
        QueryWrapper<IncomeFlow> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<IncomeFlow> wrapperLambda = wrapper.lambda();

        wrapperLambda.eq(IncomeFlow::getAcctNbr, inf.getAcctNbr());
        if (!EmptyUtil.isNullOrEmpty(inf.getIncomeCategoryId())) {
            wrapperLambda.eq(IncomeFlow::getIncomeCategoryId, inf.getIncomeCategoryId());
        }
        if (!EmptyUtil.isNullOrEmpty(inf.getIncomeYear())) {
            wrapperLambda.eq(IncomeFlow::getIncomeYear, inf.getIncomeYear());
        }
        if (!EmptyUtil.isNullOrEmpty(inf.getIncomeMonth())) {
            wrapperLambda.eq(IncomeFlow::getIncomeMonth, inf.getIncomeMonth());
        }
        if (!EmptyUtil.isNullOrEmpty(inf.getIncomeDt())) {
            wrapperLambda.eq(IncomeFlow::getIncomeDt, inf.getIncomeDt());
        }
        if (!EmptyUtil.isNullOrEmpty(inf.getIncomeHms())) {
            wrapperLambda.eq(IncomeFlow::getIncomeHms, inf.getIncomeHms());
        }

        return this.baseMapper.selectList(wrapperLambda);
    }

    @Override
    public IncomeFlow getCurrentInfo(InquireFlowInfoIn in) {
        this.baseMapper.getCurrentInfo(in);
        return null;
    }
}
