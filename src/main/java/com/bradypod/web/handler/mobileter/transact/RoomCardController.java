package com.bradypod.web.handler.mobileter.transact;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bradypod.web.handler.Handler;
import com.bradypod.web.model.RoomRechargeRecord;
import com.bradypod.web.model.RoomTouseRecord;
import com.bradypod.web.service.repository.jpa.RoomRechargeRecordRepository;
import com.bradypod.web.service.repository.jpa.RoomTouseRecordRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return request(super.createAppsTempletResponse("/apps/business/platform/room/recharge/index"));
    }

    /**
     * 房卡充值记录数据
     * @return
     */
    @RequestMapping({"/getRechargeJson"})
    @ResponseBody
    public JSONObject getRecharge(){
        Map<Object,Object> dataMap = new HashMap<Object,Object>();
        try{
            Pageable Pageable = new PageRequest(1,30);
            Page<RoomRechargeRecord> recharge = roomRechargeRecordRepository.findByUserNameLikeOrInvitationCodeLike("t","1",Pageable);
            dataMap.put("data",recharge.getContent());
            dataMap.put("count",recharge.getTotalElements());
            dataMap.put("code",0);
        }catch(Exception e){
            dataMap.put("code",1);
            dataMap.put("msg","网络异常");
        }
        return (JSONObject) JSONObject.toJSON(dataMap);
    }

    /**
     * 房卡使用记录跳转
     * @return
     */
    @RequestMapping({"/toUseRecord"})
    public ModelAndView toUseRecord(ModelMap map , HttpServletRequest request){
        return request(super.createAppsTempletResponse("/apps/business/platform/room/use/index"));
    }

    /**
     * 房卡使用记录数据
     * @return
     */
    @RequestMapping({"/getToUseJson"})
    @ResponseBody
    public JSONObject getToUseJson(){
        Map<Object,Object> dataMap = new HashMap<Object,Object>();
        try{
            Pageable Pageable = new PageRequest(1,30);
            Page<RoomTouseRecord> touse = roomTouseRecordRepository.findByUserNameLikeAndInvitationCodeLike("t","123",Pageable);
            dataMap.put("data",touse.getContent());
            dataMap.put("count",touse.getTotalElements());
            dataMap.put("code",0);
        }catch(Exception e){
            dataMap.put("code",1);
            dataMap.put("msg","网络异常");
        }
        return (JSONObject) JSONObject.toJSON(dataMap);
    }

}
