package com.example.deathparade.controllers;

import com.example.deathparade.models.dto.request.CoffinRequestDto;
import com.example.deathparade.models.dto.response.CoffinResponseDto;
import com.example.deathparade.services.CoffinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping ("/coffins")
@Tag(name = "Coffins", description = "Operations related to coffins")
public class CoffinController {

  private final CoffinService coffinService;

  /**.
   *
   */

  public CoffinController(CoffinService coffinService) {
    this.coffinService = coffinService;
  }

  /**.
   *
   */

  @Operation(
       summary = "Get all coffins",
       description = "Retrieve a list of all places",
       responses = {
         @ApiResponse(responseCode = "200",
           description = "List of places retrieved successfully"),
         @ApiResponse(responseCode = "500", description = "Internal server error",
           content = @Content(schema = @Schema(example =
           "{ \"timestamp\": \"2025-03-24T12:00:00\","
           + " \"status\": 500, \"message\":"
           + " \"Internal server error\", \"path\": \"/places\" }")))
       }
  )
  @GetMapping
  public ResponseEntity<List<CoffinResponseDto>> getAllCoffins() {
    return ResponseEntity.ok(coffinService.getAllCoffins());
  }

  /**.
   *
   */
  @Operation(
        summary = "Get coffins by they ids",
        description = "Retrieve a coffin by it's id",
        responses = {
          @ApiResponse(responseCode = "200",
              description = "Coffin retrieved successfully"),
          @ApiResponse(responseCode = "400", description = "Coffin ID is required",
              content = @Content(schema = @Schema(example =
              "{ \"timestamp\": \"2025-03-24T12:00:00\","
              + " \"status\": 400, \"message\": \"Coffin ID is required\","
              + " \"path\": \"/coffins/id\" }"))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
              content = @Content(schema = @Schema(example =
              "{ \"timestamp\": \"2025-03-24T12:00:00\","
              + " \"status\": 500, \"message\": \"Internal server error\","
              + " \"path\": \"/coffins/id\" }")))
        }
  )
  @GetMapping("/{id}")
  public ResponseEntity<CoffinResponseDto> getCoffinById(@PathVariable Long id) {
    return ResponseEntity.ok(coffinService.getCoffinById(id));
  }

  /**.
   *
   */
  @Operation(
        summary = "Create a new coffin",
        description = "Create a new coffin using the provide details",
        responses = {
            @ApiResponse(responseCode = "200", description = "Coffin created successfully"),
            @ApiResponse(
              responseCode = "400",
              description = "Invalid input",
              content = @Content(schema = @Schema(example =
                "{ \"timestamp\": \"2025-03-24T12:00:00\", "
                + "\"status\": 400, \"message\": \"Invalid input\","
                + " \"path\": \"/coffins\" }"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
              content = @Content(schema = @Schema(example =
              "{ \"timestamp\": \"2025-03-24T12:00:00\","
              + " \"status\": 500, \"message\": \"Internal server error\","
              + " \"path\": \"/coffins\" }")))
        }
  )
  @PostMapping
  public ResponseEntity<CoffinResponseDto> createCoffin(@RequestBody CoffinRequestDto dto) {
    return ResponseEntity.ok(coffinService.createCoffin(dto));
  }

  /**.
   *
   */
  @Operation(
      summary = "Update an existing place",
      description = "Update the details of an existing place using the provided ID and data.",
      responses = {
        @ApiResponse(responseCode = "200",
          description = "Coffin updated successfully"),
        @ApiResponse(responseCode = "404",
          description = "Place not found",
          content = @Content(schema = @Schema(example =
          "{ \"timestamp\": \"2025-03-24T12:00:00\","
          + " \"status\": 404, \"message\": \"Coffin not found\", "
          + "\"path\": \"/coffins/123\" }"))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
          content = @Content(schema = @Schema(example =
          "{ \"timestamp\": \"2025-03-24T12:00:00\", "
          + "\"status\": 400, \"message\": \"Invalid input\","
          +  " \"path\": \"/coffins/123\" }"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
          content = @Content(schema = @Schema(example =
          "{ \"timestamp\": \"2025-03-24T12:00:00\", \"status\": 500,"
          + " \"message\": \"Internal server error\","
          + " \"path\": \"/coffins/123\" }")))
      }
  )
  @PutMapping("/{id}")
  public ResponseEntity<CoffinResponseDto> updateCoffin(@PathVariable Long id,
                                                        @RequestBody CoffinRequestDto dto) {
    return ResponseEntity.ok(coffinService.updateCoffin(id, dto));
  }

  /**.
   *
   */
  @Operation(
      summary = "Delete a coffin",
      description = "Delete a specific coffin by its ID.",
      responses = {
        @ApiResponse(responseCode = "204",
          description = "Coffin deleted successfully"),
        @ApiResponse(responseCode = "404",
          description = "Coffin not found",
          content = @Content(schema = @Schema(example =
          "{ \"timestamp\": \"2025-03-24T12:00:00\", "
          + "\"status\": 404, \"message\": \"Coffin not found\", "
          + "\"path\": \"/coffins/123\" }"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
          content = @Content(schema = @Schema(example =
          "{ \"timestamp\": \"2025-03-24T12:00:00\", "
          + "\"status\": 500, \"message\": \"Internal server error\", "
          + "\"path\": \"/coffins/123\" }")))
      }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCoffin(@PathVariable Long id) {
    coffinService.deleteCoffins(id);
    return ResponseEntity.noContent().build();
  }
}
