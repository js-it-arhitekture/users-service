package com.example.nakup_vstopnic.controller;

import com.example.nakup_vstopnic.model.User;
import com.example.nakup_vstopnic.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Api(tags = "User API")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired  // Dependency Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation("Get all users")
    public List<User> getAllUsers() {
        log.info("Retrieving all users");
        List<User> users = userService.getAllUsers();
        log.info("Retrieved all users, count: {}", users.size());
        return users;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get user by ID")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> returnedUser = userService.getUserById(id);
        return returnedUser.map(user -> {
            log.info("Found user with ID {}", id);
            return ResponseEntity.ok(user);
        }).orElseGet(() -> {
            log.info("User with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @PostMapping
    @ApiOperation("Create a new user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        log.info("Created a new user with ID={}, username={}, name={}, age={}",
                createdUser.getId(), createdUser.getUsername(),
                createdUser.getName(), createdUser.getAge());
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update user by ID")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        try {
            Optional<User> updatedUserOptional = userService.updateUser(id, updatedUser);
            return updatedUserOptional.map(user -> {
                log.info("Updated user with ID={}, username={}, name={}, age={}",
                        user.getId(), user.getUsername(),
                        user.getName(), user.getAge());
                return ResponseEntity.ok(user);
            }).orElseGet(() -> {
                log.info("User with ID {} not found for updating", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            });
        } catch (IllegalArgumentException ex) {
            log.info("Invalid request body!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete user by ID")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return userService.getUserById(id).map(user -> {
            userService.deleteUser(id);
            log.info("User with ID {} successfully deleted.", id);
            return ResponseEntity.ok("User with ID " + id + " successfully deleted.");
        }).orElseGet(() -> {
            log.warn("User with ID {} not found for deletion.", id);
            return ResponseEntity.badRequest().body("User with ID " + id + " not found for deletion.");
        });
    }

    @GetMapping("/status")
    @ApiOperation("Check service status")
    public ResponseEntity<String> status() {
        log.info("Calling /status");
        return ResponseEntity.ok("User microservice is operational!");
    }
}
