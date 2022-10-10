package com.krugger.challenge.service;

import com.krugger.challenge.entity.Employee;
import com.krugger.challenge.entity.User;
import com.krugger.challenge.exception.ValidationException;
import com.krugger.challenge.presentation.presenter.UserPresenter;
import com.krugger.challenge.repository.UserRepository;
import com.krugger.challenge.service.impl.UserServiceImpl;
import com.krugger.challenge.util.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    @Spy
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserRepository userRepository;

    private final TestData testData = new TestData();

    @Test
    public void shouldLoginUser() {
        User user = testData.userFake();
        user.setPassword(passwordEncoder.encode(user.getEmployee().getDni()));
        when(userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword())).thenReturn(Optional.of(user));
        UserPresenter userPresenter = userService.loginUser(user.getUserName(), user.getEmployee().getDni());
        Assertions.assertThat(userPresenter).isNotNull();
    }

    @Test
    public void shouldNotAllowLoginUser() {
        when(userRepository.findByUserNameAndPassword("firstname.lastname", "")).thenReturn(Optional.empty());
        UserPresenter userPresenter = userService.loginUser("firstname.lastname", "");
        Assertions.assertThat(userPresenter).isNull();
    }

    @Test
    public void shouldCreateUserByEmployee() {
        Employee employee = testData.employeeFake();
        UserPresenter userPresenter = userService.createUserByEmployee(employee);
        Assertions.assertThat(userPresenter).isNotNull();
    }

    @Test
    public void shouldGetValidationExceptionWhenEmployeeExist() {
        Employee employee = testData.employeeFake();
        userService.createUserByEmployee(employee);
        Assertions.assertThatThrownBy(() -> userService.createUserByEmployee(employee)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("User already exist");
    }
}