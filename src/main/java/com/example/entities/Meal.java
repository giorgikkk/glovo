package com.example.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "meal")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Meal {

    @Id
    @Column(name = "meal_id")
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "meal_name", length = 128, nullable = false)
    private String mealName;
}
