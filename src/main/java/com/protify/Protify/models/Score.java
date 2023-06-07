package com.protify.Protify.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

@Entity
@Setter
@Getter
@Relation(collectionRelation = "scores", itemRelation = "score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "songs_id")
    private Songs songs;
    @Column(columnDefinition = "float default '0.0'")
    private float score;
}
