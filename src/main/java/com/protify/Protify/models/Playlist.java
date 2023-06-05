package com.protify.Protify.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

import java.util.Set;

@Entity
@Setter
@Getter
@Relation(collectionRelation = "playlists", itemRelation = "playlist")
@Builder
@AllArgsConstructor
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
  @JsonIgnore  private User user;
    @ManyToMany
    @JsonIgnore
    private Set<Songs> songs;

    public Playlist() {

    }
}
