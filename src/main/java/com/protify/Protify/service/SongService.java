package com.protify.Protify.service;

import com.protify.Protify.models.Songs;
import com.protify.Protify.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    public Page<Songs> getSongs(Pageable page){
        return songRepository.findAll(page);
    }

    public Optional<Songs> getSingleSong(long id){
        return songRepository.findById(id);
    }


    public Page<Songs> getSongsByArtist(Long id, Pageable page) {return  songRepository.findByArtistId(id, page);
    public Page<Songs> getSongsByPlaylist(Long id, Pageable page) {return songRepository.findAllByPlaylistsId(id, page);
    }
}
