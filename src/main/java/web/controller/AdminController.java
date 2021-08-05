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


    @PostMapping(value = "/admin/add" )
    public String postUser(@ModelAttribute("user") User user,
                           @RequestParam(required=false) String role) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getDefaultRole());

        if (role != null && role.equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleByName(role));
        }
        user.setRoles(roles);
        userService.addUser(user);

        return "redirect:/admin";
    }

    @PostMapping(value = "admin/edit/{id}")
    public String editUser(@ModelAttribute("user") User user,
                           @RequestParam(required=false) String role) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getDefaultRole());
        if (role != null && role.equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleByName(role));
        }
        user.setRoles(roles);
        userService.editUser(user);
        return "redirect:/admin";
    }


    @GetMapping(value = "/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
