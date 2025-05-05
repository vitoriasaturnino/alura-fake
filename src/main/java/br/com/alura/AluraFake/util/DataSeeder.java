package br.com.alura.AluraFake.util;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.task.TaskRepository;
import br.com.alura.AluraFake.task.answer_options.AnswerOption;
import br.com.alura.AluraFake.task.answer_options.AnswerOptionRepository;
import br.com.alura.AluraFake.task.multiple_choice.MultipleChoiceTask;
import br.com.alura.AluraFake.task.multiple_choice.MultipleChoiceTaskRepository;
import br.com.alura.AluraFake.task.open_text.OpenTextTask;
import br.com.alura.AluraFake.task.open_text.OpenTextTaskRepository;
import br.com.alura.AluraFake.task.single_choice.SingleChoiceTask;
import br.com.alura.AluraFake.task.single_choice.SingleChoiceTaskRepository;
import br.com.alura.AluraFake.user.Role;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile({"dev", "test"})
public class DataSeeder {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final OpenTextTaskRepository openTextTaskRepository;
    private final SingleChoiceTaskRepository singleChoiceTaskRepository;
    private final MultipleChoiceTaskRepository multipleChoiceTaskRepository;
    private final AnswerOptionRepository answerOptionRepository;

    public DataSeeder(UserRepository userRepository,
                      CourseRepository courseRepository,
                      OpenTextTaskRepository openTextTaskRepository,
                      SingleChoiceTaskRepository singleChoiceTaskRepository,
                      MultipleChoiceTaskRepository multipleChoiceTaskRepository,
                      AnswerOptionRepository answerOptionRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.openTextTaskRepository = openTextTaskRepository;
        this.singleChoiceTaskRepository = singleChoiceTaskRepository;
        this.multipleChoiceTaskRepository = multipleChoiceTaskRepository;
        this.answerOptionRepository = answerOptionRepository;
    }

