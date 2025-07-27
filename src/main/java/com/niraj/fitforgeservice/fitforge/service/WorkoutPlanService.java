package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.dto.*;
import com.niraj.fitforgeservice.fitforge.entity.*;
import com.niraj.fitforgeservice.fitforge.exception.InvalidInputException;
import com.niraj.fitforgeservice.fitforge.repository.*;
import com.niraj.fitforgeservice.fitforge.utils.constants.ResponseMessages;
import com.niraj.fitforgeservice.fitforge.utils.enums.ExerciseTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.niraj.fitforgeservice.fitforge.utils.helpers.DateHelpers.getFormattedDateTime;

@Service
public class WorkoutPlanService {
    private final WorkoutDayRepository workoutDayRepository;
    private final ExerciseCategoryRepository exerciseCategoryRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final ExerciseLogRepository exerciseLogRepository;
    private final UserRepository userRepository;


    public WorkoutPlanService(WorkoutDayRepository workoutDayRepository, ExerciseCategoryRepository exerciseCategoryRepository, ExerciseRepository exerciseRepository, WorkoutPlanRepository workoutPlanRepository, ExerciseLogRepository exerciseLogRepository, UserRepository userRepository) {
        this.workoutDayRepository = workoutDayRepository;
        this.exerciseCategoryRepository = exerciseCategoryRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutPlanRepository = workoutPlanRepository;
        this.exerciseLogRepository = exerciseLogRepository;
        this.userRepository = userRepository;
    }

    public List<WorkoutDayPlan> getWorkoutPlans(Integer planId) {
        Map<Integer, WorkoutDay> workoutDays = workoutDayRepository.findAllByWorkoutPlanId(planId).stream().collect(Collectors.toMap(WorkoutDay::getId, w -> w));
        List<ExerciseCategory> exerciseCategories = exerciseCategoryRepository.getAllExerciseCategoryByWorkoutDays(workoutDays.keySet());
        Map<Integer, List<ExerciseCategory>> exerciseCategoriesMapByDay= exerciseCategoryRepository.getAllExerciseCategoryByWorkoutDays(workoutDays.keySet()).stream().collect(Collectors.groupingBy(e -> e.getWorkoutDay().getId()));
        Map<Integer, List<Exercise>> exercisesByCategories = exerciseRepository.getAllExercisesByExerciseCategories(exerciseCategories.stream().map(ExerciseCategory::getId).toList()).stream().collect(Collectors.groupingBy(e -> e.getExerciseCategory().getId()));
        List<WorkoutDayPlan> workoutDayPlans = new ArrayList<>();
        for(Map.Entry<Integer, WorkoutDay> workoutDayEntry: workoutDays.entrySet()) {
            WorkoutDay day = workoutDayEntry.getValue();
            WorkoutDayPlan workoutDayPlan = new WorkoutDayPlan();
            workoutDayPlan.setDay(day.getDayOfCycle());
            workoutDayPlan.setTitle(day.getTitle());
            workoutDayPlan.setFocus(day.getFocus());
            workoutDayPlan.setWarmup(day.getWarmup());
            List<ExerciseCategory> exerciseCategory = exerciseCategoriesMapByDay.get(day.getDayOfCycle());
            List<ExerciseCategoryData> exerciseCategoriesData = new ArrayList<>();
            List<ExerciseData> absWorkout = new ArrayList<>();
            List<ExerciseData> neckWorkout = new ArrayList<>();
            List<ExerciseData> calisthenicsWorkout = new ArrayList<>();
            List<ExerciseData> functionalCoreWorkout = new ArrayList<>();
            for(ExerciseCategory category: exerciseCategory) {
                List<ExerciseData> exercises = exercisesByCategories.get(category.getId()).stream().map(e -> new ExerciseData(e.getName(), e.getDetails())).toList();
                switch (ExerciseTypeEnum.BY_KEY.get(category.getType())) {
                    case ExerciseTypeEnum.STRENGTH:
                        exerciseCategoriesData.add(new ExerciseCategoryData(category.getName(), exercises));
                        break;
                    case ExerciseTypeEnum.ABS:
                        absWorkout.addAll(exercises);
                        break;
                    case ExerciseTypeEnum.CALISTHENICS:
                        calisthenicsWorkout.addAll(exercises);
                        break;
                    case ExerciseTypeEnum.NECK:
                        neckWorkout.addAll(exercises);
                        break;
                    case ExerciseTypeEnum.FUNCTIONALCORE:
                        functionalCoreWorkout.addAll(exercises);
                        break;
                }
            }
            workoutDayPlan.setStrength(exerciseCategoriesData);
            workoutDayPlan.setAbs(absWorkout);
            workoutDayPlan.setNeck(neckWorkout);
            workoutDayPlan.setCalisthenics(calisthenicsWorkout);
            workoutDayPlan.setFunctionalCore(functionalCoreWorkout);
            workoutDayPlan.setCardio(day.getCardio());
            workoutDayPlan.setActiveRecovery(day.getActiveRecovery());
            workoutDayPlans.add(workoutDayPlan);
        }

        return workoutDayPlans;
    }

