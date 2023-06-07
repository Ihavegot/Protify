package com.protify.Protify.service;

import com.protify.Protify.Exceptions.ResourceNotFoundException;
import com.protify.Protify.dtos.ArtistDto;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.repository.ArtistRepository;
import com.protify.Protify.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    public Page<Artist> getArtist(Pageable page) {
        return artistRepository.findAll(page);
    }

    public Artist getSingleArtist(long id) {
        return artistRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No Artist with id" +id));
    }

    public Artist addSingleArtist(ArtistDto artistDto) {
        Artist artist = new Artist();
        artist.setName(artistDto.getName());
        artist.setSurname(artistDto.getSurname());
        artist.setArtistName(artistDto.getArtistName());
        return artistRepository.save(artist);
    }

    public ResponseEntity<Artist> updateSingleArtist(Long id, ArtistDto artist) {
        Artist updatedArtist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Artist with id " + id));
        updatedArtist.setName(artist.getName());
        updatedArtist.setSurname(artist.getSurname());
        updatedArtist.setArtistName(artist.getArtistName());

        artistRepository.save(updatedArtist);
        return ResponseEntity.ok(updatedArtist);
    }

    public void deleteSingleArtist(long id) {
        artistRepository.deleteById(id);
    }
}
