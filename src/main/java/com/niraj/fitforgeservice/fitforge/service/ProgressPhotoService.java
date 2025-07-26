package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.entity.ProgressPhoto;
import com.niraj.fitforgeservice.fitforge.repository.ProgressPhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProgressPhotoService {
    private final ProgressPhotoRepository progressPhotoRepository;

    public ProgressPhotoService(ProgressPhotoRepository progressPhotoRepository) {
        this.progressPhotoRepository = progressPhotoRepository;
    }

    @Transactional(readOnly = true)
    public List<ProgressPhoto> getPhotosForUser(Integer userId) {
        return progressPhotoRepository.findAllByUserIdOrderByTakenOnDesc(userId);
    }
}
