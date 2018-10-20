package org.victorferrer.statemachine.config;

import java.util.EnumSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class StateMachineConfig  extends StateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
            .withStates()
                .initial(States.ACCEPTED)
                .end(States.TERMINATED)
                .states(EnumSet.allOf(States.class));
    }
    
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
            .withExternal()
                .source(States.ACCEPTED).target(States.RUNNING)
                .event(Events.E1)
                .and()
            .withExternal()
                .source(States.RUNNING).target(States.FINISHEDOK)
                .event(Events.E2)
                .and()
            .withExternal()
            	.source(States.FINISHEDOK).target(States.TERMINATED)
            	.event(Events.E3);
    }
    
    

    @Bean
    public StateMachineApplicationEventListener contextListener() {
        return new StateMachineApplicationEventListener();
    }
    
    
    
}
