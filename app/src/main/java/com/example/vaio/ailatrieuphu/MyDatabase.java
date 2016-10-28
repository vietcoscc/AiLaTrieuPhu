package com.example.vaio.ailatrieuphu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by vaio on 10/29/2016.
 */

public class MyDatabase {
    public static final String TB_NAME[] = {"Question1", "Question2", "Question3", "Question4", "Question5",
            "Question6", "Question7", "Question8", "Question9", "Question10", "Question11", "Question13",
            "Question13", "Question14", "Question15",};

    public static final String QUESTION = "Question";
    public static final String CASE_A = "CaseA";
    public static final String CASE_B = "CaseB";
    public static final String CASE_C = "CaseC";
    public static final String CASE_D = "CaseD";
    public static final String TRUE_CASE = "TrueCase";
    public static final String DB_NAME = "Question.sqlite";
    public static final String PATH = Environment.getDataDirectory() + "/data/com.example.vaio.ailatrieuphu/databases/" + DB_NAME;

    private SQLiteDatabase database;
    private Context context;

    public MyDatabase(Context context) {
        this.context = context;
        copyDatabase(context);
    }

    public void copyDatabase(Context context) {
        try {
            File file = new File(PATH);
            if (file.exists()) {
                return;
            }
            File parent = file.getParentFile();
            parent.mkdirs();
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] b = new byte[1024];
            InputStream inputStream = context.getAssets().open(DB_NAME);
            int count = inputStream.read(b);
            while (count != -1) {
                outputStream.write(b, 0, count);
                count = inputStream.read(b);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDatabase() {
        database = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }

    public void closeDatabas() {
        database.close();
    }

    public Question getQuestion(int level) {
        openDatabase();


        Cursor cursor = database.query(TB_NAME[level], null, null, null, null, null, "random()", "1");
        int indexQuestion = cursor.getColumnIndex(QUESTION);
        int indexCaseA = cursor.getColumnIndex(CASE_A);
        int indexCaseB = cursor.getColumnIndex(CASE_B);
        int indexCaseC = cursor.getColumnIndex(CASE_C);
        int indexCaseD = cursor.getColumnIndex(CASE_D);
        int indexTrueCase = cursor.getColumnIndex(TRUE_CASE);

        cursor.moveToFirst();

        String question = cursor.getString(indexQuestion);
        String caseA = cursor.getString(indexCaseA);
        String caseB = cursor.getString(indexCaseB);
        String caseC = cursor.getString(indexCaseC);
        String caseD = cursor.getString(indexCaseD);
        int trueCase = cursor.getInt(indexTrueCase);
        Question question1 = new Question(question, caseA, caseB, caseC, caseD, trueCase);


        closeDatabas();
        return question1;
    }

}
