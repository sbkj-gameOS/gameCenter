package com.bradypod.web.handler.mobileter.transact;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bradypod.web.handler.Handler;
import com.bradypod.web.service.repository.jpa.RoomRechargeRecordRepository;
import com.bradypod.web.service.repository.jpa.RoomTouseRecordRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 房卡记录
 * Created by 田帅 on 2017/9/25/025.
 */
@Controller
@RequestMapping("/roomCard")
public class RoomCardController extends Handler {

    @Autowired
    private RoomRechargeRecordRepository roomRechargeRecordRepository;

    @Autowired
    private RoomTouseRecordRepository roomTouseRecordRepository;
    /**
     * 房卡充值记录跳转
     * @return
     */
    @RequestMapping({"/rechargeRecord"})
    public ModelAndView rechargeRecord(ModelMap map , HttpServletRequest request){
        map.addAttribute("data",roomRechargeRecordRepository.findByUserNameAndInvitationCode(null,null));
        return request(super.createAppsTempletResponse("/apps/business/platform/room/recharge/index"));
    }

    @RequestMapping({"/getRechargeJson"})
    @ResponseBody
    public JSONObject getRecharge(){
        Map<Object,Object> dataMap = new HashMap<Object,Object>();
        dataMap.put("data",roomRechargeRecordRepository.findByUserNameAndInvitationCode("",""));
        dataMap.put("count",2);
        dataMap.put("code",0);
        dataMap.put("msg","");
        return (JSONObject) JSONObject.toJSON(dataMap);
    }

    /**
     * 房卡使用记录跳转
     * @return
     */
    @RequestMapping({"/toUseRecord"})
    public ModelAndView toUseRecord(ModelMap map , HttpServletRequest request){
        map.addAttribute("RoomTouseRecord",roomTouseRecordRepository.findByUserNameAndInvitationCode("t","123"));
        return request(super.createAppsTempletResponse("/apps/business/platform/room/use/index"));
    }
}
