package com.protify.Protify;

import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Blob;
import java.util.Set;

@Getter
@Setter
public class SongsModel extends RepresentationModel<SongsModel> {
    private long id;
    private String title;

    private Artist artist;
    private Blob songFile;
    private Set<Playlist> playlists;
}
