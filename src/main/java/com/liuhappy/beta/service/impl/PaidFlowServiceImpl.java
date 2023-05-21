package com.liuhappy.beta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhappy.beta.common.EmptyUtil;
import com.liuhappy.beta.enums.impl.CommonErrorCode;
import com.liuhappy.beta.exception.ExceptionCast;
import com.liuhappy.beta.mapper.PaidCategoryMapper;
import com.liuhappy.beta.mapper.PaidFlowMapper;
import com.liuhappy.beta.mapper.UserMapper;
import com.liuhappy.beta.service.*;
import com.liuhappy.beta.service.dto.InquireFlowInfoIn;
import com.liuhappy.beta.utils.DateUtils;
import com.liuhappy.beta.vo.*;
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
@Service("paidFlowService")
public class PaidFlowServiceImpl extends ServiceImpl<PaidFlowMapper, PaidFlow> implements PaidFlowService {

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    @Autowired
    @Lazy
    private PaidCategoryService paidCategoryService;

    @Autowired
    @Lazy
    private IncomeCategoryService incomeCategoryService;

    @Autowired
    private CurrentAmtService currentAmtService;

    @Override
    public PaidFlow addPaidFlow(PaidFlow pf) {

        vaildAddPaidFlow(pf);

        IncomeCategory inf = new IncomeCategory();
        inf.setAcctNbr(pf.getAcctNbr());
        inf.setIncomeCategoryNm(pf.getPaidMethod());
        List<IncomeCategory> incomeCategories = incomeCategoryService.selectIncomeCategoryByCnd(inf);

        if (EmptyUtil.isNullOrEmpty(incomeCategories)) {
            ExceptionCast.cast(CommonErrorCode.IC_SELECT);
        }

        String paidCategoryId = _getPaidCategoryId(pf);

        pf.setPaidCategoryId(paidCategoryId);

        String currentDt = DateUtils.dateTimeNow(YYYYMMDDHHMMSS);
        pf.setPaidYear(currentDt.substring(0, 4));
        pf.setPaidMonth(currentDt.substring(4, 6));
        pf.setPaidDt(currentDt.substring(4, 8));
        pf.setPaidHms(currentDt.substring(8, 14));
        pf.setPaidMethod(incomeCategories.get(0).getIncomeCategoryId());
        this.save(pf);

        CurrentAmt currentAmt = new CurrentAmt();
        currentAmt.setAcctNbr(pf.getAcctNbr());
        currentAmt.setIncomeCategoryId(incomeCategories.get(0).getIncomeCategoryId());
        List<CurrentAmt> currentAmts = currentAmtService.selectCurrentAmtByCnd(currentAmt);
        if (EmptyUtil.isNullOrEmpty(currentAmts)) {
            currentAmt.setCurrentAmt(pf.getPaidAmt());
            currentAmtService.addCurrentAmt(currentAmt);
            return pf;
        }

        BigDecimal currentAmtInfo = currentAmts.get(0).getCurrentAmt();

        currentAmt.setCurrentAmt(currentAmtInfo.subtract(pf.getPaidAmt()));

        currentAmtService.updateCurrentAmt(currentAmt);

        return pf;
    }

    private void vaildAddPaidFlow(PaidFlow pf) {
        String paidCategoryNm = pf.getPaidCategoryNm();
        String paidMethod = pf.getPaidMethod();
        String acctNbr = pf.getAcctNbr();
        BigDecimal paidAmt = pf.getPaidAmt();
        if (EmptyUtil.isNullOrEmpty(acctNbr)
                || EmptyUtil.isNullOrEmpty(paidMethod)
                || EmptyUtil.isNullOrEmpty(paidAmt)
                || EmptyUtil.isNullOrEmpty(paidCategoryNm)
        ) {
            ExceptionCast.castWithCodeAndDesc(CommonErrorCode.USER_DEFINED.getCode(), "新增支出流水,账户/支出类别名称/支出金额/收入类别名称必传");
        }
    }

