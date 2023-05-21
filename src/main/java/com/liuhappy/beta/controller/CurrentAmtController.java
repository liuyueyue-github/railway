package com.liuhappy.beta.controller;

import com.liuhappy.beta.controller.dto.CurrentAmtInfo;
import com.liuhappy.beta.service.CurrentAmtService;
import com.liuhappy.beta.vo.CurrentAmt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Grin
 * @Date 2022/7/26
 * @Description
 */
@RestController
@RequestMapping("/ca")
public class CurrentAmtController {
    @Autowired
    private CurrentAmtService currentAmtService;

    @PostMapping("/addCurrentAmt")
    public CurrentAmt addCurrentAmt(@RequestBody CurrentAmt ca) {
        return currentAmtService.addCurrentAmt(ca);
    }

    @PostMapping("/deleteCurrentAmt")
    public boolean deleteCurrentAmt(@RequestBody CurrentAmt ca) {
        return currentAmtService.deleteCurrentAmt(ca);
    }

    @PostMapping("/updateCurrentAmt")
    public boolean updateCurrentAmt(@RequestBody CurrentAmt ca) {
        return currentAmtService.updateCurrentAmt(ca);
    }

    @PostMapping("/selectCurrentAmtByCnd")
    public List<CurrentAmt> selectCurrentAmtByCnd(@RequestBody CurrentAmt ca) {
        return currentAmtService.selectCurrentAmtByCnd(ca);
    }

    @PostMapping("/selectCurrentAmtList")
    public CurrentAmtInfo selectCurrentAmtList() {
        return currentAmtService.selectCurrentAmtList();
    }
}
