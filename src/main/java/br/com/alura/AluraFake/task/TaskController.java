package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final CourseRepository courseRepository;
    private final OpenTextTaskRepository openTextTaskRepository;
    private final TaskValidator taskValidator;
    private final OpenTextTaskValidator openTextTaskValidator;
    private final TaskOrderManager taskOrderManager;

    public TaskController(CourseRepository courseRepository,
                          OpenTextTaskRepository openTextTaskRepository,
                          TaskValidator taskValidator,
                          OpenTextTaskValidator openTextTaskValidator,
                          TaskOrderManager taskOrderManager) {
        this.courseRepository = courseRepository;
        this.openTextTaskRepository = openTextTaskRepository;
        this.taskValidator = taskValidator;
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
    public ResponseEntity newSingleChoice() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/new/multiplechoice")
    public ResponseEntity newMultipleChoice() {
        return ResponseEntity.ok().build();
    }

}