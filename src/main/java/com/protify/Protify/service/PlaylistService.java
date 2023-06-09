package com.protify.Protify.service;

import com.protify.Protify.Exceptions.ResourceNotFoundException;
import com.protify.Protify.dtos.PlaylistDto;
import com.protify.Protify.dtos.PlaylistSongsDto;
import com.protify.Protify.dtos.PlaylistTitleDto;
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

    public Optional<Playlist> updateSinglePlaylist(Long id, PlaylistTitleDto string) {
        return playlistRepository.findById(id).map(b -> {
                b.setTitle(string.getTitle());
            return playlistRepository.save(b);
        });
    }


    public void deleteSinglePlaylist(long id) {
        playlistRepository.deleteById(id);
    }

    public Page<Playlist> findAllByUserId(Long id, Pageable pageable) {
        return playlistRepository.findAllByUserId(id, pageable);
    }

    public Optional<Playlist> deleteSongFromPlaylist(PlaylistSongsDto playlistDto) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistDto.getPlaylistId());
        if(playlist.isPresent()) {
            Optional<Songs> song = songRepository.findById(playlistDto.getSongId());
            if(song.isPresent()) {
                playlist.get().getSongs().remove(song.get());
                return Optional.of(playlistRepository.save(playlist.get()));
            }
        }
        return Optional.empty();
    }

    public Optional<Playlist> addSongToPlaylist(PlaylistSongsDto playlistDto) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistDto.getPlaylistId());
        if(playlist.isPresent()) {
            Optional<Songs> song = songRepository.findById(playlistDto.getSongId());
            if(song.isPresent()) {
                playlist.get().getSongs().add(song.get());
                return Optional.of(playlistRepository.save(playlist.get()));
            }
        }
        return Optional.empty();
    }
}
