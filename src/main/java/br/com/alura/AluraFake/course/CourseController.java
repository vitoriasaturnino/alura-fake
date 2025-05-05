package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.user.*;
import br.com.alura.AluraFake.util.ErrorItemDTO;
import br.com.alura.AluraFake.task.TaskRepository;
import br.com.alura.AluraFake.task.TaskOrderManager;
import br.com.alura.AluraFake.task.Type;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CoursePublicationValidator coursePublicationValidator;

    @Autowired
    public CourseController(CourseRepository courseRepository, UserRepository userRepository, CoursePublicationValidator coursePublicationValidator) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.coursePublicationValidator = coursePublicationValidator;
    }

    @Transactional
    @PostMapping("/new")
    public ResponseEntity createCourse(@Valid @RequestBody NewCourseDTO newCourse) {

        //Caso implemente o bonus, pegue o instrutor logado
        Optional<User> possibleAuthor = userRepository
                .findByEmail(newCourse.getEmailInstructor())
                .filter(User::isInstructor);

        if(possibleAuthor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("emailInstructor", "Usuário não é um instrutor"));
        }

        Course course = new Course(newCourse.getTitle(), newCourse.getDescription(), possibleAuthor.get());

        courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseListItemDTO>> createCourse() {
        List<CourseListItemDTO> courses = courseRepository.findAll().stream()
                .map(CourseListItemDTO::new)
                .toList();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/{id}/publish")
    @Transactional
    public ResponseEntity<?> publishCourse(@PathVariable Long id) {
        try {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new CoursePublicationException("Course not found."));

            coursePublicationValidator.validate(course);

            course.setStatus(Status.PUBLISHED);
            course.setPublishedAt(LocalDateTime.now());

            courseRepository.save(course);

            return ResponseEntity.ok(course);
        } catch (CoursePublicationException e) {
            return ResponseEntity.badRequest().body(new ErrorItemDTO("course", e.getMessage()));
        }
    }

}
