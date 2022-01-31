package com.innowise.training.shablinskaya.helpdesk.converter.headers;


import com.innowise.training.shablinskaya.helpdesk.enums.State;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, State> {
    @Override
    public State convert(String source) {
        try{
           return State.valueOf(source.toUpperCase());
        }catch (IllegalArgumentException e){
        return null;}
    }

}
