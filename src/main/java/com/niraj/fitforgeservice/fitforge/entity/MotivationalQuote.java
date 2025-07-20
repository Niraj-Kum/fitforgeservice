package com.niraj.fitforgeservice.fitforge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "motivational_quotes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(
        sql = "update motivational_quotes set is_archived = true where id = ?"
)
@SQLRestriction("is_archived = false")
@EqualsAndHashCode(callSuper = true)
public class MotivationalQuote extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String quote;

    private String author;
}
