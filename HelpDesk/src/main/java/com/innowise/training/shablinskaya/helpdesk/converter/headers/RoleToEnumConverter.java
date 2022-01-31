package com.innowise.training.shablinskaya.helpdesk.converter.headers;

import com.innowise.training.shablinskaya.helpdesk.enums.Role;
import javafx.scene.chart.ScatterChart;
import org.springframework.core.convert.converter.Converter;

public class RoleToEnumConverter implements Converter<String, Role> {
    @Override
    public Role convert(String source) {
        try{
        return Role.valueOf(source.toUpperCase());
        }catch (IllegalArgumentException e){
            return null;
        }
    }
    
}
