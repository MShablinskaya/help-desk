package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;
import com.innowise.training.shablinskaya.helpdesk.service.UrgencyListService;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class UrgencyListServiceImpl implements UrgencyListService {
    @Override
    public List<Urgency> getUrgencyList() {

        return new ArrayList<Urgency>(EnumSet.allOf(Urgency.class));
    }
}
