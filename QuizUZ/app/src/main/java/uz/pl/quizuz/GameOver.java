package uz.pl.quizuz;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uz.pl.quizuz.model.Category;
import uz.pl.quizuz.model.DatabaseAccessor;

/**
 * Game over screen class
 *
 * @author Mateusz Borowski
 */
public class GameOver extends AppCompatActivity {
    private ArrayList<Integer> selectedIDs;
    private int correctAnswers, incorrectAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        getIntentVariables();
        setView();
    }

    /**
     * Sets all needed gui components
     */
    private void setView() {
        DatabaseAccessor databaseAccessor = DatabaseAccessor.getInstance(this);
        databaseAccessor.open();
        List<Category> categories = databaseAccessor.getCategories();
        databaseAccessor.close();

        List<String> catNames = new ArrayList<>();
        for(int catID : selectedIDs) {
            for(Category cat : categories) {
                if(cat.getCategoryID() == catID) {
                    catNames.add(cat.getCategoryName());
                }
            }
        }
        TextView selectedCatTextView = findViewById(R.id.GO_selectedCatTextView);
        selectedCatTextView.setText(catNames.toString());

        TextView correctAnswerTextView = findViewById(R.id.GO_correctAnswersTextView);
        correctAnswerTextView.setText(String.valueOf(correctAnswers));

        TextView incorrectAnswerTextView = findViewById(R.id.GO_incorrectAnswersTextView);
        incorrectAnswerTextView.setText(String.valueOf(incorrectAnswers));

        Button startAgainButton = findViewById(R.id.startAgainButton);
        startAgainButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, GameMain.class);
            intent.putExtra("categoryIDs", selectedIDs);
            startActivity(intent);
        });


        Button returnToMainMenuButton = findViewById(R.id.returnToMainMenuButton);
        returnToMainMenuButton.setOnClickListener(view -> {
            NavUtils.navigateUpFromSameTask(this);
        });
    }

    /**
     * Gets categoryID from GameCategories activity
     */
    private void getIntentVariables() {
        Intent intent = getIntent();
        selectedIDs = intent.getIntegerArrayListExtra("categoryIDs");
        correctAnswers = intent.getIntExtra("correctAnswers", 0);
        incorrectAnswers = intent.getIntExtra("incorrectAnswers", 0);
    }
}
