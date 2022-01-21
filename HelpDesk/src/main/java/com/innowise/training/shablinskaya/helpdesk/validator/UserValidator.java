//package com.innowise.training.shablinskaya.helpdesk.validator;
//
//import com.innowise.training.shablinskaya.helpdesk.entity.User;
//import com.innowise.training.shablinskaya.helpdesk.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.ValidationUtils;
//import org.springframework.validation.Validator;
//
//@Component
//public class UserValidator implements Validator {
//
//    private UserService userService;
//
//    @Autowired
//    public UserValidator(UserService userService){
//        this.userService = userService;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return User.class.equals(aClass);
//    }
//
//    @Override
//    public void validate(Object object, Errors errors) {
//        User user = (User) object;
//
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");
//        if(!userService.findByEmail(user.getEmail()).isPresent()){
//            errors.rejectValue("email", "MissMatch.UserForm.EmailOrPassword");
//        }
//
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
//
//    }
//}
