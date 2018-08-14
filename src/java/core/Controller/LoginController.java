/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Controller;


import core.Entity.Login;
import core.Entity.LoginValidador;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author brian
 */
@Controller
public class LoginController {
    private LoginValidador loginvalidar;

    public LoginController() {
        this.loginvalidar = new LoginValidador();
    }
    
    @RequestMapping("/")
    public String inicio(Model model) {       
        model.addAttribute("loginclass",new Login());
        return "login";  
    }
  
    @RequestMapping(value = "/ login",method = RequestMethod.GET)
    public ModelAndView login()
    {   ModelAndView mav = new ModelAndView("login");
        mav.addObject("loginclass",new Login());
        return mav;       
    }

    //VALIDA LOS DATOS DEL LOGIN
    @RequestMapping(value = "/ login",method = RequestMethod.POST)
    public String postPhone( @ModelAttribute("loginclass") @Valid Login login, BindingResult bindingResult) {
        this.loginvalidar.validate(login, bindingResult);
	if (bindingResult.hasErrors()) {
            return "login";
        }
        return "exito";
    }
//    @RequestMapping(value = "validarLogin", method = RequestMethod.POST)
//    public String verificarLogin(@RequestParam("ls_usuario") String usuario,@RequestParam("ls_clave") String clave,Model mod)
//    {   mod.addAttribute("usuario",usuario);
//        mod.addAttribute("clave",clave);
//        return "exito" ;
//    }
}
