package com.bradypod.core.engine.game.action;

import org.apache.commons.lang3.StringUtils;

import com.bradypod.core.BMDataContext;
import com.bradypod.core.engine.game.task.CreatePlayCardsTask;
import com.bradypod.core.statemachine.action.Action;
import com.bradypod.core.statemachine.impl.BeiMiExtentionTransitionConfigurer;
import com.bradypod.core.statemachine.message.Message;
import com.bradypod.util.cache.CacheHelper;
import com.bradypod.util.rules.model.Board;
import com.bradypod.web.model.GameRoom;

/**
 * 凑够了，开牌
 * @author iceworld
 *
 */
public class PlayCardsAction<T,S> implements Action<T, S>{
	@Override
	public void execute(Message<T> message , BeiMiExtentionTransitionConfigurer<T,S> configurer) {
		String room = (String)message.getMessageHeaders().getHeaders().get("room") ;
		if(!StringUtils.isBlank(room)){
			GameRoom gameRoom = (GameRoom) CacheHelper.getGameRoomCacheBean().getCacheObject(room, BMDataContext.SYSTEM_ORGI) ; 
			if(gameRoom!=null){
				Board board = (Board) CacheHelper.getBoardCacheBean().getCacheObject(gameRoom.getId(), gameRoom.getOrgi()) ;
				int interval = (int) message.getMessageHeaders().getHeaders().get("interval") ;
				String nextPlayer = board.getBanker();
				if(!StringUtils.isBlank(board.getNextplayer())){
					nextPlayer = board.getNextplayer() ;
				}
				CacheHelper.getExpireCache().put(gameRoom.getRoomid(), new CreatePlayCardsTask(interval , nextPlayer , gameRoom , gameRoom.getOrgi()));
			}
		}
	}
}
