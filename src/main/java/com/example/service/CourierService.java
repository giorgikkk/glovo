package com.example.service;

import com.example.entities.Courier;
import com.example.repository.CourierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class CourierService {
    private final CourierRepository repository;

    @Autowired
    public CourierService(CourierRepository repository) {
        this.repository = repository;
    }

    public Collection<Courier> getCouriers(final int page, final int pageSize) {
        log.info("getting couriers on relevant page");
        return repository.getCouriers(page, pageSize);
    }

    public Optional<Courier> getCourier(final long id) {
        log.info("getting courier by id");
        return repository.getCourier(id);
    }

    public void addCourier(final Courier courier) {
        log.info("creating new courier");
        repository.addCourier(courier);
    }

    public Courier updateCourier(final long id, final Courier courier) {
        log.info("updating courier");
        return repository.updateCourier(id, courier);
    }

    public Optional<Courier> removeCourier(final long id) {
        log.info("removing courier");
        return repository.removeCourier(id);
    }
}
