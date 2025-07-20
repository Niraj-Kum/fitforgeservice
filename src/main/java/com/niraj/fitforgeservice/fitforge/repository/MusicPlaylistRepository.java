
package com.niraj.fitforgeservice.fitforge.repository;
import com.niraj.fitforgeservice.fitforge.dto.MusicPlaylistResponse;
import com.niraj.fitforgeservice.fitforge.entity.MusicPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicPlaylistRepository extends JpaRepository<MusicPlaylist, Integer> {

    @Query("SELECT new com.niraj.fitforgeservice.fitforge.dto.MusicPlaylistResponse(m.title, m.artist, m.artwork, m.url) FROM MusicPlaylist m WHERE m.user.id = :userId")
    List<MusicPlaylistResponse> getAllByUserId(Integer userId);
}
