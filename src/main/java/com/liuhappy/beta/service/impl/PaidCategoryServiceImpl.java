package com.liuhappy.beta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhappy.beta.common.EmptyUtil;
import com.liuhappy.beta.enums.impl.CommonErrorCode;
import com.liuhappy.beta.exception.ExceptionCast;
import com.liuhappy.beta.mapper.PaidCategoryMapper;
import com.liuhappy.beta.service.CurrentAmtService;
import com.liuhappy.beta.service.PaidCategoryService;
import com.liuhappy.beta.service.PaidFlowService;
import com.liuhappy.beta.vo.CurrentAmt;
import com.liuhappy.beta.vo.PaidCategory;
import com.liuhappy.beta.vo.PaidFlow;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service("paidCategoryService")
public class PaidCategoryServiceImpl extends ServiceImpl<PaidCategoryMapper, PaidCategory> implements PaidCategoryService {
    private static final String STR_INIT_SEQ = "0001";

    private static final String PD = "PC";

    @Autowired
    private PaidFlowService paidFlowService;

    @Autowired
    private CurrentAmtService currentAmtService;

    @Override
    @Transactional
    public PaidCategory addPaidCategory(PaidCategory pc) {
        int maxPdId = this.baseMapper.selectMaxPcId(Calendar.getInstance().get(Calendar.YEAR) + STR_INIT_SEQ);
        String pcId = PD + maxPdId;
        pc.setPaidCategoryId(pcId);

        List<PaidCategory> paidCategories = this.selectPaidCategoryByCnd(pc);

        if(!EmptyUtil.isNullOrEmpty(paidCategories)){
            ExceptionCast.cast(CommonErrorCode.PC_ALREADY_EXIST);
        }

        boolean save = this.save(pc);
        if (!save) {
            ExceptionCast.cast(CommonErrorCode.PC_ADD);
        }
        return pc;
    }

    @Override
    public boolean deletePaidCategory(PaidCategory pc) {
        LambdaQueryWrapper<PaidCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PaidCategory::getAcctNbr, pc.getAcctNbr())
                .eq(PaidCategory::getPaidCategoryId, pc.getPaidCategoryId());
        int delete = this.baseMapper.delete(wrapper);
        if (delete == 0) {
            ExceptionCast.cast(CommonErrorCode.PC_DELETE);
        }
        PaidFlow pf = new PaidFlow();
        pf.setAcctNbr(pc.getAcctNbr());
        pf.setPaidCategoryId(pc.getPaidCategoryId());
        List<PaidFlow> paidFlows = paidFlowService.selectPaidFlowByCnd(pf);

        if (!EmptyUtil.isNullOrEmpty(paidFlows)) {
            for (PaidFlow paidFlow : paidFlows) {
                String paidMethod = paidFlow.getPaidMethod();
                BigDecimal paidAmt = paidFlow.getPaidAmt();
                CurrentAmt ca = new CurrentAmt();
                ca.setAcctNbr(pf.getAcctNbr());
                ca.setIncomeCategoryId(paidMethod);

                List<CurrentAmt> currentAmts = currentAmtService.selectCurrentAmtByCnd(ca);

                BigDecimal currentAmt = currentAmts.get(0).getCurrentAmt();
                ca.setCurrentAmt(paidAmt.add(currentAmt));
                currentAmtService.updateCurrentAmt(ca);
            }
        }

        return true;
    }

    @Override
    public boolean updatePaidCategory(PaidCategory pc) {
        LambdaUpdateWrapper<PaidCategory> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(PaidCategory::getPaidCategoryNm, pc.getPaidCategoryNm())
                .eq(PaidCategory::getPaidCategoryId, pc.getPaidCategoryId())
                .eq(PaidCategory::getAcctNbr, pc.getAcctNbr());
        boolean update = this.update(wrapper);

        if (!update) {
            ExceptionCast.cast(CommonErrorCode.PC_UPDATE);
        }
        return true;
    }

    @Override
    public List<PaidCategory> selectPaidCategoryByCnd(PaidCategory pc) {
        LambdaQueryWrapper<PaidCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PaidCategory::getAcctNbr, pc.getAcctNbr());

        if (!EmptyUtil.isNullOrEmpty(pc.getPaidCategoryId())) {
            wrapper.eq(PaidCategory::getPaidCategoryId, pc.getPaidCategoryId());
        }

        if (!EmptyUtil.isNullOrEmpty(pc.getPaidCategoryNm())) {
            wrapper.eq(PaidCategory::getPaidCategoryNm, pc.getPaidCategoryNm());
        }

        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public List<PaidCategory> selectPaidCategoryList() {

        return this.list();
    }
}
