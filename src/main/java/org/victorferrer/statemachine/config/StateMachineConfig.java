package org.victorferrer.statemachine.config;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

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
import org.victorferrer.statemachine.persistence.EventRecord;
import org.victorferrer.statemachine.persistence.EventRecordRepository;

/**
 * Sample State MAchine Configuration that might be replaced
 * by a more sophisticated one.
 * @author victor
 *
 */
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig  extends StateMachineConfigurerAdapter<String, Events> {

	
	public static final String ACCEPTED = "Accepted";
	public static final String RUNNING = "Running";
	public static final String FINISHED_OK = "Finished OK";
	public static final String FINISHED_ERROR = "Finished Error";
	
	
	@Autowired
	private EventRecordRepository eventRepo;
	
	private Logger logger = LoggerFactory.getLogger(StateMachineConfig.class); 
	
    @Override
    public void configure(StateMachineStateConfigurer<String, Events> configurer)
            throws Exception {
        configurer
            .withStates()
                .initial(ACCEPTED)
                .end(FINISHED_OK)
                .end(FINISHED_ERROR)
                .states(new HashSet<String>(Arrays.asList(ACCEPTED, RUNNING,
                										  FINISHED_OK, FINISHED_ERROR)));
        
    }
    
    @Override
    public void configure(StateMachineTransitionConfigurer<String, Events> transitions)
            throws Exception {
        transitions
            .withExternal()
                .source(ACCEPTED).target(RUNNING)
                .event(Events.STARTED)
                .action(persistAction(RUNNING))
                .and()
            /* FIXME Figure out how to place a choice to see if RUNNING was OK or not    
            .withChoice()
                .source(States.RUNNING)
                .first(States.FINISHEDERROR, errorCheckGuard())
                .last(States.FINISHEDOK)
                .and()*/
            .withExternal()
                .source(RUNNING).target(FINISHED_OK)
                .event(Events.FINISHED_OK)
                .action(persistAction(FINISHED_OK))
                .and()
            // FINISH ERROR due to timeout
            .withExternal()
                .source(RUNNING).target(FINISHED_ERROR)
                .event(Events.TIMEOUT)
                .action(persistAction(FINISHED_ERROR))
                .and()                
            // Handle timeout
            .withInternal()
                .source(RUNNING)
                .action(timeoutAction())
                .timerOnce(10000);
    }

	private Action<String, Events> timeoutAction() {
    	
    	return new Action<String, Events>(){

			@Override
			public void execute(StateContext<String, Events> context) {
				context.getStateMachine().sendEvent(Events.TIMEOUT);
			}
    		
    	};
	}

	private Action<String, Events> persistAction(String state) {
    	
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
