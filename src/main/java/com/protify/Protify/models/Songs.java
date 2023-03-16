package com.protify.Protify.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Songs {
    @Id
    private int id;
    private String title;
    private int artist_id;
}
