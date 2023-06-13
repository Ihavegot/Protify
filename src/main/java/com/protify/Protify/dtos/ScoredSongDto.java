package com.protify.Protify.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Blob;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Relation(collectionRelation = "songs", itemRelation = "song")
public class ScoredSongDto {



    private long id;
    private String title;


    private String songFile;

    private  Float score;

}
