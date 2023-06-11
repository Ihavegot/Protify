package com.protify.Protify.models;

import com.protify.Protify.embeddable.ScoreKey;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

@Entity
@Setter
@Getter
@Builder
@Relation(collectionRelation = "scores", itemRelation = "score")
@NoArgsConstructor
@AllArgsConstructor
public class Score {

    @EmbeddedId
    private ScoreKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name="songs")
    private Songs songs;

    private float score;
}
