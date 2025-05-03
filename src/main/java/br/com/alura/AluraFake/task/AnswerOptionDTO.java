package br.com.alura.AluraFake.task;

import jakarta.validation.constraints.*;

public class AnswerOptionDTO {

    @NotBlank
    @Size(min = 4, max = 80)
    private String option;

    @NotNull
    private Boolean isCorrect;

    public AnswerOptionDTO() {}

    public AnswerOptionDTO(String option, Boolean isCorrect) {
        this.option = option;
        this.isCorrect = isCorrect;
    }

    public String getOption() {
        return option;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public AnswerOption toEntity(Task task) {
        AnswerOption answerOption = new AnswerOption();
        answerOption.setTask(task);
        answerOption.setOptionText(option);
        answerOption.setCorrect(isCorrect);
        return answerOption;
    }
}
