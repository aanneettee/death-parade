package com.example.deathparade.services.mappers;

import com.example.deathparade.models.User;
import com.example.deathparade.models.dto.request.UserRequestDto;
import com.example.deathparade.models.dto.response.UserResponseDto;
import org.springframework.stereotype.Component;

/**.
 *
 */

@Component
public class UserMapper {

  /**.
   *
   */

  public User toEntity(UserRequestDto dto) {
    User user = new User();
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    return user;
  }

  /**.
   *
   */

  public UserResponseDto toResponseDto(User user) {
    UserResponseDto dto = new UserResponseDto();
    dto.setId(user.getId());
    dto.setName(user.getName());
    dto.setEmail(user.getEmail());
    return dto;
  }

}
