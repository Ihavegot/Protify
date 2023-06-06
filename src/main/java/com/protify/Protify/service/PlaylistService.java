package com.protify.Protify.service;

import com.protify.Protify.Exceptions.ResourceNotFoundException;
import com.protify.Protify.dtos.PlaylistDto;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.repository.PlaylistRepository;
import com.protify.Protify.repository.SongRepository;
import com.protify.Protify.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public Page<Playlist> getPlaylist(Pageable page) {
        return playlistRepository.findAll(page);
    }

    public Playlist getSinglePlaylist(long id){
        return playlistRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No playlist with id " + id));
    }

    public Playlist addSinglePlaylist(PlaylistDto playlistDto) {
        Playlist playlist = new Playlist();
        playlist.setUser(userRepository.getReferenceById(playlistDto.getUserId()));
        playlist.setTitle(playlistDto.getTitle());
        return playlistRepository.save(playlist);
    }

    public Optional<Playlist> updateSinglePlaylist(Playlist playlist) {
        return playlistRepository.findById(playlist.getId()).map(b -> {
            if (playlist.getUser() != null) {
                b.setUser(playlist.getUser());
            }
            if (playlist.getSongs() != null) {
                b.setSongs(playlist.getSongs());
            }
            if(playlist.getTitle() != null)
            {
                b.setTitle(playlist.getTitle());
            }
            return playlistRepository.save(b);
        });
    }

    public void deleteSinglePlaylist(long id) {
        playlistRepository.deleteById(id);
    }

    public Page<Playlist> findAllByUserId(Long id, Pageable pageable) {
        return playlistRepository.findAllByUserId(id, pageable);
    }

    public Optional<Playlist> deleteSongFromPlaylist(Long id, Long songId) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if(playlist.isPresent()) {
            Optional<Songs> song = songRepository.findById(songId);
            if(song.isPresent()) {
                playlist.get().getSongs().remove(song.get());
                return Optional.of(playlistRepository.save(playlist.get()));
            }
        }
        return Optional.empty();
    }
}
