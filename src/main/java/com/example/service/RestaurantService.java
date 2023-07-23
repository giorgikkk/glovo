package com.example.service;

import com.example.entities.Restaurant;
import com.example.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class RestaurantService {
    private final RestaurantRepository repository;

    @Autowired
    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Collection<Restaurant> getRestaurants(final int page, final int pageSize) {
        log.info("getting restaurants on relevant page");
        return repository.getRestaurants(page, pageSize);
    }

    public Optional<Restaurant> getRestaurant(final long id) {
        log.info("getting restaurant by id");
        return repository.getRestaurant(id);
    }

    public void addRestaurant(final Restaurant restaurant) {
        log.info("creating new restaurant");
        repository.addRestaurant(restaurant);
    }

    public Restaurant updateRestaurant(final long id, final Restaurant restaurant) {
        log.info("updating restaurant");
        return repository.updateRestaurant(id, restaurant);
    }

    public Optional<Restaurant> removeRestaurant(final long id) {
        log.info("removing restaurant");
        return repository.removeRestaurant(id);
    }
}
