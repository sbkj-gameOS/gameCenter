package com.bradypod.core.statemachine.action;

import com.bradypod.core.statemachine.impl.BeiMiExtentionTransitionConfigurer;
import com.bradypod.core.statemachine.message.Message;

public interface Action<T,S> {
	void execute(Message<T> message , BeiMiExtentionTransitionConfigurer<T, S> configurer); 
}
