package com.example.deathparade.services;

import com.example.deathparade.exceptions.ErrorMessages;
import com.example.deathparade.exceptions.NotFoundException;
import com.example.deathparade.models.User;
import com.example.deathparade.models.dto.request.UserRequestDto;
import com.example.deathparade.models.dto.response.UserResponseDto;
import com.example.deathparade.repositories.UserRepository;
import com.example.deathparade.services.mappers.UserMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

/**.
 *
 */

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  /**.
   *
   */

  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  public List<UserResponseDto> getAllUsers() {
    return userRepository.findAll().stream()
            .map(userMapper::toResponseDto).toList();
  }

  /**.
   *
   */

  public UserResponseDto getUserById(Long id) {
    User user = userRepository.findById(id)
              .orElseThrow(() -> new NotFoundException(ErrorMessages.USER_NOT_FOUND));
    return userMapper.toResponseDto(user);
  }

  /**.
   *
   */

  @Transactional
  public UserResponseDto createUser(UserRequestDto dto) {
    User user = userMapper.toEntity(dto);
    return userMapper.toResponseDto(userRepository.save(user));
  }

  /**.
   *
   */

  @Transactional
  public UserResponseDto updateUser(Long id, UserRequestDto dto) {
    User user = userRepository.findById(id).orElseThrow(()
            -> new NotFoundException(ErrorMessages.USER_NOT_FOUND));
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    return userMapper.toResponseDto(userRepository.save(user));
  }

  /**.
   *
   */

  @Transactional
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
