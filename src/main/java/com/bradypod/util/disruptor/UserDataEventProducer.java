package com.bradypod.util.disruptor;

import com.bradypod.util.event.UserDataEvent;
import com.bradypod.util.event.UserEvent;
import com.lmax.disruptor.RingBuffer;

public class UserDataEventProducer {
	private final RingBuffer<UserDataEvent> ringBuffer;

    public UserDataEventProducer(RingBuffer<UserDataEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    public void onData(UserEvent userEvent)
    {
        long id = ringBuffer.next();  // Grab the next sequence
        try{
        	UserDataEvent event = ringBuffer.get(id);
        	event.setEvent(userEvent);
        }finally{
            ringBuffer.publish(id);
        }
    }
}
