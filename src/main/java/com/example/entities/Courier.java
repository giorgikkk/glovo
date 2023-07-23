package com.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "courier")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Courier {

    @Id
    @Column(name = "courier_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courier_id_generator")
    @SequenceGenerator(name = "courier_id_generator", sequenceName = "courier_id_gen", allocationSize = 1)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "courier_name", length = 128, nullable = false)
    private String fullName;

    @Column(name = "courier_salary")
    private long salary;

    @JsonIgnore
    @OneToMany(mappedBy = "courier", cascade = CascadeType.REMOVE)
    private Set<Order> orders;
}
