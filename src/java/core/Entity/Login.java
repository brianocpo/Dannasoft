/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
/**
 *
 * @author Brian
 */
public class Login {
    
    //@Size(min = 3, max = 12, message = "El usuario Tiene que tener mínimo 3 caracteres")
    //@Pattern(regexp = "^[a-zA-Z]\\w{5,12}$", message = "La Calve Tiene que tener mínimo 5 caracteres alfanumericos")
    @NotEmpty(message = "Ingrese su Usuario")
    private String ls_usuario;
    
    @NotEmpty(message = "Ingrese su Clave")
    @Pattern(regexp = "^[0-9a-zA-Z]\\w{1,12}$", message = "La Clave no puede contener caracteres especiales")
    private String ls_clave;

    public String getLs_usuario() {
        return ls_usuario;
    }

    public void setLs_usuario(String ls_usuario) {
        this.ls_usuario = ls_usuario;
    }

    public String getLs_clave() {
        return ls_clave;
    }

    public void setLs_clave(String ls_clave) {
        this.ls_clave = ls_clave;
    }

   
  
}
