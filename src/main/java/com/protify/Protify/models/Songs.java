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
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "artist")
    @JsonIgnore
    private Artist artist;
    @Column(columnDefinition = "varchar default 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'")
    private String songFile;

    @ManyToMany(mappedBy = "songs", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Playlist> playlists;

    @OneToMany(mappedBy = "songs", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Score> scores;

    public Songs() {
    }


}
