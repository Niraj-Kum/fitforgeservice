package com.niraj.fitforgeservice.fitforge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "roadmap_phases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "update roadmap_phases set is_archived = true where id = ?")
@SQLRestriction("is_archived = false")
@EqualsAndHashCode(callSuper = true)
public class RoadmapPhase extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private RoadmapSection section;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "display_order")
    private Integer displayOrder;
}
