package com.bradypod.web.handler.mobileter.transact;

import com.alibaba.fastjson.JSONObject;
import com.bradypod.web.handler.Handler;
import com.bradypod.web.model.RoomRechargeRecord;
import com.bradypod.web.model.RoomTouseRecord;
import com.bradypod.web.service.repository.jpa.RoomRechargeRecordRepository;
import com.bradypod.web.service.repository.jpa.RoomTouseRecordRepository;
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
import java.util.List;
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
    public ModelAndView rechargeRecord(ModelMap map, HttpServletRequest request,Integer status){
        return request(super.createAppsTempletResponse("/apps/business/platform/room/recharge/index"));
    }

    /**
     * 房卡充值记录数据
     * @return
     */
    @RequestMapping({"/getRechargeJson"})
    @ResponseBody
    public JSONObject getRecharge(String userName,String invitationCode,Integer page,Integer limit){
        Map<Object,Object> dataMap = new HashMap<Object,Object>();
        try{
            int totalPage,startData;
            int total = roomRechargeRecordRepository.findRechargeCount(userName,invitationCode);
            if(page  < 1){
                page = 1;
            }
            // 计算总页数,如果能整除，取整除；不能整除，取整除+1
            if(total % limit ==0){
                totalPage = total / limit;
            }else{
                totalPage = total / limit + 1;
            }
            if(totalPage == 0){
                page = 0;
                dataMap.put("data","");
                dataMap.put("count",0);
                dataMap.put("code",0);
            }else {
                // 开始条数
                startData = (page-1) * limit;
                List<RoomRechargeRecord> recharge = roomRechargeRecordRepository.findByUserNameLikeOrInvitationCodeLike(userName,invitationCode,startData,limit);
                dataMap.put("data",recharge);
                dataMap.put("count",total);
                dataMap.put("code",0);
            }
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
    public ModelAndView toUseRecord(ModelMap map , HttpServletRequest request,Integer status){
        map.addAttribute("status",status);
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
