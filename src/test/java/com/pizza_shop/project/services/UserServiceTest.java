package com.pizza_shop.project.services;

import com.pizza_shop.project.dao.UserDao;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.exceptions.EmailException;
import com.pizza_shop.project.exceptions.UserNameException;
import com.pizza_shop.project.services.impl.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MailSenderService mailSenderService;

    private static List<User> users;
    private static User user1;
    private static User user2;

    @InjectMocks
    private UserService userService;

    @BeforeAll
    public static void init(){
        users = new ArrayList<>();
        user1 =  new User(1, "Fort", "128qwsdh", "Zack", "North", "saSAA@gmail.com", "NY", "Madison", "23213", "312321213",
                "ROLE_USER", true, null, null, null, null, null);
        user2 = new User(2, "Port", "879dsadad", "Jack", "West", "west@gmail.com", "LA", "Madison", "212313123", "3879713",
                "ROLE_USER", true, null, null, null, null, null);
        users.add(user1);
        users.add(user2);
    }
    @Test
    public void givenUserIdWhenGettingUserByIdReturnUser(){
      Mockito.when(userDao.getOne(ArgumentMatchers.anyInt())).thenReturn(user1);
        final User actualUser = userService.getUser(1);
        Assertions.assertEquals(actualUser.getId(), user1.getId());
    }
    @Test
    public void givenNothingWhenGettingAllUsersReturnAllUsers(){
        Mockito.when(userDao.findAll()).thenReturn(users);
        final List<User> allUsers = userService.getAllUsers();
        Assertions.assertEquals(users.get(0).getId(), allUsers.get(0).getId());
    }
    @Test
    public void givenUserWithOccupiedEmailAddressWhenInsertingUserReturnEmailException(){
        User userWithOccupiedEmail = new User();
        userWithOccupiedEmail.setEmail("test@test.com");
        Mockito.when(userDao.findUserByEmail(userWithOccupiedEmail.getEmail())).thenReturn(userWithOccupiedEmail);
        Assertions.assertThrows(EmailException.class,
                () -> userService.createUser(userWithOccupiedEmail),
                "This email occupied already");
    }
    @Test
    public void givenUserWithOccupiedNameWhenInsertUserReturnUserNameException(){
        User userWithOccupiedUSerName = new User();
        userWithOccupiedUSerName.setUsername("Bob");
        Mockito.when(userDao.findUsersByUsername(userWithOccupiedUSerName.getUsername())).thenReturn(userWithOccupiedUSerName);
        Assertions.assertThrows(UserNameException.class,
                () -> userService.createUser(userWithOccupiedUSerName),
                "Login occupied already");
    }
    @Test
    @WithAnonymousUser
    public void givenUserThisNoOccupiedUserNameAndEmailWhenInsertingReturnUser(){
        Mockito.when(userDao.findUserByEmail(user1.getEmail())).thenReturn(null);
        Mockito.when(userDao.findUsersByUsername(user1.getUsername())).thenReturn(null);
        Mockito.when(userDao.save(user1)).thenReturn(user1);
        Mockito.when(userDao.getOne(user1.getId())).thenReturn(user1);
        final User userActual = userService.createUser(user1);
        Assertions.assertEquals(userActual.getName(), user1.getName());
    }
    @Test
    public void givenUserIdWhenDeletingUserReturnAllUsers(){
        Mockito.when(userDao.findAll()).thenReturn(users);
        final List<User> allUsers = userService.getAllUsers();
        allUsers.remove(user1);
        final List<User> actualUsers = userService.deleteUser(allUsers.get(0).getId());
        Assertions.assertEquals(actualUsers.get(0).getId(), UserServiceTest.users.get(0).getId());
    }
}
