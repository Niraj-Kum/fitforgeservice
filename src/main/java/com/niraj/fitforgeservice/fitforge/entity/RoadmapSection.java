package com.niraj.fitforgeservice.fitforge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "roadmap_sections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "update roadmap_sections set is_archived = true where id = ?")
@SQLRestriction("is_archived = false")
@EqualsAndHashCode(callSuper = true)
public class RoadmapSection extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "icon", length = 500)
    private String icon;

    @Column(name = "display_order")
    private Integer displayOrder; // Integer for nullable int
}