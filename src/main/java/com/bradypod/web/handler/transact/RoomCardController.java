package com.bradypod.web.handler.transact;

import com.bradypod.web.handler.Handler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 房卡记录
 * Created by 田帅 on 2017/9/25/025.
 */
@Controller
@RequestMapping("/roomCard")
public class RoomCardController extends Handler {

    /**
     * 房卡充值记录跳转
     * @return
     */
    @RequestMapping({"/rechargeRecord"})
    public ModelAndView rechargeRecord(){
        ModelAndView mv = new ModelAndView("/apps/platform/room/recharge/index");
        return mv;
    }

    /**
     * 房卡使用记录跳转
     * @return
     */
    @RequestMapping({"/toUseRecord"})
    public ModelAndView toUseRecord(){
        ModelAndView mv = new ModelAndView("/apps/platform/use/index");
        return mv;
    }
}
