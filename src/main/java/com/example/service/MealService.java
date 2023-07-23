package com.example.service;

import com.example.entities.Meal;
import com.example.repository.MealRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class MealService {

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Collection<Meal> getMeals(final int page, final int pageSize) {
        log.info("getting meals on relevant page");
        return repository.getMeals(page, pageSize);
    }

    public Optional<Meal> getMeal(final long id) {
        log.info("getting meal by id");
        return repository.getMeal(id);
    }

    public void addMeal(final Meal meal) {
        log.info("creating new meal");
        repository.addMeal(meal);
    }

    public Meal updateMeal(final long id, final Meal meal) {
        log.info("updating meal");
        return repository.updateMeal(id, meal);
    }

    public Optional<Meal> removeMeal(final long id) {
        log.info("removing meal");
        return repository.removeMeal(id);
    }
}
