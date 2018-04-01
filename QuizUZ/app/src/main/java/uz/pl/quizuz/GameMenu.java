package uz.pl.quizuz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Game main menu class
 * @author Mateusz Borowski
 */
public class GameMenu extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        buttonsHandler();
    }

    /**
     * Handles button's actions
     */
    private void buttonsHandler() {
        Button categoriesButton = findViewById(R.id.categoriesButton);
        categoriesButton.setOnClickListener(view -> startActivity(new Intent(this, GameCategories.class)));

        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(view -> startActivity(new Intent(this, GameMain.class)));

        Button authorsButton = findViewById(R.id.authorsButton);
        authorsButton.setOnClickListener(view -> startActivity(new Intent(this, GameAuthors.class)));

        Button helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(view -> startActivity(new Intent(this, GameHelp.class)));
    }
}