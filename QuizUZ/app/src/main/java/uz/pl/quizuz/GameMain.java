package uz.pl.quizuz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import uz.pl.quizuz.model.DatabaseAccessor;
import uz.pl.quizuz.model.Question;

/**
 * Game main screen class
 *
 * @author Mateusz Borowski
 */
public class GameMain extends AppCompatActivity {
    //For accessing db
    private DatabaseAccessor databaseAccessor;
    //Question related variables
    private int categoryID = -1;
    private ArrayList<Integer> selectedIDs;
    private List<Question> questionsList;
    private Iterator<Question> questionIterator;
    private Question currentQuestion;
    private int questionsCount;
    //GUI component
    private TextView questionTextView;
    private ProgressBar leftTimeBar, questionBar;
    private CountDownTimer countDownTimer;
    private Button[] answerButtons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        setView();
        getIntentVariables();
        setQuestionsList();
        gameView();
    }

    /**
     * Sets all needed gui components
     */
    private void setView() {
        databaseAccessor = DatabaseAccessor.getInstance(this);
        answerButtons = new Button[4];
        leftTimeBar = findViewById(R.id.leftTimeBar);
        questionBar = findViewById(R.id.questionsBar);
        questionTextView = findViewById(R.id.questionTextView);
        answerButtons[0] = findViewById(R.id.answer1Button);
        answerButtons[1] = findViewById(R.id.answer2Button);
        answerButtons[2] = findViewById(R.id.answer3Button);
        answerButtons[3] = findViewById(R.id.answer4Button);
    }

    /**
     * Gets categoryID from GameCategories activity
     */
    private void getIntentVariables() {
        Intent intent = getIntent();
        categoryID = intent.getIntExtra("categoryID", 0);
        selectedIDs = intent.getIntegerArrayListExtra("categoryIDs");
    }

    /**
     * Sets questionList variable using questions with category id getter from database,
     * iterator and shuffles questions
     */
    private void setQuestionsList() {
        databaseAccessor.open();
        if(categoryID != 0) {
            questionsList = databaseAccessor.getQuestions(categoryID);
        } else if (!selectedIDs.isEmpty()) {
            questionsList = new ArrayList<>();
            for(Integer id : selectedIDs) {
                questionsList.addAll(databaseAccessor.getQuestions(id));
            }
        } else {
            questionsList = databaseAccessor.getQuestions();
        }
        databaseAccessor.close();
        questionIterator = questionsList.iterator();
        Collections.shuffle(questionsList);
    }

    /**
     * Creates answers list from current question and shuffles them
     *
     * @return shuffled answers list
     */
    private List<String> shuffleAnswers() {
        List<String> answersList = new ArrayList<>();
        answersList.add(currentQuestion.getAnswer1());
        answersList.add(currentQuestion.getAnswer2());
        answersList.add(currentQuestion.getAnswer3());
        answersList.add(currentQuestion.getAnswer4());
        Collections.shuffle(answersList);
        return answersList;
    }

    /**
     * Main game code
     */
    private void gameView() {
        //Increases questions number in this game and sets progress bar
        questionsCount++;
        questionBar.setProgress(questionsCount);

        //current question assignation
        if (questionIterator.hasNext()) {
            currentQuestion = questionIterator.next();
        }
        List<String> answers = shuffleAnswers();

        //text assignation and button listeners
        questionTextView.setText(currentQuestion.getQuestion());
        for (int index = 0; index < answerButtons.length; index++) {
            Button tempButton = answerButtons[index];
            tempButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); //restores original background
            tempButton.setText(answers.get(index)); //sets answers text
            tempButton.setOnClickListener(view -> checkIfCorrect(tempButton)); //sets listener
        }

        //start counting time
        countDown();
    }

    /**
     * Checks if answer is correct
     * if correct button turns green
     * if incorrect button turns red
     *
     * @param answerButton given by user
     */
    private void checkIfCorrect(Button answerButton) {
        if (answerButton == null) { //If there's not questions in category
            countDownTimer.cancel();
            gameView();
        }

        //Disables buttons
        handleButtons();

        databaseAccessor.open();
        if (answerButton != null && answerButton.getText().toString().equals(currentQuestion.getCorrectAnswer())) {
            answerButton.setBackgroundColor(Color.GREEN);
            databaseAccessor.increaseCorrectAnswers();
        } else if(answerButton != null && !answerButton.getText().toString().equals(currentQuestion.getCorrectAnswer())) {
            answerButton.setBackgroundColor(Color.RED);
            databaseAccessor.increaseIncorrectAnswers();
        }
        databaseAccessor.close();
        countDownTimer.cancel(); //stops previous time counting

        //Waits second before next question or end game
        Handler handler = new Handler();
        handler.postDelayed(this::checkIfEnd, 1000);
    }

    /**
     * Handles buttons behavior when answer is selected
     */
    private void handleButtons() {
        for(Button button : answerButtons) {
            button.setClickable(false);
        }
    }

    /**
     * Checks if game should end
     */
    private void checkIfEnd() {
        if (questionsCount == 10) {
            databaseAccessor.open();
            databaseAccessor.increaseGamesPlayed();
            databaseAccessor.close();
            countDownTimer.cancel();
            NavUtils.navigateUpFromSameTask(this);
        } else {
            gameView();
        }
    }

    /**
     * Starts counting time for user's answer
     */
    private void countDown() {
        countDownTimer = new CountDownTimer(20000, 1000) {
            int i = 0;

            @Override
            public void onTick(long milliSecondUntilFinished) {
                i++;
                leftTimeBar.setProgress(i * 100 / (20000 / 1000));
            }

            @Override
            public void onFinish() {
                leftTimeBar.setProgress(100);
                checkIfCorrect(new Button(GameMain.this));
            }
        }.start();
    }
}