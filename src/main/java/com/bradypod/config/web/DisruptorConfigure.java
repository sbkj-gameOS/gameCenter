package com.bradypod.config.web;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.bradypod.util.disruptor.UserDataEventFactory;
import com.bradypod.util.disruptor.UserEventHandler;
import com.bradypod.util.event.UserDataEvent;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

@Component
public class DisruptorConfigure {
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Bean(name="disruptor")   
    public Disruptor<UserDataEvent> disruptor() {   
		Executor executor = Executors.newCachedThreadPool();
    	 UserDataEventFactory factory = new UserDataEventFactory();
    	 Disruptor<UserDataEvent> disruptor = new Disruptor<UserDataEvent>(factory, 1024, executor, ProducerType.SINGLE , new SleepingWaitStrategy());
    	 disruptor.setDefaultExceptionHandler(new BeiMiExceptionHandler());
    	 disruptor.handleEventsWith(new UserEventHandler());
    	 disruptor.start();
         return disruptor;   
    }  
}
