package web.controller;

import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/admin")
    public String adminPage(Model model){
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "adminPage";
    }


}
