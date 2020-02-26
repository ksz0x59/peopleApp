package com.ksz.peopleApp.service;

import com.ksz.peopleApp.dao.UserDao;
import com.ksz.peopleApp.model.Gender;
import com.ksz.peopleApp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserDao fakeUserDao;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeUserDao);
    }

    @Test
    void getAllUsersShouldReturnOneUser() {
        UUID joeUserId = UUID.randomUUID();

        User joe = new User(joeUserId, "John", "Johnson",
                Gender.MALE, 22, "joe.johnson@gmail.com");

        List<User> users = Arrays.asList(joe);

        given(fakeUserDao.selectAllUsers()).willReturn(users);

        List<User> allUsers = userService.getAllUsers(Optional.empty());

        assertThat(allUsers).hasSize(1);
        User user = allUsers.get(0);
        validateUser(user);
    }

    @Test
    void getUserShouldReturnOneUser() {
        UUID joeUserId = UUID.randomUUID();

        User joe = new User(joeUserId, "John", "Johnson",
                Gender.MALE, 22, "joe.johnson@gmail.com");

        given(fakeUserDao.selectUserByUserId(joeUserId)).willReturn(Optional.of(joe));

        Optional<User> oneUser = userService.getUser(joeUserId);

        assertThat(oneUser.isPresent()).isTrue();

        User user = oneUser.get();

        validateUser(user);

    }

    @Test
    void updateUserShouldUpdateOneUser() {

        UUID joeUserId = UUID.randomUUID();

        User joe = new User(joeUserId, "John", "Johnson",
                Gender.MALE, 22, "joe.johnson@gmail.com");

        given(fakeUserDao.selectUserByUserId(joeUserId)).willReturn(Optional.of(joe));
        given(fakeUserDao.updateUser(joe)).willReturn(1);

        int updateResult = userService.updateUser(joe);
        verify(fakeUserDao).selectUserByUserId(joeUserId);

        assertThat(updateResult).isEqualTo(1);

    }

    @Test
    void removeUserShouldRemoveOneUser() {
        UUID joeUserId = UUID.randomUUID();

        User joe = new User(joeUserId, "John", "Johnson",
                Gender.MALE, 22, "joe.johnson@gmail.com");

        given(fakeUserDao.selectUserByUserId(joeUserId)).willReturn(Optional.of(joe));
        given(fakeUserDao.deleteUserById(joeUserId)).willReturn(1);

        int deleteResult = userService.removeUser(joeUserId);

        //verify interaction with mock
        verify(fakeUserDao).selectUserByUserId(joeUserId);
        verify(fakeUserDao).deleteUserById(joeUserId);

        assertThat(deleteResult).isEqualTo(1);
    }

    @Test
    void addUserShouldAddOneNewUser() {
        User joe = new User(null, "John", "Johnson",
                Gender.MALE, 22, "joe.johnson@gmail.com");

        given(fakeUserDao.insertUser(any(UUID.class), any(User.class))).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int insertResult = userService.addUser(joe);

        verify(fakeUserDao).insertUser(any(UUID.class), captor.capture());

        User user = captor.getValue();

        validateUser(user);

        assertThat(insertResult).isEqualTo(1);

    }

    private void validateUser(User user) {
        assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getEmail()).isEqualTo("joe.johnson@gmail.com");
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Johnson");
        assertThat(user.getGender()).isEqualTo(Gender.MALE);
        assertThat(user.getUid()).isNotNull();
    }
}