    private void vaildDeletePaidFlow(PaidFlow pf) {
        String paidYear = pf.getPaidYear();
        String paidMonth = pf.getPaidMonth();
        String paidDt = pf.getPaidDt();
        String paidHms = pf.getPaidHms();
        String acctNbr = pf.getAcctNbr();
        if (EmptyUtil.isNullOrEmpty(acctNbr)
                || EmptyUtil.isNullOrEmpty(paidYear)
                || EmptyUtil.isNullOrEmpty(paidMonth)
                || EmptyUtil.isNullOrEmpty(paidDt)
                || EmptyUtil.isNullOrEmpty(paidHms)
        ) {
            ExceptionCast.castWithCodeAndDesc(CommonErrorCode.USER_DEFINED.getCode(), "删除支出流水,账户/年月日时分秒必传");
        }
    }

    private void vaildUpdatePaidFlow(PaidFlow pf) {
        String paidYear = pf.getPaidYear();
        String paidMonth = pf.getPaidMonth();
        String paidDt = pf.getPaidDt();
        String paidHms = pf.getPaidHms();
        String acctNbr = pf.getAcctNbr();
        if (EmptyUtil.isNullOrEmpty(acctNbr)
                || EmptyUtil.isNullOrEmpty(paidYear)
                || EmptyUtil.isNullOrEmpty(paidMonth)
                || EmptyUtil.isNullOrEmpty(paidDt)
                || EmptyUtil.isNullOrEmpty(paidHms)
        ) {
            ExceptionCast.castWithCodeAndDesc(CommonErrorCode.USER_DEFINED.getCode(), "更新支出流水,账户/年月日时分秒必传");
        }
    }

    private String _getPaidCategoryId(PaidFlow pf) {
        PaidCategory paidCategory = new PaidCategory();
        paidCategory.setPaidCategoryId(pf.getPaidCategoryId());
        paidCategory.setAcctNbr(pf.getAcctNbr());
        paidCategory.setPaidCategoryNm(pf.getPaidCategoryNm());

        List<PaidCategory> paidCategoryInfo = paidCategoryService.selectPaidCategoryByCnd(paidCategory);

        if (EmptyUtil.isNullOrEmpty(paidCategoryInfo)) {
            ExceptionCast.cast(CommonErrorCode.PC_SELECT);
        }
        return paidCategoryInfo.get(0).getPaidCategoryId();
    }

    @Override
    public boolean deletePaidFlow(PaidFlow pf) {

        vaildDeletePaidFlow(pf);

        List<PaidFlow> paidFlows = this.selectPaidFlowByCnd(pf);

        QueryWrapper<PaidFlow> paidFlowQueryWrapper = new QueryWrapper<>();

        paidFlowQueryWrapper.lambda().eq(PaidFlow::getAcctNbr, pf.getAcctNbr())
                .eq(PaidFlow::getPaidYear, pf.getPaidYear())
                .eq(PaidFlow::getPaidMonth, pf.getPaidMonth())
                .eq(PaidFlow::getPaidDt, pf.getPaidDt())
                .eq(PaidFlow::getPaidHms, pf.getPaidHms()).eq(PaidFlow::getPaidCategoryId, pf.getPaidCategoryId());

        boolean remove = this.remove(paidFlowQueryWrapper);

        if (!remove) {
            ExceptionCast.cast(CommonErrorCode.PF_DELETE);
        }


        CurrentAmt currentAmt = new CurrentAmt();
        currentAmt.setAcctNbr(pf.getAcctNbr());
        currentAmt.setIncomeCategoryId(paidFlows.get(0).getPaidMethod());
        List<CurrentAmt> currentAmts = currentAmtService.selectCurrentAmtByCnd(currentAmt);

        if (!EmptyUtil.isNullOrEmpty(currentAmts)) {
            currentAmt.setCurrentAmt(currentAmts.get(0).getCurrentAmt().add(paidFlows.get(0).getPaidAmt()));
            currentAmtService.updateCurrentAmt(currentAmt);
        }

        return remove;
    }

