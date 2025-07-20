package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.MusicPlaylistResponse;
import com.niraj.fitforgeservice.fitforge.service.MusicPlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/music")
@Slf4j
public class MusicPlaylistController {
    private final MusicPlaylistService musicPlaylistService;

    public MusicPlaylistController(MusicPlaylistService musicPlaylistService) {
        this.musicPlaylistService = musicPlaylistService;
    }

    @GetMapping("/playlist/getAll/{userId}")
    public ResponseEntity<List<MusicPlaylistResponse>> getUserExerciseLogs(
            @PathVariable("userId") Integer userId) {

        log.info("GET /getAll/{}", userId);
        List<MusicPlaylistResponse> logs = musicPlaylistService.getMusicPlaylist(userId);
        return ResponseEntity.ok(logs);
    }
}
