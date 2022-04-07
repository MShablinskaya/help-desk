package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.dto.AuthRequestDto;
import com.innowise.training.shablinskaya.helpdesk.dto.AuthResponseDto;

public interface LoginService {

    AuthResponseDto login(AuthRequestDto requestDto);
}
