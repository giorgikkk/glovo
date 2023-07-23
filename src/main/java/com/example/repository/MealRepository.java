package com.example.repository;

import com.example.entities.Meal;
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
public class MealRepository {
    @PersistenceContext
    private EntityManager em;

    public Collection<Meal> getMeals(final int page, final int pageSize) {
        TypedQuery<Meal> query = em.createQuery("FROM Meal m", Meal.class);

        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    public Optional<Meal> getMeal(final long id) {
        TypedQuery<Meal> query = em.createQuery("select m from Meal m where m.id=:id", Meal.class);
        List<Meal> resultList = query
                .setParameter("id", id)
                .getResultList();

        return resultList.isEmpty() ? Optional.empty() : Optional.ofNullable(resultList.iterator().next());
    }

    public void addMeal(final Meal meal) {
        em.persist(meal);
    }

    public Meal updateMeal(final long id, final Meal meal) {
        meal.setId(id);
        return em.merge(meal);
    }

    public Optional<Meal> removeMeal(final long id) {
        Optional<Meal> meal = Optional.ofNullable(em.find(Meal.class, id));
        meal.ifPresent(m -> em.remove(m));
        return meal;
    }
}