    @Override
    public boolean updatePaidFlow(PaidFlow pf) {
        vaildUpdatePaidFlow(pf);
        List<PaidFlow> paidFlows = this.selectPaidFlowByCnd(pf);

        LambdaUpdateWrapper<PaidFlow> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(PaidFlow::getAcctNbr, pf.getAcctNbr())
                .eq(PaidFlow::getPaidCategoryId, pf.getPaidCategoryId())
                .eq(PaidFlow::getPaidYear, pf.getPaidYear())
                .eq(PaidFlow::getPaidMonth, pf.getPaidMonth())
                .eq(PaidFlow::getPaidDt, pf.getPaidDt())
                .eq(PaidFlow::getPaidHms, pf.getPaidHms());

        if (!EmptyUtil.isNullOrEmpty(pf.getPaidAmt())) {
            wrapper.set(PaidFlow::getPaidAmt, pf.getPaidAmt());
        }

        String incomeCategoryId = "";
        if (!EmptyUtil.isNullOrEmpty(pf.getPaidMethod())) {
            IncomeCategory inf = new IncomeCategory();
            inf.setAcctNbr(pf.getAcctNbr());
            inf.setIncomeCategoryNm(pf.getPaidMethod());
            List<IncomeCategory> incomeCategories = incomeCategoryService.selectIncomeCategoryByCnd(inf);
            if (EmptyUtil.isNullOrEmpty(incomeCategories)) {
                ExceptionCast.cast(CommonErrorCode.IC_SELECT);
            }
            incomeCategoryId = incomeCategories.get(0).getIncomeCategoryId();

            wrapper.set(PaidFlow::getPaidMethod, incomeCategoryId);
        }
        if (!EmptyUtil.isNullOrEmpty(pf.getRemark())) {
            wrapper.set(PaidFlow::getRemark, pf.getRemark());
        }

        boolean update = this.update(wrapper);
        if (!update) {
            ExceptionCast.cast(CommonErrorCode.PF_UPDATE);
        }

        if (!EmptyUtil.isNullOrEmpty(pf.getPaidAmt())) {

            BigDecimal incomeAmt = paidFlows.get(0).getPaidAmt();

            CurrentAmt ca = new CurrentAmt();
            ca.setAcctNbr(pf.getAcctNbr());
            ca.setIncomeCategoryId(paidFlows.get(0).getPaidMethod());

            List<CurrentAmt> currentAmts = currentAmtService.selectCurrentAmtByCnd(ca);

            if (EmptyUtil.isNullOrEmpty(pf.getPaidMethod())) {
                ca.setCurrentAmt(currentAmts.get(0).getCurrentAmt().add(incomeAmt).subtract(pf.getPaidAmt()));
                currentAmtService.updateCurrentAmt(ca);
            } else {
                if (incomeCategoryId.equals(paidFlows.get(0).getPaidMethod())) {
                    ca.setCurrentAmt(currentAmts.get(0).getCurrentAmt().add(incomeAmt).subtract(pf.getPaidAmt()));
                    currentAmtService.updateCurrentAmt(ca);
                } else {
                    ca.setCurrentAmt(currentAmts.get(0).getCurrentAmt().add(incomeAmt));
                    currentAmtService.updateCurrentAmt(ca);

                    ca.setIncomeCategoryId(incomeCategoryId);
                    List<CurrentAmt> currentAmtList = currentAmtService.selectCurrentAmtByCnd(ca);
                    ca.setCurrentAmt(currentAmtList.get(0).getCurrentAmt().subtract(incomeAmt));
                    currentAmtService.updateCurrentAmt(ca);
                }
            }
        }

        return true;
    }

    @Override
    public List<PaidFlow> selectPaidFlowList() {
        List<PaidFlow> list = this.list();
        _handleList(list);
        return list;
    }

