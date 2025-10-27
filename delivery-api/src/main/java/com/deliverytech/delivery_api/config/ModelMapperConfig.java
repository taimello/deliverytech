package com.deliverytech.delivery_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper modelMapper(){

        ModelMapper mapper =  new ModelMapper();

        //Configurações específicas se necessário
        mapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setFieldMatchingEnabled(true)
            .setFieldAccessLevel(org.modelmapper.config.Configuration.AcessLevel.PRIVATE);

        return mapper;    
    }
}
