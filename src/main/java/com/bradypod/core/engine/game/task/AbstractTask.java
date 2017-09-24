package com.bradypod.core.engine.game.task;

import org.cache2k.expiry.ValueWithExpiryTime;

import com.bradypod.config.web.model.Game;
import com.bradypod.core.engine.game.ActionTaskUtils;
import com.bradypod.util.UKTools;
import com.bradypod.web.model.GameRoom;

public abstract class AbstractTask implements ValueWithExpiryTime {
	protected Game game ;

	public AbstractTask(){
		game = ActionTaskUtils.game();
	}
	
	public void sendEvent(String event , Object data , GameRoom gameRoom){
		ActionTaskUtils.sendEvent(event, data, gameRoom);
	}
	
	public Object json(Object data){
		return UKTools.json(data) ;
	}

}
