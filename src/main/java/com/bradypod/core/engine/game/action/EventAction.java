package com.bradypod.core.engine.game.action;

import com.bradypod.core.statemachine.action.Action;
import com.bradypod.core.statemachine.impl.BeiMiExtentionTransitionConfigurer;
import com.bradypod.core.statemachine.message.Message;

public class EventAction<T,S> implements Action<T, S>{

	@Override
	public void execute(Message<T> message, BeiMiExtentionTransitionConfigurer<T,S> configurer) {
		
	}
}
