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
        // 1. Buscar curso e verificar existência
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new TaskException("Course not found."));

        // 2. Validar status do curso e dados do DTO
        openTextTaskValidator.validate(dto, course);

        // 3. Gerenciar ordem das tarefas
        if (dto.getOrder() == 0) {
            dto = new OpenTextTaskDTO(dto.getCourseId(), dto.getStatement(), taskOrderManager.getNextOrder(course.getId()));
        } else {
            taskOrderManager.reorderTasks(course.getId(), dto.getOrder());
        }

        // 4. Criar e persistir nova tarefa
        OpenTextTask task = dto.toEntity(course);
        openTextTaskRepository.save(task);

        // 5. Retornar resposta adequada
        return ResponseEntity.ok(task);
    }

    @PostMapping("/new/singlechoice")
    @Transactional
    public ResponseEntity<?> createSingleChoiceTask(@RequestBody SingleChoiceTaskDTO dto) {
        // Validar se o courseId foi fornecido
        if (dto.getCourseId() == null) {
            throw new TaskException("Course ID must not be null.");
        }

        // Buscar curso e verificar existência
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new TaskException("Course with ID " + dto.getCourseId() + " not found."));

        // Validar status do curso e dados do DTO
        singleChoiceTaskValidator.validate(dto, course);

        // Gerenciar ordem das tarefas
        SingleChoiceTaskDTO updatedDto = dto;
        if (dto.getOrder() == 0) {
            updatedDto = new SingleChoiceTaskDTO(dto.getCourseId(), dto.getStatement(), taskOrderManager.getNextOrder(course.getId()), dto.getOptions());
        } else {
            taskOrderManager.reorderTasks(course.getId(), dto.getOrder());
        }

        // Criar e persistir nova tarefa
        SingleChoiceTask task = updatedDto.toEntity(course);
        singleChoiceTaskRepository.save(task);

        // Criar e associar opções
        List<AnswerOption> options = updatedDto.getOptions().stream()
                .map(optionDTO -> optionDTO.toEntity(task))
                .toList();
        answerOptionRepository.saveAll(options);

        // Retornar resposta adequada
        return ResponseEntity.ok(task);
    }

    @PostMapping("/new/multiplechoice")
    @Transactional
    public ResponseEntity<?> createMultipleChoiceTask(@RequestBody MultipleChoiceTaskDTO dto) {
        // Validar se o courseId foi fornecido
        if (dto.getCourseId() == null) {
            throw new TaskException("Course ID must not be null.");
        }

        // Buscar curso e verificar existência
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new TaskException("Course with ID " + dto.getCourseId() + " not found."));

        // Validar status do curso e dados do DTO
        multipleChoiceTaskValidator.validate(dto, course);

        // Gerenciar ordem das tarefas
        MultipleChoiceTaskDTO updatedDto = dto;
        if (dto.getOrder() == 0) {
            updatedDto = new MultipleChoiceTaskDTO(dto.getCourseId(), dto.getStatement(), taskOrderManager.getNextOrder(course.getId()), dto.getOptions());
        } else {
            taskOrderManager.reorderTasks(course.getId(), dto.getOrder());
        }

        // Criar e persistir nova tarefa
        MultipleChoiceTask task = updatedDto.toEntity(course);
        multipleChoiceTaskRepository.save(task);

        // Criar e associar opções
        List<AnswerOption> options = updatedDto.getOptions().stream()
                .map(optionDTO -> optionDTO.toEntity(task))
                .toList();
        answerOptionRepository.saveAll(options);

        // Retornar resposta adequada
        return ResponseEntity.ok(task);
    }

}