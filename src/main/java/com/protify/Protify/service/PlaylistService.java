package com.protify.Protify.service;

import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.repository.PlaylistRepository;
import com.protify.Protify.repository.SongRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    public Page<Playlist> getPlaylist(Pageable page){
        return playlistRepository.findAll(page);
    }

    public Optional<Playlist> getSinglePlaylist(long id){
        return playlistRepository.findById(id);
    }
    }
