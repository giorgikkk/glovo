package com.example.controller;

import com.example.entities.Order;
import com.example.service.OrderService;
import com.example.exceptions.OrderNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;

@RestController
@RequestMapping("orders")
@Slf4j
public class OrderController {
    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get orders")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found orders", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Collection<Order> getOrders(@Min(1) @Parameter(description = "page index") @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                       @Min(1) @Max(10) @Parameter(description = "page size") @RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.info("Getting all order");
        log.debug("page:{} pageSize:{}", page, pageSize);
        return service.getOrders(page, pageSize);
    }

    @GetMapping("{identifier}")
    @Operation(summary = "Get order by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found order by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Id not found", content = {@Content(mediaType = "application/json")})})
    public Order getOrder(@PathVariable(name = "identifier") final long id) {
        log.info("Getting order by id");
        log.debug("id:{}", id);
        return service.getOrder(id).orElseThrow(() -> new OrderNotFoundException(String.format("Order with id:%d does not exist", id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create order")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Order created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public void addOrder(@RequestBody final Order order) {
        log.info("Creating new order");
        log.debug("order:{}", order);
        service.addOrder(order);
    }

    @PutMapping("{identifier}")
    @Operation(summary = "Update order")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Order updated"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    public Order updateOrder(@PathVariable(name = "identifier") final long id, @RequestBody final Order order) {
        log.info("Updating order");
        log.debug("id:{} order:{}", id, order);
        return service.updateOrder(id, order);
    }

    @DeleteMapping("{identifier}")
    @Operation(summary = "Remove order")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Order deleted"),
            @ApiResponse(responseCode = "404", description = "Id not found")})
    public Order removeOrder(@PathVariable(name = "identifier") final long id) {
        log.info("Deleting order");
        log.debug("id:{}", id);
        return service.removeOrder(id).orElseThrow(() -> new OrderNotFoundException(String.format("Order with id:%d does not exist", id)));
    }
}
