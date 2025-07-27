package com.niraj.fitforgeservice.fitforge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "roadmap_resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "update roadmap_resources set is_archived = true where id = ?")
@SQLRestriction("is_archived = false")
@EqualsAndHashCode(callSuper = true)
public class RoadmapResource extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private RoadmapTopic topic;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "url", length = 300)
    private String url;
}
