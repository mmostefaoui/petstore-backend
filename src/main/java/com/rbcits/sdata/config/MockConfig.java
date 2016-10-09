package com.rbcits.sdata.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

//@Configuration
public class MockConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    @Bean(name = "mockData")
    public Jackson2RepositoryPopulatorFactoryBean jackson2RepositoryPopulatorFactoryBean() {
        Jackson2RepositoryPopulatorFactoryBean factoryBean = new Jackson2RepositoryPopulatorFactoryBean();
        factoryBean.setResources(new Resource[]{new ClassPathResource("mock.json")});
        return factoryBean;
    }
}