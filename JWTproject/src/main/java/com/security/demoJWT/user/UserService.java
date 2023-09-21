package com.security.demoJWT.user;

import com.security.demoJWT.exception.TicketBookingException;
import com.security.demoJWT.repo.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service
public class UserService {
    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * This method is used to add user entity to the database
     *
     * @param user
     * @return
     */
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>());
        return userRepository.save(user);
    }

    /**
     * This method is used to add roles entity to the database
     *
     * @param role
     * @return
     */
    public Roles saveRole(Roles role) {
        return rolesRepository.save(role);
    }

    /**
     * This method is used to links roles to the user
     *
     * @param username
     * @param rolesname
     */
    public void addToUser(String username, String rolesname) {
        if (!userRepository.findByEmail(username).isPresent()) {
            throw new TicketBookingException("User with email" + username + " does not exist");
        }
        Roles roles = rolesRepository.findByName(rolesname);
        if (roles == null) {
            throw new TicketBookingException("Roles with name" + rolesname + " does not exist");
        }
        User user = userRepository.findByEmail(username).get();
        user.getRoles().add(roles);
    }
}
