package com.uniovi.validators;

import com.uniovi.entities.Mark;
import com.uniovi.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MarksFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Mark.class.equals(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Mark mark = (Mark) target;
        if (mark.getDescription().length() < 5 || mark.getDescription().length() > 50) {
            errors.rejectValue("description", "Error.edit.description.length");
        }
        if (mark.getScore() < 0 || mark.getScore() > 10) {
            errors.rejectValue("score", "Error.edit.score.value");
        }
    }
}
