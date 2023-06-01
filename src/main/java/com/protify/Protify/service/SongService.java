package com.protify.Protify.service;

import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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

    public Page<Songs> getSongsByPlaylist(Long id, Pageable page) {return songRepository.findAllByPlaylistsId(id, page);}

    public Songs addSingleSong(Songs song){
        return songRepository.save(song);
    }

    public Optional<Songs> updateSingleSong(Songs song){
        return songRepository.findById(song.getId()).map(b -> {
            if (song.getTitle() != null) {
                b.setTitle(song.getTitle());
            }
            if(song.getSongFile() != null){
                b.setSongFile(song.getSongFile());
            }
            if(song.getArtist() != null){
                b.setArtist(song.getArtist());
            }
            return songRepository.save(b);
        });
    }

    public void deleteSingleSong(long id){
        songRepository.deleteById(id);
    }

}
