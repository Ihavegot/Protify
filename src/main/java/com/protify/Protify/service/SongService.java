package com.protify.Protify.service;

import com.protify.Protify.Request;
import com.protify.Protify.models.Songs;
import com.protify.Protify.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    public Page<Songs> getSongs(int page, int size){
        return songRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Songs> getSingleSong(long id){
        return songRepository.findById(id);
    }
}
