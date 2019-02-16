package com.uniovi.validators;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");
        if (user.getDni().length() < 5 || user.getDni().length() > 24) {
            errors.rejectValue("dni", "Error.edit.dni.length");
        }
        if (user.getName().length() < 4 || user.getName().length() > 24) {
            errors.rejectValue("name", "Error.edit.name.length");
        }
        if (user.getLastName().length() < 4 || user.getLastName().length() > 24) {
            errors.rejectValue("lastName", "Error.edit.lastName.length");
        }
    }
}
