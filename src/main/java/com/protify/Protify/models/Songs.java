package com.protify.Protify.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Blob;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@Relation(collectionRelation = "songs", itemRelation = "song")
public class Songs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "artist")
    @JsonIgnore
    private Artist artist;
    private Blob songFile;

    @ManyToMany(mappedBy = "songs", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Playlist> playlists;

    public Songs() {
    }
}
