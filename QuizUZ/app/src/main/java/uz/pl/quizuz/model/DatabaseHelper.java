package uz.pl.quizuz.model;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Database config class (package visible)
 * @author Mateusz Borowski
 */
class DatabaseHelper extends SQLiteAssetHelper {
    //Database config variables
    private final static String DB_NAME = "QuizUZ_DB.sqlite";
    private final static int DB_VERSION = 1;

    //Constructor (package visible)
    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
}
