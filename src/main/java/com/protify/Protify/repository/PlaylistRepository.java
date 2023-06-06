package com.protify.Protify.repository;

import com.protify.Protify.models.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Page<Playlist> findAllByUserId(Long id, Pageable pageable);
}
