package com.protify.Protify.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.util.Set;

@Entity
@Setter
@Getter
public class Songs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "artist")
    private Artist artist;
    private Blob songFile;

    @ManyToMany(mappedBy = "songs")
    private Set<Playlist> playlists;
}
