package repository;
import model.Question;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionSet {
    private List<Question> questions;
    private File file;

    /**
     * Constructor
     *
     * @throws Exception if file i/o fails
     */
    public QuestionSet() throws Exception {
        this.questions = new ArrayList<>();
        this.file = new File("files/questions.txt");
        this.readFromFile();
    }

    /**
     * Returns all questions
     *
     * @return List of all questions
     */
    public List<Question> getAllQuestions() {
        return this.questions;
    }

    /**
     * Returns a specific question
     *
     * @param id Question's ID
     * @return Question or null if it is not found
     */
    public Question getQuestion(int id) {
        for (Question question : this.questions) {
            if (question.getQuestionId() == id)
                return question;
        }
        return null;
    }

    /**
     * Loads all questions from a file
     *
     * @throws Exception if the file path is invalid
     */
    private void readFromFile() throws Exception {
        Scanner scanner = new Scanner(this.file);

        while (scanner.hasNextLine()) {
            String[] questionData = scanner.nextLine().split(";");

            List<Integer> correctAnswers = new ArrayList<>();
            if (questionData[7].compareTo("-") != 0) {
                String[] answerData = questionData[7].split(",");
                for (String answer : answerData) {
                    correctAnswers.add(Integer.parseInt(answer));
                }
            }

            Question question = new Question(Integer.parseInt(questionData[0]), questionData[1], questionData[2], questionData[3], questionData[4], questionData[5], questionData[6], correctAnswers);

            this.questions.add(question);
        }
        scanner.close();
    }
}
