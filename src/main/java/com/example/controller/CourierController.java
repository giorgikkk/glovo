package com.example.controller;


import com.example.entities.Courier;
import com.example.service.CourierService;
import com.example.exceptions.CourierNotFoundException;
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
@RequestMapping("couriers")
@Slf4j
public class CourierController {
    private final CourierService service;

    @Autowired
    public CourierController(CourierService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get couriers")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found courier", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Collection<Courier> getCouriers(@Min(1) @Parameter(description = "page index") @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                           @Min(1) @Max(10) @Parameter(description = "page size") @RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.info("Getting all courier");
        log.debug("page:{} pageSize:{}", page, pageSize);
        return service.getCouriers(page, pageSize);
    }

    @GetMapping("{identifier}")
    @Operation(summary = "Get courier by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found courier by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Id not found", content = {@Content(mediaType = "application/json")})})
    public Courier getCourier(@PathVariable(name = "identifier") final long id) {
        log.info("Getting courier by id");
        log.debug("id:{}", id);
        return service.getCourier(id).orElseThrow(() -> new CourierNotFoundException(String.format("Courier with id:%d does not exist", id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create courier")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Courier created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public void addCourier(@RequestBody final Courier courier) {
        log.info("Creating new courier");
        log.debug("courier:{}", courier);
        service.addCourier(courier);
    }

    @PutMapping("{identifier}")
    @Operation(summary = "Update courier")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Courier updated"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    public Courier updateCourier(@PathVariable(name = "identifier") final long id, @RequestBody final Courier courier) {
        log.info("Updating courier");
        log.debug("id:{} courier:{}", id, courier);
        return service.updateCourier(id, courier);
    }

    @DeleteMapping("{identifier}")
    @Operation(summary = "Remove courier")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Courier deleted"),
            @ApiResponse(responseCode = "404", description = "Id not found")})
    public Courier removeCourier(@PathVariable(name = "identifier") final long id) {
        log.info("Deleting courier");
        log.debug("id:{}", id);
        return service.removeCourier(id).orElseThrow(() -> new CourierNotFoundException(String.format("Courier with id:%d does not exist", id)));
    }
}
