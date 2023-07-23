package com.example.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_generator")
    @SequenceGenerator(name = "order_id_generator", sequenceName = "order_id_gen", allocationSize = 1)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "order_address", length = 256, nullable = false)
    private String deliveryAddress;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "courier_id", nullable = false)
    private Courier courier;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
