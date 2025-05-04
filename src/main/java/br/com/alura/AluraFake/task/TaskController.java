package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final CourseRepository courseRepository;
    private final SingleChoiceTaskRepository singleChoiceTaskRepository;
    private final MultipleChoiceTaskRepository multipleChoiceTaskRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final OpenTextTaskRepository openTextTaskRepository;
    private final TaskValidator taskValidator;
    private final SingleChoiceTaskValidator singleChoiceTaskValidator;
    private final MultipleChoiceTaskValidator multipleChoiceTaskValidator;
    private final OpenTextTaskValidator openTextTaskValidator;
    private final TaskOrderManager taskOrderManager;

    public TaskController(CourseRepository courseRepository,
                          SingleChoiceTaskRepository singleChoiceTaskRepository,
                          MultipleChoiceTaskRepository multipleChoiceTaskRepository,
                          AnswerOptionRepository answerOptionRepository,
                          OpenTextTaskRepository openTextTaskRepository,
                          TaskValidator taskValidator,
                          SingleChoiceTaskValidator singleChoiceTaskValidator,
                          MultipleChoiceTaskValidator multipleChoiceTaskValidator,
                          OpenTextTaskValidator openTextTaskValidator,
                          TaskOrderManager taskOrderManager) {
        this.courseRepository = courseRepository;
        this.singleChoiceTaskRepository = singleChoiceTaskRepository;
        this.multipleChoiceTaskRepository = multipleChoiceTaskRepository;
        this.answerOptionRepository = answerOptionRepository;
        this.openTextTaskRepository = openTextTaskRepository;
        this.taskValidator = taskValidator;
        this.singleChoiceTaskValidator = singleChoiceTaskValidator;
        this.multipleChoiceTaskValidator = multipleChoiceTaskValidator;
        this.openTextTaskValidator = openTextTaskValidator;
        this.taskOrderManager = taskOrderManager;
    }

    @PostMapping("/new/opentext")
    @Transactional
    public ResponseEntity<?> createOpenTextTask(@RequestBody OpenTextTaskDTO dto) {
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new TaskException("Course not found."));

        openTextTaskValidator.validate(dto, course);

        taskOrderManager.validatePreviousOrderExists(course.getId(), dto.getOrder());

        if (dto.getOrder() == 0) {
            dto = new OpenTextTaskDTO(dto.getCourseId(), dto.getStatement(), taskOrderManager.getNextOrder(course.getId()));
        } else {
            taskOrderManager.reorderTasks(course.getId(), dto.getOrder());
        }

        OpenTextTask task = dto.toEntity(course);
        openTextTaskRepository.save(task);

        return ResponseEntity.ok(task);
    }

    @PostMapping("/new/singlechoice")
    @Transactional
    public ResponseEntity<?> createSingleChoiceTask(@RequestBody SingleChoiceTaskDTO dto) {
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new TaskException("Course with ID " + dto.getCourseId() + " not found."));

        singleChoiceTaskValidator.validate(dto, course);

        taskOrderManager.validatePreviousOrderExists(course.getId(), dto.getOrder());

        SingleChoiceTaskDTO updatedDto = dto;
        if (dto.getOrder() == 0) {
            updatedDto = new SingleChoiceTaskDTO(dto.getCourseId(), dto.getStatement(), taskOrderManager.getNextOrder(course.getId()), dto.getOptions());
        } else {
            taskOrderManager.reorderTasks(course.getId(), dto.getOrder());
        }

        SingleChoiceTask task = updatedDto.toEntity(course);
        singleChoiceTaskRepository.save(task);

        List<AnswerOption> options = updatedDto.getOptions().stream()
                .map(optionDTO -> optionDTO.toEntity(task))
                .toList();
        answerOptionRepository.saveAll(options);

        return ResponseEntity.ok(task);
    }

    @PostMapping("/new/multiplechoice")
    @Transactional
    public ResponseEntity<?> createMultipleChoiceTask(@RequestBody MultipleChoiceTaskDTO dto) {
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new TaskException("Course with ID " + dto.getCourseId() + " not found."));

        multipleChoiceTaskValidator.validate(dto, course);

        taskOrderManager.validatePreviousOrderExists(course.getId(), dto.getOrder());

        MultipleChoiceTaskDTO updatedDto = dto;
        if (dto.getOrder() == 0) {
            updatedDto = new MultipleChoiceTaskDTO(dto.getCourseId(), dto.getStatement(), taskOrderManager.getNextOrder(course.getId()), dto.getOptions());
        } else {
            taskOrderManager.reorderTasks(course.getId(), dto.getOrder());
        }

        MultipleChoiceTask task = updatedDto.toEntity(course);
        multipleChoiceTaskRepository.save(task);

        List<AnswerOption> options = updatedDto.getOptions().stream()
                .map(optionDTO -> optionDTO.toEntity(task))
                .toList();
        answerOptionRepository.saveAll(options);

        return ResponseEntity.ok(task);
    }
}