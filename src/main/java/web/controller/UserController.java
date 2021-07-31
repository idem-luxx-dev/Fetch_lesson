package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.User;
import web.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}

    @RequestMapping(value = "/user")
    public String getUserPage(Model model) {
        List<String> messages = new ArrayList<>();
        messages.add("Привет!");
        messages.add("Все что ты можешь - любоваться собой:");
        model.addAttribute("messages", messages);
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userService.getDetailsService().loadUserByUsername(((UserDetails) user).getUsername()));

        return "userPage";
    }

}
