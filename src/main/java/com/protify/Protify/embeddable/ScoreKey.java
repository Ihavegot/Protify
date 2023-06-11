package com.protify.Protify.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreKey implements Serializable {

@Column(name="user_id")
Long userId;

    @Column(name="songs")
    Long songId;
}
