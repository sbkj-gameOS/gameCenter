package com.bradypod.web.handler.mobileter.transact;

import com.alibaba.fastjson.JSONObject;
import com.bradypod.util.BeanUtils;
import com.bradypod.web.handler.Handler;
import com.bradypod.web.model.PresentApp;
import com.bradypod.web.model.RoomRechargeRecord;
import com.bradypod.web.model.RoomTouseRecord;
import com.bradypod.web.model.mobileter.murecharge.vo.PlayUserVo;
import com.bradypod.web.model.mobileter.murecharge.vo.RoomRechargeVo;
import com.bradypod.web.service.repository.jpa.RoomRechargeRecordRepository;
import com.bradypod.web.service.repository.jpa.RoomTouseRecordRepository;
import com.bradypod.web.service.repository.spec.DefaultSpecification;
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
import java.text.SimpleDateFormat;
import java.util.*;

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
    public ModelAndView rechargeRecord(ModelMap map, HttpServletRequest request){
        return request(super.createAppsTempletResponse("/apps/business/platform/room/recharge/index"));
    }

    /**
     * 房卡充值记录数据
     * @return
     */
    @RequestMapping({"/getRechargeJson"})
    @ResponseBody
    public JSONObject getRecharge(String startTime,String endTime,String userName,String invitationCode,Integer page,Integer limit){
        Map<Object,Object> dataMap = new HashMap<Object,Object>();
        try{
            Pageable pageable = new PageRequest(page-1, limit);
            DefaultSpecification<RoomRechargeRecord> spec = new DefaultSpecification<RoomRechargeRecord>();

            if (userName != null && !userName.equals("")) spec.setParams("userName", "like", "%" + userName + "%");

            if (invitationCode != null && !invitationCode.equals("")) spec.setParams("invitationCode", "like", "%" + invitationCode + "%");

            if (startTime != null && !startTime.equals("")) spec.setParams("rechargeTime", ">=", startTime);

            if (endTime != null && !endTime.equals("")) spec.setParams("rechargeTime", "<=", endTime);

            Page<RoomRechargeRecord> recharge = roomRechargeRecordRepository.findAll(spec, pageable);
            List<RoomRechargeVo> rechargeVo = new ArrayList<RoomRechargeVo>();
            for(RoomRechargeRecord rechargeList:recharge.getContent()){
                RoomRechargeVo vo = new RoomRechargeVo();
                BeanUtils.copyProperties(rechargeList, vo);
                vo.setRechargeTimes(rechargeList.getRechargeTime().toString().substring(0,rechargeList.getRechargeTime().toString().length()-2));
                rechargeVo.add(vo);
            }
            dataMap.put("data",rechargeVo);
            dataMap.put("count", recharge.getTotalElements());
            dataMap.put("code",0);
        }catch(Exception e){
            e.printStackTrace();
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
    public JSONObject getToUseJson(String userName,String invitationCode,Integer page,Integer limit){
        Map<Object,Object> dataMap = new HashMap<Object,Object>();
        List<RoomTouseRecord> toUse = new ArrayList<RoomTouseRecord>();
        try{
            int totalPage,startData;
            int total = roomTouseRecordRepository.findToUseCount(userName,invitationCode);
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
            }else {
                // 开始条数
                startData = (page-1) * limit;
                toUse = roomTouseRecordRepository.findByUserNameLikeOrInvitationCodeLike(userName,invitationCode,startData,limit);
            }
            dataMap.put("data",toUse);
            dataMap.put("count",total);
            dataMap.put("code",0);
        }catch(Exception e){
            dataMap.put("code",1);
            dataMap.put("msg","网络异常");
        }
        return (JSONObject) JSONObject.toJSON(dataMap);
    }

}
