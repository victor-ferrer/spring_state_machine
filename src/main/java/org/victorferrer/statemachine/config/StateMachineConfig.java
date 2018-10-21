package org.victorferrer.statemachine.config;

import java.util.Date;
import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.victorferrer.statemachine.model.Events;
import org.victorferrer.statemachine.model.States;
import org.victorferrer.statemachine.persistence.EventRecord;
import org.victorferrer.statemachine.persistence.EventRecordRepository;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig  extends StateMachineConfigurerAdapter<States, Events> {

	@Autowired
	private EventRecordRepository eventRepo;
	
	private Logger logger = LoggerFactory.getLogger(StateMachineConfig.class); 
	
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
            .withStates()
                .initial(States.ACCEPTED)
                .end(States.FINISHEDOK)
                .end(States.FINISHEDERROR)
                .states(EnumSet.allOf(States.class));
    }
    
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
            .withExternal()
                .source(States.ACCEPTED).target(States.RUNNING)
                .event(Events.STARTED)
                .action(persistAction(States.RUNNING))
                .and()
            /* FIXME Figure out how to place a choice to see if RUNNING was OK or not    
            .withChoice()
                .source(States.RUNNING)
                .first(States.FINISHEDERROR, errorCheckGuard())
                .last(States.FINISHEDOK)
                .and()*/
            .withExternal()
                .source(States.RUNNING).target(States.FINISHEDOK)
                .event(Events.FINISHED_OK)
                .action(persistAction(States.FINISHEDOK))
                .and()
            // FINISH ERROR due to timeout
            .withExternal()
                .source(States.RUNNING).target(States.FINISHEDERROR)
                .event(Events.TIMEOUT)
                .action(persistAction(States.FINISHEDERROR))
                .and()                
            // Handle timeout
            .withInternal()
                .source(States.RUNNING)
                .action(timeoutAction())
                .timerOnce(10000);
    }

	private Action<States, Events> timeoutAction() {
    	
    	return new Action<States, Events>(){

			@Override
			public void execute(StateContext<States, Events> context) {
				context.getStateMachine().sendEvent(Events.TIMEOUT);
			}
    		
    	};
	}

	private Action<States, Events> persistAction(final States state) {
    	
    	return context -> {
    		String id = context.getStateMachine().getId();
    		logger.info(String.format("Persisting [%s state for StateMachine [%s] to DB",state,id));
    		
    		EventRecord record = new EventRecord();
    		record.setTaskName(id);
    		record.setTimestamp(new Date());
    		record.setState(state);
			eventRepo.save(record);
		};
	}
}
