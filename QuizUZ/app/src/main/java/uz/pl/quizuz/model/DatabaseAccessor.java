package uz.pl.quizuz.model;

import android.content.ContentValues;
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
        String[] params = new String[]{String.valueOf(categoryID)};
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
        } else {
            Question tempQuestion = new Question();
            tempQuestion.setQuestion("Brak pytań dla tej kategorii");
            tempQuestion.setCategoryID(categoryID);
            tempQuestion.setAnswer1("");
            tempQuestion.setAnswer2("");
            tempQuestion.setAnswer3("");
            tempQuestion.setAnswer4("");
            questionsList.add(tempQuestion);
        }
        cursor.close();
        return questionsList;
    }

    //Statistics queries

    /**
     * Increases number of played games
     */
    public void increaseGamesPlayed() {
        sqLiteDatabase.execSQL("UPDATE Statistics SET GamesPlayed = GamesPlayed + 1 WHERE PlayerID LIKE 1");
    }

    /**
     * Gets number of played games
     *
     * @return number of played games
     */
    public int getGamesPlayed() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT GamesPlayed FROM Statistics WHERE PlayerID LIKE 1", null);
        int gamesPlayed = 0;
        if (cursor.moveToFirst()) gamesPlayed = cursor.getInt(0);
        cursor.close();
        return gamesPlayed;
    }

    /**
     * Increases number of won games
     */
    public void increaseGamesWon() {
        sqLiteDatabase.execSQL("UPDATE Statistics SET GamesWon = GamesWon + 1 WHERE PlayerID LIKE 1");
    }

    /**
     * Gets number of won games
     *
     * @return number of won games
     */
    public int getGamesWon() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT GamesWon FROM Statistics WHERE PlayerID LIKE 1", null);
        int gamesWon = 0;
        if (cursor.moveToFirst()) gamesWon = cursor.getInt(0);
        cursor.close();
        return gamesWon;
    }

    /**
     * Increases number of lost games
     */
    public void increaseGamesLost() {
        sqLiteDatabase.execSQL("UPDATE Statistics SET GamesLost = GamesLost + 1 WHERE PlayerID LIKE 1");
    }

    /**
     * Gets number of lost games
     *
     * @return number of lost games
     */
    public int getGamesLost() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT GamesLost FROM Statistics WHERE PlayerID LIKE 1", null);
        int gamesLost = 0;
        if (cursor.moveToFirst()) gamesLost = cursor.getInt(0);
        cursor.close();
        return gamesLost;
    }

    /**
     * Increases number of correct answers
     */
    public void increaseCorrectAnswers() {
        sqLiteDatabase.execSQL("UPDATE Statistics SET CorrectAnswers = CorrectAnswers + 1 WHERE PlayerID LIKE 1");
    }

    /**
     * Gets number of correct answers
     *
     * @return number of correct answers
     */
    public int getCorrectAnswers() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT CorrectAnswers FROM Statistics WHERE PlayerID LIKE 1", null);
        int correctAnswers = 0;
        if (cursor.moveToFirst()) correctAnswers = cursor.getInt(0);
        cursor.close();
        return correctAnswers;
    }

    /**
     * Increases number of incorrect answers
     */
    public void increaseIncorrectAnswers() {
        sqLiteDatabase.execSQL("UPDATE Statistics SET IncorrectAnswers = IncorrectAnswers + 1 WHERE PlayerID LIKE 1");
    }

    /**
     * Gets number of incorrect answers
     *
     * @return number of incorrect answers
     */
    public int getIncorrectAnswers() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT IncorrectAnswers FROM Statistics WHERE PlayerID LIKE 1", null);
        int incorrectAnswers = 0;
        if (cursor.moveToFirst()) incorrectAnswers = cursor.getInt(0);
        cursor.close();
        return incorrectAnswers;
    }

    /**
     * Resets player's stats
     */
    public void resetStats() {
        sqLiteDatabase.execSQL("UPDATE Statistics SET GamesPlayed = 0, GamesWon = 0, GamesLost = 0");
    }
}
