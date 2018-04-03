package uz.pl.quizuz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

/**
 * Game authors class
 *
 * @author Mateusz Borowski
 */
public class GameAuthors extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_authors);

        TextView helpTextView = findViewById(R.id.authorsTextView);
        String text = getString(R.string.authorsText);
        CharSequence styledText = Html.fromHtml(text);
        //Sets textView with html markups
        helpTextView.setText(styledText);
    }
}
