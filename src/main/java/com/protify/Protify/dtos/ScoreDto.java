package com.protify.Protify.dtos;

import com.protify.Protify.models.Songs;
import com.protify.Protify.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ScoreDto {
    private Long userId;
    private Long songId;
}
