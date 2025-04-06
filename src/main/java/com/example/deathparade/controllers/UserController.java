package com.example.deathparade.controllers;

import com.example.deathparade.models.dto.request.UserRequestDto;
import com.example.deathparade.models.dto.response.UserResponseDto;
import com.example.deathparade.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Users", description = "Operations related to users management")
public class UserController {

  private final UserService userService;

  /**.
   *
   */

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(
      summary = "Get all users",
        description = "Retrieves a list of all registered users",
          responses = {
            @ApiResponse(responseCode = "200",
                          description = "Successfully retrieved list of users"),
              @ApiResponse (responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(example = """
                            {
                              "timestamp": "2025-03-24T12:00:00",
                              "status": 500,
                              "message": "Internal server error",
                              "path": "/users"
                            }""")))
          }
      )
  @GetMapping
  public ResponseEntity<List<UserResponseDto>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  /**.
   *
   */

  @Operation(
      summary = "Get user by ID",
      description = "Retrieves user details by user ID",
      responses = {
        @ApiResponse(responseCode = "200",
                     description = "User found and returned",
                     content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
        @ApiResponse(responseCode = "404",
                     description = "User not found",
                     content = @Content(schema = @Schema(example = """
                       {
                         "timestamp": "2025-03-24T12:00:00",
                         "status": 404,
                         "message": "User not found with id: 123",
                         "path": "/users/123"
                       }"""))),
        @ApiResponse(responseCode = "500",
                     description = "Internal server error",
                     content = @Content(schema = @Schema(example = """
                       {
                         "timestamp": "2025-03-24T12:00:00",
                         "status": 500,
                         "message": "Internal server error",
                         "path": "/users/123"
                       }""")))
      }
  )
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  /**.
   *
   */

  @Operation(
          summary = "Create new user",
          description = "Creates a new user account",
          responses = {
            @ApiResponse(responseCode = "200",
                         description = "User successfully created",
                         content = @Content(schema =
                         @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400",
                         description = "Invalid user data provided",
                         content = @Content(schema = @Schema(example = """
                           {
                             "timestamp": "2025-03-24T12:00:00",
                             "status": 400,
                             "message": "Validation failed for object='userRequestDto'",
                             "errors": [
                               "Name must be between 3 and 25 characters",
                               "Email must be valid"
                             ],
                             "path": "/users"
                           }"""))),
            @ApiResponse(responseCode = "500",
                         description = "Internal server error",
                         content = @Content(schema = @Schema(example = """
                           {
                             "timestamp": "2025-03-24T12:00:00",
                             "status": 500,
                             "message": "Internal server error",
                             "path": "/users"
                           }""")))
          }
  )
  @PostMapping
  public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto dto) {
    return ResponseEntity.ok(userService.createUser(dto));
  }

  /**.
   *
   */

  @Operation(
          summary = "Update user",
          description = "Updates existing user details",
          responses = {
              @ApiResponse(responseCode = "200",
                          description = "User successfully updated",
                          content = @Content(schema =
                          @Schema(implementation = UserResponseDto.class))),
              @ApiResponse(responseCode = "400",
                          description = "Invalid user data provided",
                          content = @Content(schema = @Schema(example = """
                                {
                                    "timestamp": "2025-03-24T12:00:00",
                                    "status": 400,
                                    "message": "Validation failed for object='userRequestDto'",
                                    "errors": [
                                        "Name must be between 3 and 25 characters",
                                        "Email must be valid"
                                    ],
                                    "path": "/users/123"
                                }"""))),
              @ApiResponse(responseCode = "404",
                          description = "User not found",
                          content = @Content(schema = @Schema(example = """
                                {
                                    "timestamp": "2025-03-24T12:00:00",
                                    "status": 404,
                                    "message": "User not found with id: 123",
                                    "path": "/users/123"
                                }"""))),
              @ApiResponse(responseCode = "500",
                          description = "Internal server error",
                          content = @Content(schema = @Schema(example = """
                                {
                                    "timestamp": "2025-03-24T12:00:00",
                                    "status": 500,
                                    "message": "Internal server error",
                                    "path": "/users/123"
                                }""")))
          }
  )
  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                    @RequestBody @Valid UserRequestDto dto) {
    return ResponseEntity.ok(userService.updateUser(id, dto));
  }

  /**.
   *
   */

  @Operation(
          summary = "Delete user",
          description = "Deletes user account by ID",
          responses = {
              @ApiResponse(responseCode = "204",
                          description = "User successfully deleted"),
              @ApiResponse(responseCode = "404",
                          description = "User not found",
                          content = @Content(schema = @Schema(example = """
                                {
                                    "timestamp": "2025-03-24T12:00:00",
                                    "status": 404,
                                    "message": "User not found with id: 123",
                                    "path": "/users/123"
                                }"""))),
              @ApiResponse(responseCode = "500",
                          description = "Internal server error",
                          content = @Content(schema = @Schema(example = """
                                {
                                    "timestamp": "2025-03-24T12:00:00",
                                    "status": 500,
                                    "message": "Internal server error",
                                    "path": "/users/123"
                                }""")))
          }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}
