package com.uabc.database.example.examplejpa.controllers;

import com.uabc.database.example.examplejpa.constant.ViewConstant;
import com.uabc.database.example.examplejpa.model.UserModel;
import com.uabc.database.example.examplejpa.services.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    private static final Log log = LogFactory.getLog(UserController.class);

    @GetMapping("/cancel")
    public String cancel() {
        return "redirect:/users/showUsers";
    }

    @GetMapping("/userForm")
    public String redirectUserForm(Model model, @RequestParam(name = "id", required = false) int id) {
        UserModel userModel = new UserModel();
        if(id != 0)
            userModel = userService.findUserByIdModel(id);
        model.addAttribute("userModel",userModel);
        return ViewConstant.USER_FORM;
    }

    @PostMapping("/adduser")
    public String addUser(@ModelAttribute(name = "userModel")UserModel userModel, Model model) {
        log.info("Method: addUser() -- Params: " + userModel.toString());
        if(userService.addUser(userModel) != null)
            model.addAttribute("result",1);
        else
            model.addAttribute("result",0);
        return "redirect:/users/showUsers";
    }

    @GetMapping("/showUsers")
    public ModelAndView showUsers() {
        ModelAndView mav = new ModelAndView(ViewConstant.USERS);
        mav.addObject("users",userService.listAllUsers());
        return mav;
    }

    @GetMapping("/removeUser")
    public ModelAndView removeUser(@RequestParam(name = "id", required = true) int id) {
        userService.removeUser(id);
        return showUsers();
    }
}