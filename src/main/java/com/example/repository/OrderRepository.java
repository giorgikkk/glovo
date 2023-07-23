package com.example.repository;

import com.example.entities.Order;
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
public class OrderRepository {
    @PersistenceContext
    private EntityManager em;

    public Collection<Order> getOrders(final int page, final int pageSize) {
        TypedQuery<Order> query = em.createQuery("FROM Order o", Order.class);

        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    public Optional<Order> getOrder(final long id) {
        TypedQuery<Order> query = em.createQuery("select o from Order o where o.id=:id", Order.class);
        List<Order> resultList = query
                .setParameter("id", id)
                .getResultList();

        return resultList.isEmpty() ? Optional.empty() : Optional.ofNullable(resultList.iterator().next());
    }

    public void addOrder(final Order order) {
        em.persist(order);
    }

    public Order updateOrder(final long id, final Order order) {
        order.setId(id);
        return em.merge(order);
    }

    public Optional<Order> removeOrder(final long id) {
        Optional<Order> order = Optional.ofNullable(em.find(Order.class, id));
        order.ifPresent(o -> em.remove(o));
        return order;
    }
}
