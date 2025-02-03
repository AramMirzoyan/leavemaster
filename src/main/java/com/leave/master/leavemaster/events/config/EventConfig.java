package com.leave.master.leavemaster.events.config;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leave.master.leavemaster.events.EventHandlerFactory;

/**
 * Configuration class for event handling. It registers beans required for handling events using
 * ServiceLocatorFactoryBean.
 */
@Configuration
public class EventConfig {

  /**
   * Creates and configures a ServiceLocatorFactoryBean for EventHandlerFactory.
   *
   * @return a configured instance of ServiceLocatorFactoryBean
   */
  @Bean
  public ServiceLocatorFactoryBean eventHandlerFactoryBean() {
    final ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
    serviceLocatorFactoryBean.setServiceLocatorInterface(EventHandlerFactory.class);
    return serviceLocatorFactoryBean;
  }

  /**
   * Provides an instance of EventHandlerFactory.
   *
   * @return an EventHandlerFactory instance
   */
  @Bean
  public EventHandlerFactory eventHandlerFactory() {
    return (EventHandlerFactory) eventHandlerFactoryBean().getObject();
  }
}
