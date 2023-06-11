package com.protify.Protify.mappers;

import com.protify.Protify.dtos.ScoredSongDto;
import com.protify.Protify.models.Score;
import com.protify.Protify.models.Songs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SongMapper {

    @Mapping(target = "id", source = "song.id")
ScoredSongDto songToScoredSong(Songs song, Score score);
}