    @PostConstruct
    public void seed() {
        seedUsers();
        seedCourses();
        seedTasks();
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
            User student = new User("Maria", "maria@alura.com.br", Role.STUDENT);
            userRepository.saveAll(List.of(instructor, student));
        }
    }

    private void seedCourses() {
        if (courseRepository.count() == 0) {
            User instructor = userRepository.findByEmail("paulo@alura.com.br").orElseThrow();
            Course javaCourse = new Course("Java Básico", "Aprenda os fundamentos de Java", instructor);
            Course springCourse = new Course("Spring Framework", "Desenvolvimento com Spring", instructor);
            Course designPatternsCourse = new Course("Design Patterns", "Padrões de projeto em Java", instructor);
            designPatternsCourse.setStatus(Status.PUBLISHED);
            courseRepository.saveAll(List.of(javaCourse, springCourse, designPatternsCourse));
        }
    }

    private void seedTasks() {
        Course javaCourse = courseRepository.findByTitle("Java Básico").orElseThrow();
        Course springCourse = courseRepository.findByTitle("Spring Framework").orElseThrow();
        Course designPatternsCourse = courseRepository.findByTitle("Design Patterns").orElseThrow();

        seedOpenTextTasks(javaCourse);
        seedSingleChoiceTasks(javaCourse);
        seedMultipleChoiceTasks(javaCourse);

        seedPublishableCourse(springCourse);
        seedEdgeCases(designPatternsCourse);
    }

    private void seedOpenTextTasks(Course course) {
        if (openTextTaskRepository.findByCourseIdAndStatement(course.getId(), "O que é Java?").isEmpty()) {
            OpenTextTask task1 = new OpenTextTask();
            task1.setCourse(course);
            task1.setStatement("O que é Java?");
            task1.setOrder(1);
            openTextTaskRepository.save(task1);
        }

        if (openTextTaskRepository.findByCourseIdAndStatement(course.getId(), "Explique o conceito de POO.").isEmpty()) {
            OpenTextTask task2 = new OpenTextTask();
            task2.setCourse(course);
            task2.setStatement("Explique o conceito de POO.");
            task2.setOrder(2);
            openTextTaskRepository.save(task2);
        }
    }

    private void seedSingleChoiceTasks(Course course) {
        if (singleChoiceTaskRepository.findByCourseIdAndStatement(course.getId(), "Qual é a saída do código abaixo?").isEmpty()) {
            SingleChoiceTask task = new SingleChoiceTask();
            task.setCourse(course);
            task.setStatement("Qual é a saída do código abaixo?");
            task.setOrder(3);

            AnswerOption option1 = new AnswerOption();
            option1.setOptionText("Valor 10");
            option1.setCorrect(false);

            AnswerOption option2 = new AnswerOption();
            option2.setOptionText("Valor 20");
            option2.setCorrect(true);

            AnswerOption option3 = new AnswerOption();
            option3.setOptionText("Valor 30");
            option3.setCorrect(false);

            singleChoiceTaskRepository.save(task);
            option1.setTask(task);
            option2.setTask(task);
            option3.setTask(task);
            answerOptionRepository.saveAll(List.of(option1, option2, option3));
        }
    }

    private void seedMultipleChoiceTasks(Course course) {
        if (multipleChoiceTaskRepository.findByCourseIdAndStatement(course.getId(), "Quais são os pilares da POO?").isEmpty()) {
            MultipleChoiceTask task = new MultipleChoiceTask();
            task.setCourse(course);
            task.setStatement("Quais são os pilares da POO?");
            task.setOrder(4);

            AnswerOption option1 = new AnswerOption();
            option1.setOptionText("Encapsulamento");
            option1.setCorrect(true);

            AnswerOption option2 = new AnswerOption();
            option2.setOptionText("Herança");
            option2.setCorrect(true);

            AnswerOption option3 = new AnswerOption();
            option3.setOptionText("Polimorfismo");
            option3.setCorrect(true);

            AnswerOption option4 = new AnswerOption();
            option4.setOptionText("Compilação");
            option4.setCorrect(false);

            multipleChoiceTaskRepository.save(task);
            option1.setTask(task);
            option2.setTask(task);
            option3.setTask(task);
            option4.setTask(task);
            answerOptionRepository.saveAll(List.of(option1, option2, option3, option4));
        }
    }

    private void seedPublishableCourse(Course course) {
        seedOpenTextTasks(course);
        seedSingleChoiceTasks(course);
        seedMultipleChoiceTasks(course);
    }

    private void seedEdgeCases(Course course) {
        OpenTextTask openTextTask = new OpenTextTask();
        openTextTask.setCourse(course);
        openTextTask.setStatement("Explique o padrão Singleton.");
        openTextTask.setOrder(1);

        SingleChoiceTask singleChoiceTask = new SingleChoiceTask();
        singleChoiceTask.setCourse(course);
        singleChoiceTask.setStatement("Qual é o objetivo do padrão Factory?");
        singleChoiceTask.setOrder(2);

        AnswerOption option1 = new AnswerOption();
        option1.setOptionText("Criar objetos");
        option1.setCorrect(true);

        AnswerOption option2 = new AnswerOption();
        option2.setOptionText("Gerenciar memória");
        option2.setCorrect(false);

        MultipleChoiceTask multipleChoiceTask = new MultipleChoiceTask();
        multipleChoiceTask.setCourse(course);
        multipleChoiceTask.setStatement("Quais padrões são criacionais?");
        multipleChoiceTask.setOrder(3);

        AnswerOption option3 = new AnswerOption();
        option3.setOptionText("Singleton");
        option3.setCorrect(true);

        AnswerOption option4 = new AnswerOption();
        option4.setOptionText("Factory");
        option4.setCorrect(true);

        AnswerOption option5 = new AnswerOption();
        option5.setOptionText("Observer");
        option5.setCorrect(false);

        openTextTaskRepository.save(openTextTask);
        singleChoiceTaskRepository.save(singleChoiceTask);
        option1.setTask(singleChoiceTask);
        option2.setTask(singleChoiceTask);
        answerOptionRepository.saveAll(List.of(option1, option2));
        multipleChoiceTaskRepository.save(multipleChoiceTask);
        option3.setTask(multipleChoiceTask);
        option4.setTask(multipleChoiceTask);
        option5.setTask(multipleChoiceTask);
        answerOptionRepository.saveAll(List.of(option3, option4, option5));
    }
}
