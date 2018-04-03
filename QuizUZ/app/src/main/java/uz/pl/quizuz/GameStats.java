package uz.pl.quizuz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import uz.pl.quizuz.model.DatabaseAccessor;

/**
 * Game player's statistics class
 *
 * @author Mateusz Borowski
 */
public class GameStats extends AppCompatActivity {
    private TextView playedGamesTextView, wonGamesTextView,
            lostGamesTextView, correctAnswersTextView, incorrectAnswersTextView;
    private Button resetStatsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats);

        setView();
        updateStats();
    }

    /**
     * Sets all needed variables
     */
    private void setView() {
        playedGamesTextView = findViewById(R.id.playedGamesTextView);
        wonGamesTextView = findViewById(R.id.wonGamesTextView);
        lostGamesTextView = findViewById(R.id.lostGamesTextView);
        correctAnswersTextView = findViewById(R.id.correctAnswersTextView);
        incorrectAnswersTextView = findViewById(R.id.incorrectAnswersTextView);
        resetStatsButton = findViewById(R.id.resetStatsButton);
        resetStatsButton.setOnClickListener(view -> {
            DatabaseAccessor databaseAccessor = DatabaseAccessor.getInstance(this);
            databaseAccessor.open();
            databaseAccessor.resetStats();
            databaseAccessor.close();
            updateStats();
        });
    }

    /**
     * Sets all textViews to current player's stats
     */
    private void updateStats() {
        DatabaseAccessor databaseAccessor = DatabaseAccessor.getInstance(this);
        databaseAccessor.open();
        int playedGames = databaseAccessor.getGamesPlayed();
        int wonGames = databaseAccessor.getGamesWon();
        int lostGames = databaseAccessor.getGamesLost();
        int correctAnswers = databaseAccessor.getCorrectAnswers();
        int incorrectAnswers = databaseAccessor.getIncorrectAnswers();
        databaseAccessor.close();

        playedGamesTextView.setText(String.valueOf(playedGames));
        wonGamesTextView.setText(String.valueOf(wonGames));
        lostGamesTextView.setText(String.valueOf(lostGames));
        correctAnswersTextView.setText(String.valueOf(correctAnswers));
        incorrectAnswersTextView.setText(String.valueOf(incorrectAnswers));
    }
}
