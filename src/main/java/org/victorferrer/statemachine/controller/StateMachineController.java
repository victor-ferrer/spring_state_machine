package org.victorferrer.statemachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.victorferrer.statemachine.config.Events;
import org.victorferrer.statemachine.config.States;

@Controller
@RequestMapping("/")
public class StateMachineController {
	
	@Autowired
	private StateMachine<States, Events> sm;
	
	@GetMapping("/start")
	@ResponseBody
	public String start() {
		sm.start();
		return "ok start";
	}
	
	@GetMapping(value = "/event/{id}")
	@ResponseBody
	public String event(@PathVariable String id) {
		sm.sendEvent(Events.valueOf(id));
		return "ok event";
	}

}
