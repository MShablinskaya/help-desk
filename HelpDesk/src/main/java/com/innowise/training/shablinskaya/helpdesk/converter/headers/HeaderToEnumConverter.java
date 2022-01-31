package com.innowise.training.shablinskaya.helpdesk.converter.headers;

import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;
import org.springframework.core.convert.converter.Converter;


public class HeaderToEnumConverter implements Converter<String, Urgency> {

    @Override
    public Urgency convert(String source) {
        try {
            return Urgency.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }

    }
}