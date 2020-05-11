package controller;
import repository.QuestionSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {
    private QuestionSet questionSet;

    /**
     * Constructor
     *
     * @throws Exception if the question set's file i/o operation fails
     */
    public Controller() throws Exception {
        this.questionSet = new QuestionSet();
    }

    /**
     * Generates a list of 26 unique question IDs
     *
     * @return list of question IDs
     */
    public List<Integer> generateTestSet() {
        List<Integer> questionIds = new ArrayList<>();
        Random random = new Random();
        while (questionIds.size() < 26) {
            int questionId = random.nextInt(questionSet.getAllQuestions().size() - 1) + 1;
            if (!questionIds.contains(questionId)) {
                questionIds.add(questionId);
            }
        }
        return questionIds;
    }

    /**
     * Returns a specific question's image path
     *
     * @param questionId Question ID
     * @return Question's image path
     */
    public String getImagePath(int questionId) {
        return (this.questionSet.getQuestion(questionId).getImagePath());
    }

    /**
     * Returns a specific question's text
     *
     * @param questionId Question ID
     * @return Question text
     */
    public String getQuestionText(int questionId) {
        return (this.questionSet.getQuestion(questionId).getQuestionText());
    }

    /**
     * Returns a specific question's first answer
     *
     * @param questionId Question ID
     * @return First answer
     */
    public String getAnswerOne(int questionId) {
        return (this.questionSet.getQuestion(questionId).getAnswerOne());
    }

    /**
     * Returns a specific question's second answer
     *
     * @param questionId Question ID
     * @return Second answer
     */
    public String getAnswerTwo(int questionId) {
        return (this.questionSet.getQuestion(questionId).getAnswerTwo());
    }

    /**
     * Returns a specific question's third answer
     *
     * @param questionId Question ID
     * @return Third answer
     */
    public String getAnswerThree(int questionId) {
        return (this.questionSet.getQuestion(questionId).getAnswerThree());
    }

    /**
     * Returns a specific question's fourth answer
     *
     * @param questionId Question ID
     * @return Fourth answer
     */
    public String getAnswerFour(int questionId) {
        return (this.questionSet.getQuestion(questionId).getAnswerFour());
    }

    /**
     * Checks if an answer is correct
     *
     * @param questionId ID of the answered question
     * @param answer     Chosen answer
     * @return true if the answer is correct, false otherwise
     */
    public boolean CheckAnswer(int questionId, List<Integer> answer) {
        List<Integer> correctAnswer = this.questionSet.getQuestion(questionId).getCorrectAnswer();
        if (answer.size() == correctAnswer.size()) {
            for (int answerId : answer) {
                if (!correctAnswer.contains(answerId))
                    return false;
            }
            return true;
        }
        return false;
    }
}
