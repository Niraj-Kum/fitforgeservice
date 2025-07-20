package com.niraj.fitforgeservice.fitforge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "workout_days")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(
        sql = "update workout_days set is_archived = true where id = ?"
)
@SQLRestriction("is_archived = false")
@EqualsAndHashCode(callSuper = true)
public class WorkoutDay extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private WorkoutPlan workoutPlan;

    @Column(name = "day_of_cycle", nullable = false)
    private Integer dayOfCycle;

    @Column(nullable = false)
    private String title;

    private String focus;

    private String warmup;

    private String cardio;

    @Column(name = "active_recovery")
    private String activeRecovery;

    @OneToMany(mappedBy = "workoutDay", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ExerciseCategory> exerciseCategories = new HashSet<>();
}