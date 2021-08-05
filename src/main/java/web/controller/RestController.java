package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    public UserService userService;

    @Autowired
    public RoleService roleService;



    @RequestMapping(value = "/api",method = RequestMethod.GET)
    public ResponseEntity<User> getUser() {
        return ResponseEntity.ok(userService.getUserById(24L));
    }

    @PostMapping(value = "/api/add")
    public ResponseEntity<Void> insert(@RequestBody User user) {
        System.out.println(user.getRoles());
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/api/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleService.allRoles());
    }



}
