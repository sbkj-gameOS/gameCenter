package com.bradypod.core.engine.game.action;

import org.apache.commons.lang3.StringUtils;

import com.bradypod.core.BMDataContext;
import com.bradypod.core.engine.game.task.CreateRaiseHandsTask;
import com.bradypod.core.statemachine.action.Action;
import com.bradypod.core.statemachine.impl.BeiMiExtentionTransitionConfigurer;
import com.bradypod.core.statemachine.message.Message;
import com.bradypod.util.cache.CacheHelper;
import com.bradypod.web.model.GameRoom;

/**
 * 反底牌发给地主
 * @author iceworld
 *
 * @param <T>
 * @param <S>
 */
public class RaiseHandsAction<T,S> implements Action<T, S>{

	@Override
	public void execute(Message<T> message, BeiMiExtentionTransitionConfigurer<T,S> configurer) {
		String room = (String)message.getMessageHeaders().getHeaders().get("room") ;
		if(!StringUtils.isBlank(room)){
			GameRoom gameRoom = (GameRoom) CacheHelper.getGameRoomCacheBean().getCacheObject(room, BMDataContext.SYSTEM_ORGI) ; 
			if(gameRoom!=null){
				CacheHelper.getExpireCache().put(gameRoom.getRoomid(), new CreateRaiseHandsTask(0 , gameRoom , gameRoom.getOrgi()));
			}
		}
	}
}
