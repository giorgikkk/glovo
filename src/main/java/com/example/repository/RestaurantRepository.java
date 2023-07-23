package com.example.repository;

import com.example.entities.Restaurant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class RestaurantRepository {
    @PersistenceContext
    private EntityManager em;

    public Collection<Restaurant> getRestaurants(final int page, final int pageSize) {
        TypedQuery<Restaurant> query = em.createQuery("FROM Restaurant r", Restaurant.class);

        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    public Optional<Restaurant> getRestaurant(final long id) {
        TypedQuery<Restaurant> query = em.createQuery("select r from Restaurant r where r.id=:id", Restaurant.class);
        List<Restaurant> resultList = query
                .setParameter("id", id)
                .getResultList();

        return resultList.isEmpty() ? Optional.empty() : Optional.ofNullable(resultList.iterator().next());
    }

    public void addRestaurant(final Restaurant restaurant) {
        em.persist(restaurant);
    }

    public Restaurant updateRestaurant(final long id, final Restaurant restaurant) {
        restaurant.setId(id);
        return em.merge(restaurant);
    }

    public Optional<Restaurant> removeRestaurant(final long id) {
        Optional<Restaurant> restaurant = Optional.ofNullable(em.find(Restaurant.class, id));
        restaurant.ifPresent(r -> em.remove(r));
        return restaurant;
    }
}
