package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private final static UserService userService = new UserServiceImpl();


    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Bruce", "Wayne", (byte) 48);
        userService.saveUser("Barry", "Allen", (byte) 29);
        userService.saveUser("Selina", "Kyle", (byte) 35);
        userService.saveUser("Pamela", "Isley", (byte) 34);

        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
