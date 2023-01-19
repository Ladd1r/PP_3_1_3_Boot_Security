package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DBInit {
    private final UserServiceImpl userServiceImpl;
    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public DBInit(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }
    @PostConstruct
    public void initDB() {
        Role roleUser = new Role(1, "ROLE_USER");
        Role roleAdmin = new Role(2, "ROLE_ADMIN");

        Set<Role> adminSet = new HashSet<>();
        Set<Role> userSet = new HashSet<>();

        roleServiceImpl.addRole(roleAdmin);
        roleServiceImpl.addRole(roleUser);

        adminSet.add(roleAdmin);
        adminSet.add(roleUser);

        userSet.add(roleUser);

        User admin = new User("Laddir", "Yegor", "Zyghan", 23, "admin", adminSet);
        admin.setId(2);

        User user = new User("Ployka", "Ivan", "Egorov", 24, "user", userSet);
        admin.setId(1);

        userServiceImpl.saveUser(admin);
        userServiceImpl.saveUser(user);
    }

}
