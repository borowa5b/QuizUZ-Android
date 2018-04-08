package uz.pl.quizuz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import uz.pl.quizuz.model.DatabaseAccessor;

/**
 * Game main menu class
 *
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
        playButton.setOnClickListener(view -> {
            int categoryID = drawRandomCategory();
            ArrayList<Integer> categoryIDs = new ArrayList<>();
            categoryIDs.add(categoryID);
            Intent intent = new Intent(this, GameMain.class);
            intent.putExtra("categoryIDs", categoryIDs); //Passes chosen categoryID to new opened activity
            startActivity(intent); //Starts GameMain Activity
        });

        Button authorsButton = findViewById(R.id.authorsButton);
        authorsButton.setOnClickListener(view -> startActivity(new Intent(this, GameAuthors.class)));

        Button statsButton = findViewById(R.id.statsButton);
        statsButton.setOnClickListener(view -> startActivity(new Intent(this, GameStats.class)));

        FloatingActionButton fab = findViewById(R.id.FAB);
        fab.setOnClickListener(view -> startActivity(new Intent(this, GameHelp.class)));
    }

    /**
     * Draws random category number
     *
     * @return random category number
     */
    private int drawRandomCategory() {
        int randomCategoryNumber;
        DatabaseAccessor databaseAccessor = DatabaseAccessor.getInstance(this);
        databaseAccessor.open();
        int numbersOfCategories = databaseAccessor.getCategories().size();
        databaseAccessor.close();

        randomCategoryNumber = ThreadLocalRandom.current().nextInt(1, numbersOfCategories + 1);
        return randomCategoryNumber;
    }
}