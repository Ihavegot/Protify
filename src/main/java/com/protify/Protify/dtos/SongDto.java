package com.protify.Protify.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SongDto {
    private String title;
    private Long artistId;
}
