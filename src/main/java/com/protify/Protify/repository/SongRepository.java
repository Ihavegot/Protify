package com.protify.Protify.repository;

import com.protify.Protify.models.Songs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Songs, Long> {
  
    Page<Songs> findByArtistId(Long id, Pageable page);
    Page<Songs> findAllByPlaylistsId(Long id, Pageable page);
}
