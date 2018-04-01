package uz.pl.quizuz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    //Question related variables
    private int categoryID;
    private List<Question> questionsList;
    private Iterator<Question> questionIterator;
    private Question currentQuestion;
    private int correctAnswers;
    //GUI component
    private TextView questionTextView;
    private ProgressBar leftTimeBar;
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
        answerButtons = new Button[4];
        leftTimeBar = findViewById(R.id.leftTimeBar);
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
    }

    /**
     * Sets questionList variable using questions with categoryID getter from database,
     * iterator and shuffles questions
     */
    private void setQuestionsList() {
        DatabaseAccessor databaseAccessor = DatabaseAccessor.getInstance(this);
        databaseAccessor.open();
        if (categoryID != 0) {
            questionsList = databaseAccessor.getQuestions(categoryID);
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
        int questionsCount = 0;
        //current question assignation
        if (questionIterator.hasNext()) {
            currentQuestion = questionIterator.next();
        }
        List<String> answers = shuffleAnswers();

        //text assignation and button listeners
        questionTextView.setText(currentQuestion.getQuestion());
        for (int index = 0; index < answerButtons.length; index++) {
            String tempAnswer = answers.get(index);
            answerButtons[index].setText(tempAnswer);
            answerButtons[index].setOnClickListener(view -> checkIfCorrect(tempAnswer));
        }

        //start counting time
        countDown();
    }

    /**
     * Checks if answer is correct
     *
     * @param answer given by user
     */
    private void checkIfCorrect(String answer) {
        if(answer == null) { //If there's not questions in category
            countDownTimer.cancel();
            gameView();
        }
        if (answer.equals(currentQuestion.getCorrectAnswer())) {
            Toast.makeText(this, "Poprawna odpowiedź", Toast.LENGTH_SHORT).show();
            correctAnswers++;
        } else {
            Toast.makeText(this, "Niepoprawna odpowiedź", Toast.LENGTH_SHORT).show();
        }
        countDownTimer.cancel(); //stops previous time counting
        gameView();
    }

    /**
     * Starts counting time for user's answer
     */
    private void countDown() {
        countDownTimer = new CountDownTimer(20000,1000) {
            int i = 0;
            @Override
            public void onTick(long milliSecondUntilFinished) {
                i++;
                leftTimeBar.setProgress(i * 100 / (20000/1000));
            }
            @Override
            public void onFinish() {
                leftTimeBar.setProgress(100);
                checkIfCorrect("");
            }
        }.start();
    }

    /**
     * Stops counter when activity is no longer visible
     */
    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }
}