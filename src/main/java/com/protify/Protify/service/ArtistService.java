package com.protify.Protify.service;

import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.repository.ArtistRepository;
import com.protify.Protify.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;
    public Page<Artist> getArtist(Pageable page){
        return artistRepository.findAll(page);
    }

    public Optional<Artist> getSingleArtist(long id){
        return artistRepository.findById(id);
    }
}
