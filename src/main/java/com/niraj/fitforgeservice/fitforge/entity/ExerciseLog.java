package com.niraj.fitforgeservice.fitforge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(name = "exercise_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(
        sql = "update exercise_logs set is_archived = true where id = ?"
)
@SQLRestriction("is_archived = false")
@EqualsAndHashCode(callSuper = true)
public class ExerciseLog extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @Column(name = "exercise_name", nullable = false)
    private String exerciseName;

    @Column(name = "weight_lbs")
    private Double weightLbs;

    private Integer reps;
}