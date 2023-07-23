package com.example.repository;

import com.example.entities.Courier;
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
public class CourierRepository {
    @PersistenceContext
    private EntityManager em;

    public Collection<Courier> getCouriers(final int page, final int pageSize) {
        TypedQuery<Courier> query = em.createQuery("FROM Courier c", Courier.class);

        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    public Optional<Courier> getCourier(final long id) {
        TypedQuery<Courier> query = em.createQuery("select c from Courier c where c.id=:id", Courier.class);
        List<Courier> resultList = query
                .setParameter("id", id)
                .getResultList();

        return resultList.isEmpty() ? Optional.empty() : Optional.ofNullable(resultList.iterator().next());
    }

    public void addCourier(final Courier courier) {
        em.persist(courier);
    }

    public Courier updateCourier(final long id, final Courier courier) {
        courier.setId(id);
        return em.merge(courier);
    }

    public Optional<Courier> removeCourier(final long id) {
        Optional<Courier> courier = Optional.ofNullable(em.find(Courier.class, id));
        courier.ifPresent(c -> em.remove(c));
        return courier;
    }
}
