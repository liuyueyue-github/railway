package com.liuhappy.beta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhappy.beta.common.EmptyUtil;
import com.liuhappy.beta.enums.impl.CommonErrorCode;
import com.liuhappy.beta.exception.ExceptionCast;
import com.liuhappy.beta.mapper.IncomeFlowMapper;
import com.liuhappy.beta.service.*;
import com.liuhappy.beta.service.dto.InquireFlowInfoIn;
import com.liuhappy.beta.service.dto.InquireFlowInfoOut;
import com.liuhappy.beta.utils.DateUtils;
import com.liuhappy.beta.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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
    @Lazy
    private IncomeFlowService incomeFlowService;

    @Autowired
    @Lazy
    private PaidCategoryService paidCategoryService;

    @Autowired
    private PaidFlowService paidFlowService;

    @Autowired
    private CurrentAmtService currentAmtService;

    @Override
    public IncomeFlow addIncomeFlow(IncomeFlow inf) {

        vaildAddIncomeFlow(inf);

        String incomeCategoryId = getIncomeCategoryId(inf);

        inf.setIncomeCategoryId(incomeCategoryId);

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

    private void vaildAddIncomeFlow(IncomeFlow inf) {
        String acctNbr = inf.getAcctNbr();
        String incomeCategoryNm = inf.getIncomeCategoryNm();
        if (EmptyUtil.isNullOrEmpty(acctNbr)
                || EmptyUtil.isNullOrEmpty(incomeCategoryNm)
        ) {
            ExceptionCast.castWithCodeAndDesc(CommonErrorCode.USER_DEFINED.getCode(), "新增收入类型,账户/收入类别名称必传");
        }
    }

    private void vaildDeleteIncomeFlow(IncomeFlow inf) {
        String acctNbr = inf.getAcctNbr();
        String incomeYear = inf.getIncomeYear();
        String incomeMonth = inf.getIncomeMonth();
        String incomeDt = inf.getIncomeDt();
        String incomeHms = inf.getIncomeHms();
        if (EmptyUtil.isNullOrEmpty(acctNbr)
                || EmptyUtil.isNullOrEmpty(incomeYear)
                || EmptyUtil.isNullOrEmpty(incomeMonth)
                || EmptyUtil.isNullOrEmpty(incomeDt)
                || EmptyUtil.isNullOrEmpty(incomeHms)
        ) {
            ExceptionCast.castWithCodeAndDesc(CommonErrorCode.USER_DEFINED.getCode(), "删除收入类型,账户/年月日是分秒必传");
        }
    }

    private void vaildUpdateIncomeFlow(IncomeFlow inf) {
        String acctNbr = inf.getAcctNbr();
        String incomeYear = inf.getIncomeYear();
        String incomeMonth = inf.getIncomeMonth();
        String incomeDt = inf.getIncomeDt();
        String incomeHms = inf.getIncomeHms();
        if (EmptyUtil.isNullOrEmpty(acctNbr)
                || EmptyUtil.isNullOrEmpty(incomeYear)
                || EmptyUtil.isNullOrEmpty(incomeMonth)
                || EmptyUtil.isNullOrEmpty(incomeDt)
                || EmptyUtil.isNullOrEmpty(incomeHms)
        ) {
            ExceptionCast.castWithCodeAndDesc(CommonErrorCode.USER_DEFINED.getCode(), "更新收入类型,账户/年月日是分秒必传");
        }
    }

    private String getIncomeCategoryId(IncomeFlow inf) {
        IncomeCategory incomeCategory = new IncomeCategory();
        incomeCategory.setIncomeCategoryId(inf.getIncomeCategoryId());
        incomeCategory.setIncomeCategoryNm(inf.getIncomeCategoryNm());
        incomeCategory.setAcctNbr(inf.getAcctNbr());

        List<IncomeCategory> incomeCategories = incomeCategoryService.selectIncomeCategoryByCnd(incomeCategory);

        if (EmptyUtil.isNullOrEmpty(incomeCategories)) {
            ExceptionCast.cast(CommonErrorCode.IC_SELECT);
        }
        return incomeCategories.get(0).getIncomeCategoryId();
    }

    @Override
    public boolean deleteIncomeFlow(IncomeFlow inf) {
        vaildDeleteIncomeFlow(inf);
        QueryWrapper<IncomeFlow> wrapper = new QueryWrapper<>();

        wrapper.lambda().eq(IncomeFlow::getAcctNbr, inf.getAcctNbr())
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
        vaildUpdateIncomeFlow(inf);
        IncomeFlow inquire = new IncomeFlow();
        inquire.setAcctNbr(inf.getAcctNbr());
        inquire.setIncomeCategoryId(inf.getIncomeCategoryId());
        inquire.setIncomeYear(inf.getIncomeYear());
        inquire.setIncomeMonth(inquire.getIncomeMonth());
        inquire.setIncomeDt(inquire.getIncomeDt());
        inquire.setIncomeHms(inquire.getIncomeHms());

        List<IncomeFlow> incomeFlows = this.selectIncomeFlowByCnd(inquire);

        LambdaUpdateWrapper<IncomeFlow> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(IncomeFlow::getAcctNbr, inf.getAcctNbr())
                .eq(IncomeFlow::getIncomeYear, inf.getIncomeYear())
                .eq(IncomeFlow::getIncomeMonth, inf.getIncomeMonth())
                .eq(IncomeFlow::getIncomeDt, inf.getIncomeDt())
                .eq(IncomeFlow::getIncomeHms, inf.getIncomeHms());

        if (!EmptyUtil.isNullOrEmpty(inf.getIncomeAmt())) {
            wrapper.set(IncomeFlow::getIncomeAmt, inf.getIncomeAmt());

            BigDecimal incomeAmt = incomeFlows.get(0).getIncomeAmt();

            CurrentAmt ca = new CurrentAmt();
            ca.setAcctNbr(inf.getAcctNbr());
            ca.setIncomeCategoryId(inf.getIncomeCategoryId());

            List<CurrentAmt> currentAmts = currentAmtService.selectCurrentAmtByCnd(ca);

            ca.setCurrentAmt(currentAmts.get(0).getCurrentAmt().subtract(incomeAmt).add(inf.getIncomeAmt()));

            currentAmtService.updateCurrentAmt(ca);
        }

        if (!EmptyUtil.isNullOrEmpty(inf.getRemark())) {
            wrapper.set(IncomeFlow::getRemark, inf.getRemark());
        }

        boolean update = this.update(wrapper);
        if (!update) {
            ExceptionCast.cast(CommonErrorCode.IF_UPDATE);
        }

        return true;
    }

    @Override
    public List<IncomeFlow> selectIncomeFlowList() {
        List<IncomeFlow> list = this.list();

        _handleResult(list);

        return list;
    }

    private void _handleResult(List<IncomeFlow> list) {
        if (!EmptyUtil.isNullOrEmpty(list)) {
            for (IncomeFlow incomeFlow : list) {
                String acctNbr = incomeFlow.getAcctNbr();
                String incomeCategoryId = incomeFlow.getIncomeCategoryId();
                IncomeCategory incomeCategory = new IncomeCategory();
                incomeCategory.setIncomeCategoryId(incomeCategoryId);
                incomeCategory.setAcctNbr(acctNbr);

                List<IncomeCategory> incomeCategories = incomeCategoryService.selectIncomeCategoryByCnd(incomeCategory);
                incomeFlow.setIncomeCategoryNm(EmptyUtil.isNullOrEmpty(incomeCategories) ? null : incomeCategories.get(0).getIncomeCategoryNm());
            }
        }
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

        List<IncomeFlow> list = this.baseMapper.selectList(wrapperLambda);

        _handleResult(list);

        return list;
    }

    @Override
    public InquireFlowInfoOut getCurrentInfo(InquireFlowInfoIn in) {
        InquireFlowInfoOut inquireFlowInfoOut = new InquireFlowInfoOut();
        List<PaidFlow> paidFlows = paidFlowService.inquirePaidFlowByCnd(in);

        //支出分类,支出金额
        Map<String, BigDecimal> currentPaidAmtDetailMap = new HashMap<>();

        //收入分类,收入金额
        Map<String, BigDecimal> currentIncomeAmtDetailMap = new HashMap<>();

        //当前支出金额
        BigDecimal currentTotalPaidAmt = BigDecimal.ZERO;

        //当前收入金额
        BigDecimal currentTotalIncomeAmt = BigDecimal.ZERO;

        Set<String> paidMethodList = new HashSet<>();
        if (!EmptyUtil.isNullOrEmpty(paidFlows)) {
            for (PaidFlow paidFlow : paidFlows) {
                currentTotalPaidAmt = currentTotalPaidAmt.add(paidFlow.getPaidAmt());
                paidMethodList.add(paidFlow.getPaidMethod());

                if (currentPaidAmtDetailMap.containsKey(paidFlow.getPaidCategoryId())) {
                    BigDecimal bigDecimal = currentPaidAmtDetailMap.get(paidFlow.getPaidCategoryId());
                    bigDecimal = bigDecimal.add(paidFlow.getPaidAmt());
                    currentPaidAmtDetailMap.put(getPaidCategoryNm(paidFlow), bigDecimal);
                } else {
                    currentPaidAmtDetailMap.put(getPaidCategoryNm(paidFlow), paidFlow.getPaidAmt());
                }
            }
        }

        if (!EmptyUtil.isNullOrEmpty(paidMethodList) && !EmptyUtil.isNullOrEmpty(in.getIncomeCategoryList())) {
            paidMethodList.retainAll(in.getIncomeCategoryList());
        }

        in.setIncomeCategoryList(new ArrayList<>(paidMethodList));

        List<IncomeFlow> incomeFlows = incomeFlowService.inquirePaidFlowByCnd(in);

        if (!EmptyUtil.isNullOrEmpty(incomeFlows)) {
            for (IncomeFlow incomeFlow : incomeFlows) {
                currentTotalIncomeAmt = currentTotalIncomeAmt.add(incomeFlow.getIncomeAmt());

                if (currentIncomeAmtDetailMap.containsKey(incomeFlow.getIncomeCategoryId())) {
                    BigDecimal bigDecimal = currentIncomeAmtDetailMap.get(incomeFlow.getIncomeCategoryId());
                    bigDecimal = bigDecimal.add(incomeFlow.getIncomeAmt());
                    currentIncomeAmtDetailMap.put(getIncomeCategoryNm(incomeFlow), bigDecimal);
                } else {
                    currentIncomeAmtDetailMap.put(getIncomeCategoryNm(incomeFlow), incomeFlow.getIncomeAmt());
                }
            }
        }

        inquireFlowInfoOut.setCurrentTotalIncomeAmt(currentTotalIncomeAmt);
        inquireFlowInfoOut.setCurrentIncomeAmtDetailMap(currentIncomeAmtDetailMap);
        inquireFlowInfoOut.setCurrentTotalPaidAmt(currentTotalPaidAmt);
        inquireFlowInfoOut.setCurrentPaidAmtDetailMap(currentPaidAmtDetailMap);

        return inquireFlowInfoOut;
    }

    private String getPaidCategoryNm(PaidFlow pf) {
        PaidCategory paidCategory = new PaidCategory();
        paidCategory.setPaidCategoryId(pf.getPaidCategoryId());
        paidCategory.setAcctNbr(pf.getAcctNbr());
        paidCategory.setPaidCategoryNm(pf.getPaidCategoryNm());

        List<PaidCategory> paidCategoryInfo = paidCategoryService.selectPaidCategoryByCnd(paidCategory);

        if (EmptyUtil.isNullOrEmpty(paidCategoryInfo)) {
            ExceptionCast.cast(CommonErrorCode.PC_SELECT);
        }
        return paidCategoryInfo.get(0).getPaidCategoryNm();
    }

    private String getIncomeCategoryNm(IncomeFlow incomeFlow) {
        IncomeCategory incomeCategory = new IncomeCategory();
        incomeCategory.setIncomeCategoryId(incomeFlow.getIncomeCategoryId());
        incomeCategory.setAcctNbr(incomeFlow.getAcctNbr());

        List<IncomeCategory> incomeCategories = incomeCategoryService.selectIncomeCategoryByCnd(incomeCategory);

        if (EmptyUtil.isNullOrEmpty(incomeCategories)) {
            ExceptionCast.cast(CommonErrorCode.IC_SELECT);
        }
        return incomeCategories.get(0).getIncomeCategoryNm();
    }

    @Override
    public List<IncomeFlow> inquirePaidFlowByCnd(InquireFlowInfoIn in) {
        String startDt = in.getStartDt();
        String endDt = in.getEndDt();
        LambdaUpdateWrapper<IncomeFlow> wrapper = new LambdaUpdateWrapper<>();

        if (!EmptyUtil.isNullOrEmpty(in.getIncomeCategoryList())) {
            wrapper.in(IncomeFlow::getIncomeCategoryId, in.getIncomeCategoryList());
        }

        wrapper.between(IncomeFlow::getIncomeYear, EmptyUtil.isNullOrEmpty(startDt) ? "0" : startDt.substring(0, 4), EmptyUtil.isNullOrEmpty(endDt) ? "9999" : endDt.substring(0, 4));

        wrapper.between(IncomeFlow::getIncomeMonth, EmptyUtil.isNullOrEmpty(startDt) ? "0" : startDt.substring(4, 6), EmptyUtil.isNullOrEmpty(endDt) ? "9999" : endDt.substring(4, 6));

        wrapper.between(IncomeFlow::getIncomeDt, EmptyUtil.isNullOrEmpty(startDt) ? "0" : startDt.substring(4, 8), EmptyUtil.isNullOrEmpty(endDt) ? "9999" : endDt.substring(4, 8));

        return this.baseMapper.selectList(wrapper);
    }
}
