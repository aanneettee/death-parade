package com.example.deathparade.controllers;

import com.example.deathparade.models.dto.request.UserRequestDto;
import com.example.deathparade.models.dto.response.UserResponseDto;
import com.example.deathparade.services.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**.
 *
 */

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  /**.
   *
   */

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**.
   *
   */

  @GetMapping
  public ResponseEntity<List<UserResponseDto>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  /**.
   *
   */

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  /**.
   *
   */

  @PostMapping
  public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto dto) {
    return ResponseEntity.ok(userService.createUser(dto));
  }

  /**.
   *
   */

  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                    @RequestBody UserRequestDto dto) {
    return ResponseEntity.ok(userService.updateUser(id, dto));
  }

  /**.
   *
   */

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}
