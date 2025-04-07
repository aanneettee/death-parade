package com.example.deathparade.service;

import com.example.deathparade.exceptions.ErrorMessages;
import com.example.deathparade.exceptions.NotFoundException;
import com.example.deathparade.models.User;
import com.example.deathparade.models.dto.request.UserRequestDto;
import com.example.deathparade.models.dto.response.UserResponseDto;
import com.example.deathparade.services.UserService;
import com.example.deathparade.services.mappers.UserMapper;
import com.example.deathparade.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    @Mock
    private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        User user = new User();
        UserResponseDto dto = new UserResponseDto();
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(dto);
        List<UserResponseDto> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(userRepository).findAll();
    }

    @Test void getUserById_WhenUserFound_ShouldReturnUser() {
        Long id = 1L;
        User user = new User();
        UserResponseDto dto = new UserResponseDto();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(dto);
        UserResponseDto result = userService.getUserById(id);
        assertEquals(dto, result); verify(userRepository).findById(id);
    }
    @Test
    void getUserById_WhenUserNotFound_ShouldThrowException() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userService.getUserById(id));

        assertEquals(ErrorMessages.USER_NOT_FOUND, exception.getMessage());
    }
    @Test void updateUser_WhenUserExists_ShouldUpdateAndReturnUser() {
        Long id = 1L;
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setName("Updated Name");
        requestDto.setEmail("updated@example.com");
        User user = new User();
        UserResponseDto responseDto = new UserResponseDto();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);
        UserResponseDto result = userService.updateUser(id, requestDto);
        assertEquals("Updated Name", user.getName());
        assertEquals("updated@example.com", user.getEmail());
        assertEquals(responseDto, result); verify(userRepository).save(user);
    }

    @Test
    void updateUser_WhenUserNotFound_ShouldThrowException() {
        // Arrange
        Long id = 1L;
        UserRequestDto requestDto = new UserRequestDto("Test User", "test@example.com");
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userService.updateUser(id, requestDto));

        assertEquals(ErrorMessages.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void deleteUser_ShouldDeleteAndRemoveFromCache() {
        // Arrange
        Long id = 1L;

        // Act
        userService.deleteUser(id);

        // Assert
        verify(userRepository).deleteById(id);
    }
}
