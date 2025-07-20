package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.dto.MusicPlaylistResponse;
import com.niraj.fitforgeservice.fitforge.repository.MusicPlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicPlaylistService {

    private final MusicPlaylistRepository musicPlaylistRepository;

    public MusicPlaylistService(MusicPlaylistRepository musicPlaylistRepository) {
        this.musicPlaylistRepository = musicPlaylistRepository;
    }

    public List<MusicPlaylistResponse> getMusicPlaylist(Integer userId) {
        return musicPlaylistRepository.getAllByUserId(userId);
    }
}
