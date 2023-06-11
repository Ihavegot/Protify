package com.protify.Protify.service;

import com.protify.Protify.Exceptions.ResourceNotFoundException;
import com.protify.Protify.dtos.SongDto;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.repository.ArtistRepository;
import com.protify.Protify.repository.PlaylistRepository;
import com.protify.Protify.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    public final ArtistRepository artistRepository;
    public final PlaylistRepository playlistRepository;

    public Page<Songs> getSongs(Pageable page) {
        return songRepository.findAll(page);
    }

    public Songs getSingleSong(Long id) {
        return songRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not song with id " + id));
    }

    public Page<Songs> getSongsByArtist(Long id, Pageable page) {
        return songRepository.findByArtistId(id, page);
    }

    public Page<Songs> getSongsByPlaylist(Long id, Pageable page) {
        return songRepository.findAllByPlaylistsId(id, page);
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    public Songs postSong(SongDto song) {
        Songs newSong = new Songs();
        newSong.setTitle(song.getTitle());
        newSong.setArtist(artistRepository.getReferenceById(song.getArtistId()));
        return songRepository.save(newSong);
    }

    public Songs putSong(Long id, SongDto song)  {
        Songs updatedSong = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not song with id " + id));
        updatedSong.setTitle(song.getTitle());
        updatedSong.setArtist(artistRepository.getReferenceById(song.getArtistId()));

        return songRepository.save(updatedSong);
    }

    public Songs save(Songs song) {
        return  songRepository.save(song);
    }
}
