package com.protify.Protify.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PlaylistSongsDto {
    private Long playlistId;
    private Long songId;
}
