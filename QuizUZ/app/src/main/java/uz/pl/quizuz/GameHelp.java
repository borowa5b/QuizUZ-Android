package uz.pl.quizuz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Game help class
 * @author Mateusz Borowski
 */
public class GameHelp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_help);

        TextView tv = new TextView(this);
        tv.setText("Tu będzie pomoc");
        LinearLayout ll = findViewById(R.id.helpLayout);
        ll.addView(tv);
    }
}
