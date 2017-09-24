package com.bradypod.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bradypod.config.web.model.Game;
import com.bradypod.core.statemachine.BeiMiStateMachine;
import com.bradypod.core.statemachine.impl.BeiMiMachineHandler;

@Configuration
public class BeiMiStateMachineHandlerConfig {
	
	@Autowired
	private BeiMiStateMachine<String,String> configure ;
	
    @Bean
    public Game persist() {
        return new Game(persistStateMachineHandler());
    }

    public BeiMiMachineHandler persistStateMachineHandler() {
        return new BeiMiMachineHandler(this.configure);
    }
}
