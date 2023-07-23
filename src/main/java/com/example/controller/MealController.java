package com.example.controller;

import com.example.entities.Meal;
import com.example.service.MealService;
import com.example.exceptions.MealNotFoundException;
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
@RequestMapping("meals")
@Slf4j
public class MealController {
    private final MealService service;

    @Autowired
    public MealController(MealService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get meal")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found meal", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Collection<Meal> getMeals(@Min(1) @Parameter(description = "page index") @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                     @Min(1) @Max(10) @Parameter(description = "page size") @RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.info("Getting all meals");
        log.debug("page:{} pageSize:{}", page, pageSize);
        return service.getMeals(page, pageSize);
    }

    @GetMapping("{identifier}")
    @Operation(summary = "Get meal by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found meal by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Id not found", content = {@Content(mediaType = "application/json")})})
    public Meal getMeal(@PathVariable(name = "identifier") final long id) {
        log.info("Getting meal by id");
        log.debug("id:{}", id);
        return service.getMeal(id).orElseThrow(() -> new MealNotFoundException(String.format("Meal with id:%d does not exist", id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create meal")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Meal created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public void addMeal(@RequestBody final Meal meal) {
        log.info("Creating new meal");
        log.debug("meal:{}", meal);
        service.addMeal(meal);
    }

    @PutMapping("{identifier}")
    @Operation(summary = "Update meal")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Meal updated"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    public Meal updateMeal(@PathVariable(name = "identifier") final long id, @RequestBody final Meal meal) {
        log.info("Updating meal");
        log.debug("id:{} meal:{}", id, meal);
        return service.updateMeal(id, meal);
    }

    @DeleteMapping("{identifier}")
    @Operation(summary = "Remove meal")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Meal deleted"),
            @ApiResponse(responseCode = "404", description = "Id not found")})
    public Meal removeMeal(@PathVariable(name = "identifier") final long id) {
        log.info("Deleting meal");
        log.debug("id:{}", id);
        return service.removeMeal(id).orElseThrow(() -> new MealNotFoundException(String.format("Meal with id:%d does not exist", id)));
    }
}
