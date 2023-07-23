package com.example.controller;

import com.example.entities.Restaurant;
import com.example.service.RestaurantService;
import com.example.exceptions.RestaurantNotFoundException;
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
@RequestMapping("restaurants")
@Slf4j
public class RestaurantController {
    private final RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get restaurant")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found restaurant", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Collection<Restaurant> getRestaurants(@Min(1) @Parameter(description = "page index") @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                                 @Min(1) @Max(10) @Parameter(description = "page size") @RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.info("Getting all restaurant");
        log.debug("page:{} pageSize:{}", page, pageSize);
        return service.getRestaurants(page, pageSize);
    }

    @GetMapping("{identifier}")
    @Operation(summary = "Get restaurant by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found restaurant by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Id not found", content = {@Content(mediaType = "application/json")})})
    public Restaurant getRestaurant(@PathVariable(name = "identifier") final long id) {

        log.info("Getting restaurant by id");
        log.debug("id:{}", id);
        return service.getRestaurant(id).orElseThrow(() -> new RestaurantNotFoundException(String.format("Restaurant with id:%d does not exist", id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create restaurant")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Restaurant created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public void addRestaurant(@RequestBody final Restaurant restaurant) {

        log.info("Creating new restaurant");
        log.debug("restaurant:{}", restaurant);
        service.addRestaurant(restaurant);
    }

    @PutMapping("{identifier}")
    @Operation(summary = "Update restaurant")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Restaurant updated"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    public Restaurant updateRestaurant(@PathVariable(name = "identifier") final long id, @RequestBody final Restaurant restaurant) {
        log.info("Updating restaurant");
        log.debug("id:{} restaurant:{}", id, restaurant);
        return service.updateRestaurant(id, restaurant);
    }

    @DeleteMapping("{identifier}")
    @Operation(summary = "Remove restaurant")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Restaurant deleted"),
            @ApiResponse(responseCode = "404", description = "Id not found")})
    public Restaurant removeRestaurant(@PathVariable(name = "identifier") final long id) {
        log.info("Deleting restaurant");
        log.debug("id:{}", id);
        return service.removeRestaurant(id).orElseThrow(() -> new RestaurantNotFoundException(String.format("Restaurant with id:%d does not exist", id)));
    }
}
