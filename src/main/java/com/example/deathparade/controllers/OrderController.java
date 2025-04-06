package com.example.deathparade.controllers;

import com.example.deathparade.models.dto.request.OrderRequestDto;
import com.example.deathparade.models.dto.response.OrderResponseDto;
import com.example.deathparade.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**.
 *
 */

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Operations related to orders")
public class OrderController {
  private final OrderService orderService;

  /**.
   *
   */

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @Operation(
      summary = "get all orders",
      description = "Successfully retrieved list of orders",
      responses = {
          @ApiResponse(responseCode = "200",
            description = "List of orders retrieved successfully"),
          @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(schema = @Schema(example =
                "{ \"timestamp\": \"2025-03-24T12:00:00\","
              + " \"status\": 500, \"message\":"
              + " \"Internal server error\", \"path\": \"/places\" }")))
      }

  )
  @GetMapping
  public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
    return ResponseEntity.ok(orderService.getAllOrders());
  }

  /**.
   *
   */

  @Operation(
      summary = "Get order by ID",
      description = "Retrieves order details by order ID",
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Order found and returned",
          content = @Content(schema = @Schema(implementation = OrderResponseDto.class))
          ),
        @ApiResponse(
          responseCode = "404",
          description = "Order not found",
          content = @Content(schema = @Schema(example = """
            {
              "timestamp": "2025-03-24T12:00:00",
              "status": 404,
              "message": "Order not found with id: 123",
              "path": "/orders/123"
            }"""))
          ),
        @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(example = """
            {
              "timestamp": "2025-03-24T12:00:00",
              "status": 500,
              "message": "Internal server error",
              "path": "/orders/123"
            }"""))
          )
      }
  )
  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
    return ResponseEntity.ok(orderService.getOrderById(id));
  }

  /**.
   *
   */

  @Operation(
      summary = "Get orders by user ID",
      description = "Retrieves all orders associated with specific user",
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Successfully retrieved user's orders",
          content = @Content(array = @ArraySchema(schema =
          @Schema(implementation = OrderResponseDto.class)))
          ),
        @ApiResponse(
          responseCode = "400",
          description = "Invalid user ID parameter",
          content = @Content(schema = @Schema(example = """
            {
             "timestamp": "2025-03-24T12:00:00",
             "status": 400,
             "message": "User ID parameter is required",
             "path": "/orders/by-user"
            }"""))
          ),
        @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(example = """
            {
              "timestamp": "2025-03-24T12:00:00",
              "status": 500,
              "message": "Internal server error",
              "path": "/orders/by-user"
            }"""))
          )
      }
  )
  @GetMapping("/by-user")
  public ResponseEntity<List<OrderResponseDto>> findOrderByUserId(@RequestParam Long userId) {
    return ResponseEntity.ok(orderService.findOrderByUserId(userId));
  }

  /**.
   *
   */

  @Operation(
      summary = "Get orders by user Id (Native query)",
      description = "Retrieves all orders associated with specific user using native SQL query",
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Successfully retrieved user's orders",
          content = @Content(array = @ArraySchema(schema =
          @Schema(implementation = OrderResponseDto.class)))
          ),
        @ApiResponse(
          responseCode = "400",
          description = "Invalid user ID parameter",
          content = @Content(schema = @Schema(example = """
            {
              "timestamp": "2025-03-24T12:00:00",
              "status": 400,
              "message": "User ID parameter is required",
              "path": "/orders/by-user-native"
            }"""))
          ),
        @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(example = """
            {
              "timestamp": "2025-03-24T12:00:00",
              "status": 500,
              "message": "Internal server error",
              "path": "/orders/by-user-native"
            }"""))
          )
      }
  )
  @GetMapping("/by-user-native")
  public ResponseEntity<List<OrderResponseDto>> findOrderByUserNative(@RequestParam Long userId) {
    return ResponseEntity.ok(orderService.findOrderByUserIdNative(userId));
  }

  /**.
   *
   */

  @Operation(
      summary = "Create new order",
      description =  "Create a new order with yhe provide data",
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Order successfully created"
          ),
        @ApiResponse(
          responseCode = "400",
          description = "Invalid input",
          content = @Content(schema = @Schema(example =
            "{ \"timestamp\": \"2025-03-24T12:00:00\", "
            + "\"status\": 400, \"message\": \"Invalid input\", "
            + "\"path\": \"/orders\" }"))
          ),
        @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(example =
            "{ \"timestamp\": \"2025-03-24T12:00:00\", "
            + "\"status\": 500, \"message\": \"Internal server error\", "
            + "\"path\": \"/orders\" }"))
          )
      }
  )
  @PostMapping
  public ResponseEntity<OrderResponseDto> createOrder(
          @RequestBody OrderRequestDto orderRequestDto) {
    return ResponseEntity.ok(orderService.createOrder(orderRequestDto));
  }

  /**.
   *
   */

  @Operation(
      summary = "update an existing order",
      description = "Update the details of an existing order ",
      responses = {
        @ApiResponse(responseCode = "200", description = "Order updated successfully"),
        @ApiResponse(
          responseCode = "404", description = "Route not found",
          content = @Content(schema = @Schema(example =
            "{ \"timestamp\": \"2025-03-24T12:00:00\", "
             + "\"status\": 404, \"message\": \"Order not found\","
             + " \"path\": \"/order/123\" }"))
          ),
        @ApiResponse(
          responseCode = "400", description = "Invalid input",
          content = @Content(schema = @Schema(example =
            "{ \"timestamp\": \"2025-03-24T12:00:00\", \"status\": 400,"
            + " \"message\": \"Invalid input\","
            + " \"path\": \"/orders/123\" }"))
          ),
        @ApiResponse(
          responseCode = "500", description = "Internal server error",
          content = @Content(schema = @Schema(example =
            "{ \"timestamp\": \"2025-03-24T12:00:00\","
            + " \"status\": 500, \"message\": \"Internal server error\", "
            + "\"path\": \"/orders/123\" }"))
          )
      }
  )
  @PutMapping("/{id}")
  public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long id,
           @RequestBody OrderRequestDto orderRequestDto) {
    return ResponseEntity.ok(orderService.updateOrder(id, orderRequestDto));
  }

  /**.
   *
   */

  @Operation(
      summary = "Delete order",
      description = "Deletes an existing order by ID",
      responses = {
        @ApiResponse(
          responseCode = "204",
          description = "Order successfully deleted"
          ),
        @ApiResponse(
           responseCode = "404",
           description = "Order not found",
           content = @Content(schema = @Schema(example = """
           {
            "timestamp": "2025-03-24T12:00:00",
            "status": 404,
            "message": "Order not found with id: 123",
            "path": "/orders/123"
            }"""))
           ),
        @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(example = """
          {
            "timestamp": "2025-03-24T12:00:00",
            "status": 500,
            "message": "Internal server error",
            "path": "/orders/123"
          }"""))
          )
      }
    )
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }
}