    @Override
    public List<PaidFlow> selectPaidFlowByCnd(PaidFlow pf) {
        LambdaQueryWrapper<PaidFlow> paidFlowQueryWrapper = new LambdaQueryWrapper<>();
        paidFlowQueryWrapper.eq(PaidFlow::getAcctNbr, pf.getAcctNbr());
        if (!EmptyUtil.isNullOrEmpty(pf.getPaidCategoryId())) {
            paidFlowQueryWrapper.eq(PaidFlow::getPaidCategoryId, pf.getPaidCategoryId());
        }
        if (!EmptyUtil.isNullOrEmpty(pf.getPaidYear())) {
            paidFlowQueryWrapper.eq(PaidFlow::getPaidYear, pf.getPaidYear());
        }
        if (!EmptyUtil.isNullOrEmpty(pf.getPaidMonth())) {
            paidFlowQueryWrapper.eq(PaidFlow::getPaidMonth, pf.getPaidMonth());
        }
        if (!EmptyUtil.isNullOrEmpty(pf.getPaidDt())) {
            paidFlowQueryWrapper.eq(PaidFlow::getPaidDt, pf.getPaidDt());
        }
        if (!EmptyUtil.isNullOrEmpty(pf.getPaidDt())) {
            paidFlowQueryWrapper.eq(PaidFlow::getPaidDt, pf.getPaidDt());
        }

        if (!EmptyUtil.isNullOrEmpty(pf.getPaidMethod())) {
            paidFlowQueryWrapper.eq(PaidFlow::getPaidMethod, pf.getPaidMethod());
        }
        List<PaidFlow> list = this.baseMapper.selectList(paidFlowQueryWrapper);

        _handleList(list);

        return list;
    }

    @Override
    public List<PaidFlow> inquirePaidFlowByCnd(InquireFlowInfoIn in) {
        String startDt = in.getStartDt();
        String endDt = in.getEndDt();
        LambdaUpdateWrapper<PaidFlow> wrapper = new LambdaUpdateWrapper<>();

        if (!EmptyUtil.isNullOrEmpty(in.getPaidCategoryList())) {
            wrapper.in(PaidFlow::getPaidCategoryId, in.getPaidCategoryList());
        }

        wrapper.between(PaidFlow::getPaidYear, EmptyUtil.isNullOrEmpty(startDt) ? "0" : startDt.substring(0, 4), EmptyUtil.isNullOrEmpty(endDt) ? "9999" : endDt.substring(0, 4));

        wrapper.between(PaidFlow::getPaidMonth, EmptyUtil.isNullOrEmpty(startDt) ? "0" : startDt.substring(4, 6), EmptyUtil.isNullOrEmpty(endDt) ? "9999" : endDt.substring(4, 6));

        wrapper.between(PaidFlow::getPaidDt, EmptyUtil.isNullOrEmpty(startDt) ? "0" : startDt.substring(4, 8), EmptyUtil.isNullOrEmpty(endDt) ? "9999" : endDt.substring(4, 8));

        return this.baseMapper.selectList(wrapper);
    }

    private void _handleList(List<PaidFlow> list) {
        if (!EmptyUtil.isNullOrEmpty(list)) {
            for (PaidFlow paidFlow : list) {
                String acctNbr = paidFlow.getAcctNbr();
                String paidCategoryId = paidFlow.getPaidCategoryId();
                PaidCategory paidCategory = new PaidCategory();
                paidCategory.setPaidCategoryId(paidCategoryId);
                paidCategory.setAcctNbr(acctNbr);

                List<PaidCategory> paidCategoryInfo = paidCategoryService.selectPaidCategoryByCnd(paidCategory);
                paidFlow.setPaidCategoryNm(EmptyUtil.isNullOrEmpty(paidCategoryInfo) ? null : paidCategoryInfo.get(0).getPaidCategoryNm());

                IncomeCategory inf = new IncomeCategory();
                inf.setAcctNbr(acctNbr);
                inf.setIncomeCategoryId(paidFlow.getPaidMethod());
                List<IncomeCategory> incomeCategories = incomeCategoryService.selectIncomeCategoryByCnd(inf);

                paidFlow.setPaidMethod(EmptyUtil.isNullOrEmpty(incomeCategories) ? null : incomeCategories.get(0).getIncomeCategoryNm());
            }
        }
    }
}
