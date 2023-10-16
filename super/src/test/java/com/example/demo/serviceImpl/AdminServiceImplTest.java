package com.example.demo.serviceImpl;

import com.example.demo.entities.Roles;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.RoleResponse;
import org.openapitools.model.UserCreationResponse;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {
    @InjectMocks
    AdminServiceImpl adminService;

    @Mock
    UserRepository userRepository;

    @Test
    void approveUser() {

        User newUser=new User("Naveen","N","12.01.2001","male","naveen@gmail.com","pass","requested");
        newUser.setId(1);
        Roles roles=new Roles(2,"HELPER","helper");
        newUser.setRoles(roles);

        UserCreationResponse expectedResponse=new UserCreationResponse();
        expectedResponse.setId(1);
        expectedResponse.setDOB("12.01.2001");
        expectedResponse.setEmail("naveen@gmail.com");
        expectedResponse.setGender("male");

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("HELPER");
        roleResponse.setDescription("helper");
        roleResponse.setId(2);

        expectedResponse.setRole(List.of(roleResponse));
        expectedResponse.setFirstName("Naveen");
        expectedResponse.setLastName("N");
        expectedResponse.setPassword("pass");
        expectedResponse.setStatus("approved");

        when(userRepository.findById(1)).thenReturn(Optional.of(newUser));
        assertEquals(adminService.approveUser(1),expectedResponse);

    }

    @Test
    void getAllRequestedUser() {
        User userOne=new User("Naveen","N","12.01.2001","male","naveen@gmail.com","pass","requested");
        userOne.setId(1);
        Roles roles=new Roles(2,"HELPER","helper");
        userOne.setRoles(roles);
        User userTwo=new User("Sakthi","S","12.01.2001","male","sakthi@gmail.com","pass","requested");
        userTwo.setId(2);
        userTwo.setRoles(roles);

        UserCreationResponse userOneResponse=new UserCreationResponse();
        userOneResponse.setId(1);
        userOneResponse.setDOB("12.01.2001");
        userOneResponse.setEmail("naveen@gmail.com");
        userOneResponse.setGender("male");

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("HELPER");
        roleResponse.setDescription("helper");
        roleResponse.setId(2);

        userOneResponse.setRole(List.of(roleResponse));
        userOneResponse.setFirstName("Naveen");
        userOneResponse.setLastName("N");
        userOneResponse.setPassword("pass");
        userOneResponse.setStatus("requested");

        UserCreationResponse userTwoResponse=new UserCreationResponse();
        userTwoResponse.setId(2);
        userTwoResponse.setDOB("12.01.2001");
        userTwoResponse.setEmail("sakthi@gmail.com");
        userTwoResponse.setGender("male");

        userTwoResponse.setRole(List.of(roleResponse));
        userTwoResponse.setFirstName("Sakthi");
        userTwoResponse.setLastName("S");
        userTwoResponse.setPassword("pass");
        userTwoResponse.setStatus("requested");

        when(userRepository.getAllRequestedUser()).thenReturn(List.of(userOne,userTwo));
        assertEquals(adminService.getAllRequestedUser(),List.of(userOneResponse,userTwoResponse));
    }
}