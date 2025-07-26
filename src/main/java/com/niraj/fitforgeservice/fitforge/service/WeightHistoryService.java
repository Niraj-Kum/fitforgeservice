package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.dto.CreateWeightHistoryDto;
import com.niraj.fitforgeservice.fitforge.dto.WeightHistoryResponse;
import com.niraj.fitforgeservice.fitforge.entity.User;
import com.niraj.fitforgeservice.fitforge.entity.WeightHistory;
import com.niraj.fitforgeservice.fitforge.repository.WeightHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeightHistoryService {

    private final WeightHistoryRepository weightHistoryRepository;
    private final UserService userService;

    public WeightHistoryService(WeightHistoryRepository weightHistoryRepository, UserService userService) {
        this.weightHistoryRepository = weightHistoryRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public List<WeightHistoryResponse> getHistoryForUser(Integer userId) {
        return weightHistoryRepository.findAllByUserIdOrderByLoggedAtDesc(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public WeightHistoryResponse createEntry(CreateWeightHistoryDto dto) {
        User user = userService.findUserById(dto.getUserId());
        WeightHistory newEntry = new WeightHistory();
        newEntry.setUser(user);
        newEntry.setWeightLbs(dto.getWeightLbs());
        newEntry.setBodyFatPct(dto.getBodyFatPct());
        newEntry.setMuscleMassLbs(dto.getMuscleMassLbs());
        newEntry.setLoggedAt(dto.getLoggedAt());

        WeightHistory savedEntry = weightHistoryRepository.save(newEntry);
        return convertToDto(savedEntry);
    }

    private WeightHistoryResponse convertToDto(WeightHistory entity) {
        return new WeightHistoryResponse(
                entity.getId(),entity.getUser().getId(),
                entity.getLoggedAt(),
                entity.getWeightLbs(),
                entity.getBodyFatPct(),
                entity.getMuscleMassLbs()
        );
    }
}