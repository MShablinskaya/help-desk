package com.innowise.training.shablinskaya.helpdesk.converter;

import com.innowise.training.shablinskaya.helpdesk.dto.HistoryDto;
import com.innowise.training.shablinskaya.helpdesk.entity.History;


public interface HistoryConverter {

    HistoryDto toDto(History history);
}
