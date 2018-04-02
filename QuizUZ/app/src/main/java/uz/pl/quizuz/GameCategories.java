package uz.pl.quizuz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
<<<<<<< HEAD
=======
import android.widget.Space;
>>>>>>> feat

import java.util.List;

import uz.pl.quizuz.model.Category;
import uz.pl.quizuz.model.DatabaseAccessor;

/**
 * Game categories class
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

<<<<<<< HEAD
        //All categories button
        Button allCatButton = new Button(this);
        allCatButton.setText(getResources().getString(R.string.allCatButtonText));
=======
        //Adds space between buttons
        Space space = new Space(this);
        space.setMinimumHeight(2);
        linearLayout.addView(space);

        //All categories button
        Button allCatButton = new Button(this);
        allCatButton.setText(getResources().getString(R.string.allCatButtonText));
        allCatButton.setHeight(200);
>>>>>>> feat
        allCatButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, GameMain.class);
            intent.putExtra("categoryID", 0);
            startActivity(intent);
        });
        linearLayout.addView(allCatButton);

        //Categories buttons
        Button[] catButtons = new Button[categoriesList.size()];
<<<<<<< HEAD
        for (int i = 0; i < catButtons.length ; i++) {
            catButtons[i] = new Button(this);
            catButtons[i].setText(categoriesList.get(i).getCategoryName());
=======
        space = new Space(this);
        space.setMinimumHeight(2);
        linearLayout.addView(space);
        for (int i = 0; i < catButtons.length ; i++) {
            catButtons[i] = new Button(this);
            catButtons[i].setText(categoriesList.get(i).getCategoryName());
            catButtons[i].setHeight(200);
>>>>>>> feat

            int categoryID = categoriesList.get(i).getCategoryID();
            catButtons[i].setOnClickListener(view -> {
                Intent intent = new Intent(this, GameMain.class);
                intent.putExtra("categoryID", categoryID); //Passes chosen categoryID to new opened activity
                startActivity(intent); //Starts GameMain Activity
            });
            linearLayout.addView(catButtons[i]);
<<<<<<< HEAD
=======

            space = new Space(this);
            space.setMinimumHeight(2);
            linearLayout.addView(space);
>>>>>>> feat
        }
    }
}
