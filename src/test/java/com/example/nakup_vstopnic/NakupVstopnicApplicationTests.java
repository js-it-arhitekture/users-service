package com.example.nakup_vstopnic;

import com.example.nakup_vstopnic.controller.UserController;
import com.example.nakup_vstopnic.model.User;
import com.example.nakup_vstopnic.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class NakupVstopnicApplicationTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
    @Test
    void contextLoads() {
    }

    @Test
    void getAllUsers() {
        System.out.println("Running getAllUsers test...");
        User user1 = new User("user1", "password1", "John Doe", 25);
        User user2 = new User("user2", "password2", "Jane Doe", 30);
        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        // Call the actual method under test
        List<User> response = userController.getAllUsers();

        // Preverjanje rezultatov
        assertEquals(users, response);
    }

    @Test
    void getUserByIdNotFound() {
        String userId = "123";

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserById(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createUser() {
        User user = new User("user1", "password1", "John Doe", 25);

        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void updateUser() {
        String userId = "123";
        User updatedUser = new User("user1", "password1", "John Doe", 25);

        when(userService.updateUser(userId, updatedUser)).thenReturn(Optional.of(updatedUser));

        ResponseEntity<User> response = userController.updateUser(userId, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    void updateUserNotFound() {
        String userId = "123";
        User updatedUser = new User("user1", "password1", "John Doe", 25);

        when(userService.updateUser(userId, updatedUser)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.updateUser(userId, updatedUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteUser() {
        String userId = "123";
        User user = new User("user1", "password1", "John Doe", 25);

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void deleteUserNotFound() {
        String userId = "123";

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void status() {
        ResponseEntity<String> response = userController.status();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User microservice is operational!", response.getBody());
    }

}
