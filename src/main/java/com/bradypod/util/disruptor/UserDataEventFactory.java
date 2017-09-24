package com.bradypod.util.disruptor;

import com.bradypod.util.event.UserDataEvent;
import com.lmax.disruptor.EventFactory;

public class UserDataEventFactory implements EventFactory<UserDataEvent>{

	@Override
	public UserDataEvent newInstance() {
		return new UserDataEvent();
	}
}
