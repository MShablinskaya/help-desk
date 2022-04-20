package com.innowise.training.shablinskaya.helpdesk.enums;

public enum Action {
    SUBMIT("NEW"),
    DECLINE("DECLINED"),
    CANCEL("CANCELLED"),
    APPROVE("APPROVED"),
    ASSIGN("IN_PROGRESS"),
    DONE("DONE"),
    EDIT("Edit"),
    LEAVE_FEEDBACK("Leave Feedback");

    private final String action;

    private Action(String action){
        this.action = action;
    }

    public String getAction(){
        return action;
    }

}
