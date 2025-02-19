package com.reddy.config;

import com.reddy.dto.leave.LeaveRequestResponseDTO;
import com.reddy.model.employee.Employee;
import com.reddy.model.leave.LeaveRequest;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        Converter<Employee, String> fullNameConverter = ctx ->
                ctx.getSource().getFirstName() + " " + ctx.getSource().getLastName();
        return new ModelMapper();
    }
}

