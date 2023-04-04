package com.protify.Protify.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
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

    public Songs() {}
}
