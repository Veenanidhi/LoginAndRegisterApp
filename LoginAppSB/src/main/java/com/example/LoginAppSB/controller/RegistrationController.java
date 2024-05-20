package com.example.LoginAppSB.controller;

import com.example.LoginAppSB.entity.User;
import com.example.LoginAppSB.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);

    }

    @GetMapping("/showRegistrationForm")
    public String showRegistrationForm(Model model) {

        model.addAttribute("webUser", new User());

        return "register/registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("webUser") User theWebUser,
            BindingResult theBindingResult,
            Model theModel) {

        // Form validation
        if (theBindingResult.hasErrors()) {
            return "register/registration-form";
        }

        // Check if the username is already taken
        User existingUser = userService.findByUsername(theWebUser.getUsername());
        if (existingUser != null) {
            theModel.addAttribute("webUser", new User());
            theModel.addAttribute("registrationError", "Username already exists!");
            return "register/registration-confirmation";
        }

        // Save the new user
        userService.save(theWebUser);

        return "register/registration-confirmation";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id")Long id){
        userService.deleteById(id);
        return "redirect:/systems";
    }

    @GetMapping("/showRegistrationFormForUpdate")
    public String showMyLoginPageForUpdate(@RequestParam("id") Long id,Model theModel){

        User user=userService.findById(id);

        theModel.addAttribute("webUser",user);

        return "register/update-form";
    }

    @PostMapping("/processRegistrationFormForUpdate")
    public String processRegistrationFormForUpdate(
            @Valid @ModelAttribute("webUser") User theWebUser,
            BindingResult theBindingResult,
            Model theModel){

        //form validation
        if (theBindingResult.hasErrors()) {
            return "register/update-form";
        }
        // create user account and store in the database
        userService.save(theWebUser);

        return "register/update-confirmation";
    }

}
