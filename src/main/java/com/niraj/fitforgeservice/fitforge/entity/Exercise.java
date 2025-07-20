package com.niraj.fitforgeservice.fitforge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(name = "exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(
        sql = "update exercises set is_archived = true where id = ?"
)
@SQLRestriction("is_archived = false")
@EqualsAndHashCode(callSuper = true)
public class Exercise extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ExerciseCategory exerciseCategory;

    @Column(nullable = false)
    private String name;

    private String details;

    @Column(name = "display_order")
    private Integer displayOrder;
}