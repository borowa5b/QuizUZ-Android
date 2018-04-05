package uz.pl.quizuz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;

import java.util.ArrayList;
import java.util.List;

import uz.pl.quizuz.model.Category;
import uz.pl.quizuz.model.DatabaseAccessor;

/**
 * Game categories class
 *
 * @author Mateusz Borowski
 */
public class GameCategories extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_categories);

        populateScreen();
    }

    /**
     * Populates layout with categories from database
     */
    private void populateScreen() {
        DatabaseAccessor databaseAccessor = DatabaseAccessor.getInstance(this);
        databaseAccessor.open();
        List<Category> categoriesList = databaseAccessor.getCategories();
        databaseAccessor.close();

        //Gets linear layout to place all buttons in it
        LinearLayout linearLayout = findViewById(R.id.categoriesLinearLayout);

        //Adds space between buttons
        Space space = new Space(this);
        space.setMinimumHeight(2);
        linearLayout.addView(space);

        //All categories button
        Button allCatButton = new Button(this);
        allCatButton.setText(getResources().getString(R.string.allCatButtonText));
        allCatButton.setHeight(200);
        allCatButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, GameMain.class);
            intent.putExtra("categoryID", 0);
            startActivity(intent);
        });
        linearLayout.addView(allCatButton);

        space = new Space(this);
        space.setMinimumHeight(2);
        linearLayout.addView(space);

        //Categories buttons
        Button[] catButtons = new Button[categoriesList.size()];
        ArrayList<Integer> selectedIDs = new ArrayList<>();
        for (int i = 0; i < catButtons.length; i++) {
            catButtons[i] = new Button(this);
            catButtons[i].setText(categoriesList.get(i).getCategoryName());
            catButtons[i].setHeight(200);

            int key = categoriesList.get(i).getCategoryID();
            Button tmp = catButtons[i];
            catButtons[i].setOnClickListener(view -> {
                if (!selectedIDs.contains(key)) {
                    selectedIDs.add(key);
                    tmp.setBackgroundColor(Color.rgb(60, 60, 60));
                } else {
                    selectedIDs.remove(Integer.valueOf(key));
                    tmp.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            });
            linearLayout.addView(catButtons[i]);

            space = new Space(this);
            space.setMinimumHeight(2);
            linearLayout.addView(space);
        }

        FloatingActionButton playSelectedCategoriesFAB = findViewById(R.id.playFAB);
        playSelectedCategoriesFAB.setOnClickListener(view -> {
            Intent intent = new Intent(this, GameMain.class);
            intent.putExtra("categoryIDs", selectedIDs); //Passes chosen categoryIDs to new opened activity
            startActivity(intent); //Starts GameMain Activity
        });
    }
}