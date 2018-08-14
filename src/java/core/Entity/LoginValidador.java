/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Entity;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author brian
 */
public class LoginValidador  implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return Login.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Login login= new Login();
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ls_usuario", "requiered.ls_usuario","El campo Usuario es requerido");
        
    }
    
}
