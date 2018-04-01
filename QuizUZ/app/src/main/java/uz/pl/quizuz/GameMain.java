package uz.pl.quizuz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
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
    private Button answer1Button, answer2Button, answer3Button, answer4Button;

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
        questionTextView = findViewById(R.id.questionTextView);
        answer1Button = findViewById(R.id.answer1Button);
        answer2Button = findViewById(R.id.answer2Button);
        answer3Button = findViewById(R.id.answer3Button);
        answer4Button = findViewById(R.id.answer4Button);
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
        questionsList = databaseAccessor.getQuestions(categoryID);
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

        //text assignation
        questionTextView.setText(currentQuestion.getQuestion());
        answer1Button.setText(answers.get(0));
        answer2Button.setText(answers.get(1));
        answer3Button.setText(answers.get(2));
        answer4Button.setText(answers.get(3));

        //button listeners
        answer1Button.setOnClickListener(view -> checkIfCorrect(answers.get(0)));
        answer2Button.setOnClickListener(view -> checkIfCorrect(answers.get(1)));
        answer3Button.setOnClickListener(view -> checkIfCorrect(answers.get(2)));
        answer4Button.setOnClickListener(view -> checkIfCorrect(answers.get(3)));
    }

    /**
     * Checks if answer is correct
     * @param answer given by user
     */
    private void checkIfCorrect(String answer) {
        if (answer.equals(currentQuestion.getCorrectAnswer())) {
            Toast.makeText(this, "Poprawna odpowiedź", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Niepoprawna odpowiedź", Toast.LENGTH_SHORT).show();
        }
        gameView();
    }
}