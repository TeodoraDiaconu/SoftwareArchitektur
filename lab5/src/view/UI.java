package view;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UI {
    private Stage window;
    private Controller controller;
    private int currentTime = 0;
    private int availableTime = 60;

    /**
     * Constructor
     *
     * @param window Main window
     */
    public UI(Stage window) {
        try {
            this.controller = new Controller();
            this.window = window;
        } catch (Exception ex) {
            System.out.println("An error has occurred.");
        }
    }

    /**
     * Starts the test
     */
    public void startTest() {
        this.confirmationWindow("Führerscheintest", "Test beginnen?");
    }

    /**
     * Runs the test
     */
    private void test() {
        int windowHeight = 720;
        int windowWidth = 1280;

        List<Integer> testSet = this.controller.generateTestSet();
        AtomicInteger currentQuestion = new AtomicInteger();
        AtomicInteger wrongAnswers = new AtomicInteger();

        this.currentTime = 0;
        Countdown countdown = new Countdown();
        Thread thread = new Thread(countdown);
        thread.start();

        while (currentQuestion.get() < testSet.size()) {

            if (wrongAnswers.get() > 4) {
                this.confirmationWindow("Durchgefallen", "Test durchgefallen! Du hast "
                        + (currentQuestion.get() - wrongAnswers.get())
                        + " Fragen richtig beantwortet. Nochmal versuchen?");
                break;
            }

            if (this.currentTime > availableTime) {
                break;
            }

            Stage questionWindow = new Stage();
            questionWindow.setTitle("Frage " + (currentQuestion.get() + 1));

            Label testStatus = new Label("Beantwortete Fragen: " + currentQuestion.get()
                    + "    Richtige Antworten: " + (currentQuestion.get() - wrongAnswers.get())
                    + "    Falsche Antworten: " + wrongAnswers.get()
                    + "    Zeit: " + availableTime+" Sekunden");

            ImageView image = new ImageView(new Image(this.controller.getImagePath(testSet.get(currentQuestion.get()))));
            image.setFitHeight((double) windowWidth / 4);
            image.setPreserveRatio(true);
            Label questionInfo = new Label("Frage " + (currentQuestion.get() + 1) + ": "
                    + this.controller.getQuestionText(testSet.get(currentQuestion.get())));
            Button answerConfirmationButton = new Button("Antwort bestätigen");

            CheckBox box1 = new CheckBox(this.controller.getAnswerOne(testSet.get(currentQuestion.get())));
            CheckBox box2 = new CheckBox(this.controller.getAnswerTwo(testSet.get(currentQuestion.get())));
            CheckBox box3 = new CheckBox(this.controller.getAnswerThree(testSet.get(currentQuestion.get())));
            CheckBox box4 = new CheckBox(this.controller.getAnswerFour(testSet.get(currentQuestion.get())));

            List<Integer> labelDimensions = new ArrayList<>(Arrays.asList(
                    questionInfo.getText().length(),
                    box1.getText().length(),
                    box2.getText().length(),
                    box3.getText().length(),
                    box4.getText().length()));

            double padding = ((windowWidth - (double) (Collections.max(labelDimensions) * 8)) / 2);

            VBox question = new VBox();
            question.getChildren().addAll(questionInfo, box1, box2, box3, box4);
            question.setSpacing(20);
            question.setAlignment(Pos.CENTER_LEFT);
            question.setPadding(new Insets(0, 0, 0, padding));

            VBox questionLayout = new VBox();
            questionLayout.getChildren().addAll(testStatus, image, question, answerConfirmationButton);
            questionLayout.setSpacing(20);
            questionLayout.setAlignment(Pos.CENTER);

            questionLayout.setStyle(("-fx-background-color: #FFFFFF;"));

            Scene questionScene = new Scene(questionLayout, windowWidth, windowHeight);

            answerConfirmationButton.setOnAction(e -> {
                List<Integer> answer = this.Answer(box1, box2, box3, box4);
                if (!this.controller.CheckAnswer(testSet.get(currentQuestion.get()), answer) && this.currentTime <= availableTime)
                    wrongAnswers.getAndIncrement();
                if (this.currentTime <= availableTime)
                    currentQuestion.getAndIncrement();
                questionWindow.close();
            });

            questionWindow.setScene(questionScene);
            questionWindow.showAndWait();
        }

        countdown.stop();

        if (currentQuestion.get() - wrongAnswers.get() >= 22) {
            this.confirmationWindow("Bestanden", "Test bestanden! Du hast "
                    + (currentQuestion.get() - wrongAnswers.get())
                    + " Fragen richtig beantwortet. Nochmal versuchen?");
        } else {
            this.confirmationWindow("Durchgefallen", "Zeit aus! Du hast "
                    + (currentQuestion.get() - wrongAnswers.get())
                    + " Fragen richtig beantwortet. Nochmal versuchen?");
        }

    }

    /**
     * Creates a confirmation window which gives the user a choice
     *
     * @param title   Window's title
     * @param message Message to be displayed
     */
    private void confirmationWindow(String title, String message) {
        window.setTitle(title);

        Label introMessage = new Label(message);
        Button confirmButton = new Button("Ja");
        Button denyButton = new Button("Nein");

        HBox startButtons = new HBox();
        startButtons.setSpacing(20);
        startButtons.setAlignment(Pos.CENTER);
        startButtons.getChildren().addAll(confirmButton, denyButton);

        VBox confirmationLayout = new VBox();
        confirmationLayout.setSpacing(20);
        confirmationLayout.setAlignment(Pos.CENTER);
        confirmationLayout.getChildren().addAll(introMessage, startButtons);

        Scene startScene = new Scene(confirmationLayout, 600, 200);

        confirmButton.setOnAction(e -> {
            window.close();
            test();
        });
        denyButton.setOnAction(e -> closeProgram());

        window.setScene(startScene);
        window.show();
    }

    /**
     * Gets the user's answer
     *
     * @param box1 First check box
     * @param box2 Second check box
     * @param box3 Third check box
     * @param box4 Fourth check box
     * @return answer as integers
     */
    private List<Integer> Answer(CheckBox box1, CheckBox box2, CheckBox box3, CheckBox box4) {
        List<Integer> answer = new ArrayList<>();

        if (box1.isSelected())
            answer.add(1);
        if (box2.isSelected())
            answer.add(2);
        if (box3.isSelected())
            answer.add(3);
        if (box4.isSelected())
            answer.add(4);

        return answer;
    }

    /**
     * Closes the program
     */
    private void closeProgram() {
        System.out.println("Program closed.");
        window.close();
    }

    /**
     * Countdown timer
     */
    private class Countdown implements Runnable {

        private boolean exit = false;

        /**
         * Starts the countdown
         */
        @Override
        public void run() {
            try {
                while (!exit) {
                    Thread.sleep(1000);
                    currentTime++;
                    if (availableTime - currentTime >= 0)
                        System.out.println("Time left: " + (availableTime - currentTime) + " seconds");
                    if (availableTime - currentTime == -1)
                        System.out.println("Time out.");
                }
                Thread.currentThread().interrupt();
            } catch (InterruptedException ignored) {
            }
        }

        /**
         * Stops the timer
         */
        private void stop() {
            this.exit = true;
        }
    }
}