    public UserWorkoutPlan getUserWorkoutPlan(Integer userId) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findByUserId(userId).orElseThrow(() -> new InvalidInputException(""));
        UserWorkoutPlan userWorkoutPlan = new UserWorkoutPlan();
        userWorkoutPlan.setIsPaused(workoutPlan.isActive());
        userWorkoutPlan.setStartDate(getFormattedDateTime(workoutPlan.getStartDate()));
        userWorkoutPlan.setPauseDate(getFormattedDateTime(workoutPlan.getPauseDate()));
        List<WorkoutDayPlan> workoutDayPlans = getWorkoutPlans(workoutPlan.getId());
        userWorkoutPlan.setPlan(workoutDayPlans);
        userWorkoutPlan.setId(workoutPlan.getId());
        return userWorkoutPlan;
    }

    public String resumeWorkout(Integer userId) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findByUserId(userId).orElseThrow(() -> new InvalidInputException(""));
        workoutPlan.setPauseDate(new Date());
        workoutPlan.setActive(true);
        workoutPlanRepository.save(workoutPlan);
        return ResponseMessages.RESUMED_SUCCESSFULLY;
    }

    public String pauseWorkout(Integer userId) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findByUserId(userId).orElseThrow(() -> new InvalidInputException(""));
        workoutPlan.setPauseDate(new Date());
        workoutPlan.setActive(false);
        workoutPlanRepository.save(workoutPlan);
        return ResponseMessages.PAUSED_SUCCESSFULLY;
    }

    public WorkoutPlanResponse createWorkoutPlan(Integer userId, CreateWorkoutPlanRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidInputException("No User Found"));
        WorkoutPlan plan = new WorkoutPlan();
        plan.setActive(false);
        plan.setStartDate(null);
        plan.setUser(user);
        plan.setName(request.name());
        plan.setPauseDate(null);
        plan.setStartDate(request.startDate());
        WorkoutPlan newPlan = workoutPlanRepository.save(plan);
        return new WorkoutPlanResponse(newPlan.getId(), newPlan.getUser().getId(), newPlan.getName(), getFormattedDateTime(newPlan.getStartDate()), newPlan.isActive(), new ArrayList<>());
    }

    public WorkoutPlan findByUserIdAndPlanId(Integer userId, Integer planId) {
        return workoutPlanRepository.findByUserIdAndPlanId(userId, planId).orElseThrow(() -> new InvalidInputException("No Plan Found"));
    }

    public String archiveWorkoutPlan(Integer userId, Integer planId) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findByUserIdAndPlanId(userId, planId).orElseThrow(() -> new InvalidInputException("No Plan Found"));
        workoutPlanRepository.delete(workoutPlan);
        return ResponseMessages.ARCHIVED_SUCCESSFULLY;
    }

    @Transactional
    public void updatePlan(Integer planId, UpdateWorkoutPlanDto dto) {
        WorkoutPlan plan = workoutPlanRepository.findById(planId)
                .orElseThrow(() -> new InvalidInputException("WorkoutPlan not found with id: " + planId));

        if (dto.isActive() != null) {
            plan.setActive(dto.isActive());
        }
        if (dto.pauseDate() != null) {
            plan.setPauseDate(dto.pauseDate());
        }
        if (dto.startDate() != null) {
            plan.setStartDate(dto.startDate());
        }

        workoutPlanRepository.save(plan);
    }
}
