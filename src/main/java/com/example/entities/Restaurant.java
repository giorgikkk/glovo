package com.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant {

    @Id
    @Column(name = "restaurant_id")
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "restaurant_name", length = 128, nullable = false)
    private String restaurantName;

    @Column(name = "restaurant_address", length = 512, nullable = false)
    private String address;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "restaurant_meal_map",
            joinColumns = @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id", referencedColumnName = "meal_id")
    )
    private Set<Meal> meals;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
    private Set<Order> orders;
}
