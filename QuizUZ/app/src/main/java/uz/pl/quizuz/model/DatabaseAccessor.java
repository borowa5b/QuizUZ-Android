package uz.pl.quizuz.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Database accessing class
 *
 * @author Mateusz Borowski
 */
public class DatabaseAccessor {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static DatabaseAccessor instance;

    /**
     * Private constructor to avoid creating object from outside classes
     *
     * @param context activity context
     */
    private DatabaseAccessor(Context context) {
        this.sqLiteOpenHelper = new DatabaseHelper(context);
    }

    /**
     * Returns singleton instance of DatabaseAccessor class
     *
     * @param context activity context
     * @return the instance of DatabaseAccessor class
     */
    public static DatabaseAccessor getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccessor(context);
        }
        return instance;
    }

    /**
     * Opens connection with database
     */
    public void open() {
        this.sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    /**
     * Closes database connection
     */
    public void close() {
        if (sqLiteDatabase != null) {
            this.sqLiteDatabase.close();
        }
    }

    /**
     * Returns ArrayList of categories
     *
     * @return list of categories
     */
    public List<Category> getCategories() {
        List<Category> categoriesList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Categories", null);
        if (cursor.moveToFirst()) {
            do {
                Category tempCategory = new Category();
                tempCategory.setCategoryID(cursor.getInt(0));
                tempCategory.setCategoryName(cursor.getString(1));
                categoriesList.add(tempCategory);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categoriesList;
    }

    /**
     * Return ArrayList of all questions
     *
     * @return list of all questions
     */
    public List<Question> getQuestions() {
        List<Question> questionsList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Questions", null);
        if (cursor.moveToFirst()) {
            do {
                Question tempQuestion = new Question();
                tempQuestion.setQuestionID(cursor.getInt(0));
                tempQuestion.setCategoryID(cursor.getInt(1));
                tempQuestion.setQuestion(cursor.getString(2));
                tempQuestion.setAnswer1(cursor.getString(3));
                tempQuestion.setAnswer2(cursor.getString(4));
                tempQuestion.setAnswer3(cursor.getString(5));
                tempQuestion.setAnswer4(cursor.getString(6));
                tempQuestion.setCorrectAnswer(cursor.getString(7));
                questionsList.add(tempQuestion);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionsList;
    }

    /**
     * Return ArrayList of questions from specific category
     *
     * @param categoryID id of a category
     * @return list of questions from specific category
     */
    public List<Question> getQuestions(int categoryID) {
        List<Question> questionsList = new ArrayList<>();
        String[] params = new String[] {String.valueOf(categoryID)};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Questions WHERE categoryID = ?", params);
        if (cursor.moveToFirst()) {
            do {
                Question tempQuestion = new Question();
                tempQuestion.setQuestionID(cursor.getInt(0));
                tempQuestion.setCategoryID(cursor.getInt(1));
                tempQuestion.setQuestion(cursor.getString(2));
                tempQuestion.setAnswer1(cursor.getString(3));
                tempQuestion.setAnswer2(cursor.getString(4));
                tempQuestion.setAnswer3(cursor.getString(5));
                tempQuestion.setAnswer4(cursor.getString(6));
                tempQuestion.setCorrectAnswer(cursor.getString(7));
                questionsList.add(tempQuestion);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionsList;
    }
}
