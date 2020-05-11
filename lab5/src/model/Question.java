package model;
import java.util.List;

public class Question {
    private int questionId;
    private String imagePath;
    private String questionText;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    private String answerFour;
    private List<Integer> correctAnswer;

    /**
     * Constructor
     *
     * @param questionId     question ID
     * @param imagePath      image path
     * @param questionText   question text
     * @param answerOne      answer one
     * @param answerTwo      answer two
     * @param answerThree    answer three
     * @param correctAnswers list of correct answers
     */
    public Question(int questionId, String imagePath, String questionText, String answerOne,
                    String answerTwo, String answerThree, String answerFour, List<Integer> correctAnswers) {
        this.questionId = questionId;
        this.imagePath = imagePath;
        this.questionText = questionText;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerFour = answerFour;
        this.correctAnswer = correctAnswers;
    }

    /**
     * Returns the question's ID
     *
     * @return Question ID
     */
    public int getQuestionId() {
        return questionId;
    }

    /**
     * Returns the question's image path
     *
     * @return Image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Returns the question's text
     *
     * @return Question text
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Returns the question's first answer
     *
     * @return First answer
     */
    public String getAnswerOne() {
        return answerOne;
    }

    /**
     * Returns the question's second answer
     *
     * @return Second answer
     */
    public String getAnswerTwo() {
        return answerTwo;
    }

    /**
     * Returns the question's third answer
     *
     * @return Third answer
     */
    public String getAnswerThree() {
        return answerThree;
    }

    /**
     * Returns the question's fourth answer
     *
     * @return Fourth answer
     */
    public String getAnswerFour() {
        return answerFour;
    }

    /**
     * Returns the question's list of correct answers
     *
     * @return Correct answers
     */
    public List<Integer> getCorrectAnswer() {
        return correctAnswer;
    }
}
