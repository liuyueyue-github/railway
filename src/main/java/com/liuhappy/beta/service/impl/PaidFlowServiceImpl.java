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
import com.liuhappy.beta.service.CurrentAmtService;
import com.liuhappy.beta.service.PaidCategoryService;
import com.liuhappy.beta.service.PaidFlowService;
import com.liuhappy.beta.service.UserService;
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
    private CurrentAmtService currentAmtService;

    @Override
    public PaidFlow addPaidFlow(PaidFlow pf) {
        String paidCategoryId = pf.getPaidCategoryId();

        PaidCategory paidCategory = new PaidCategory();
        paidCategory.setPaidCategoryId(paidCategoryId);
        paidCategory.setAcctNbr(pf.getAcctNbr());

        List<PaidCategory> paidCategoryInfo = paidCategoryService.selectPaidCategoryByCnd(paidCategory);

        if (EmptyUtil.isNullOrEmpty(paidCategoryInfo)) {
            ExceptionCast.cast(CommonErrorCode.PC_SELECT);
        }

        String currentDt = DateUtils.dateTimeNow(YYYYMMDDHHMMSS);
        pf.setPaidYear(currentDt.substring(0, 4));
        pf.setPaidMonth(currentDt.substring(4, 6));
        pf.setPaidDt(currentDt.substring(4, 8));
        pf.setPaidHms(currentDt.substring(8, 14));
        this.save(pf);

        CurrentAmt currentAmt = new CurrentAmt();
        currentAmt.setAcctNbr(pf.getAcctNbr());
        currentAmt.setIncomeCategoryId(pf.getPaidMethod());
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

    @Override
    public boolean deletePaidFlow(PaidFlow pf) {

        List<PaidFlow> paidFlows = this.selectPaidFlowByCnd(pf);

        QueryWrapper<PaidFlow> paidFlowQueryWrapper = new QueryWrapper<>();

        paidFlowQueryWrapper.lambda().eq(PaidFlow::getAcctNbr, pf.getAcctNbr())
                .eq(PaidFlow::getPaidCategoryId, pf.getPaidCategoryId())
                .eq(PaidFlow::getPaidYear, pf.getPaidYear())
                .eq(PaidFlow::getPaidMonth, pf.getPaidMonth())
                .eq(PaidFlow::getPaidDt, pf.getPaidDt())
                .eq(PaidFlow::getPaidHms, pf.getPaidHms());

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
        List<PaidFlow> paidFlows = this.selectPaidFlowByCnd(pf);

        LambdaUpdateWrapper<PaidFlow> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(PaidFlow::getAcctNbr, pf.getAcctNbr())
                .eq(PaidFlow::getPaidCategoryId, pf.getPaidCategoryId())
                .eq(PaidFlow::getPaidYear, pf.getPaidYear())
                .eq(PaidFlow::getPaidMonth, pf.getPaidMonth())
                .eq(PaidFlow::getPaidDt, pf.getPaidDt())
                .eq(PaidFlow::getPaidHms, pf.getPaidHms())
                .set(PaidFlow::getPaidAmt, pf.getPaidAmt())
                .set(PaidFlow::getRemark, pf.getRemark());

        boolean update = this.update(wrapper);
        if (!update) {
            ExceptionCast.cast(CommonErrorCode.PF_UPDATE);
        }

        BigDecimal incomeAmt = paidFlows.get(0).getPaidAmt();

        CurrentAmt ca = new CurrentAmt();
        ca.setAcctNbr(pf.getAcctNbr());
        ca.setIncomeCategoryId(paidFlows.get(0).getPaidMethod());

        List<CurrentAmt> currentAmts = currentAmtService.selectCurrentAmtByCnd(ca);

        ca.setCurrentAmt(currentAmts.get(0).getCurrentAmt().add(incomeAmt).subtract(pf.getPaidAmt()));

        currentAmtService.updateCurrentAmt(ca);

        return true;
    }

    @Override
    public List<PaidFlow> selectPaidFlowList() {
        return this.list();
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

        return this.baseMapper.selectList(paidFlowQueryWrapper);
    }
}
