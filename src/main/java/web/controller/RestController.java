package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.BindingResult;
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

    @PostMapping(value = "/api/add")
    public ResponseEntity<Void> insert(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/api/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {return ResponseEntity.ok(userService.getUserById(id));}

    @GetMapping(value = "/api/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleService.allRoles());
    }

    @GetMapping(value = "/api/all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping(value = "api/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/api/edit")
    public ResponseEntity<Void> edit(@RequestBody User user) {
        userService.editUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
