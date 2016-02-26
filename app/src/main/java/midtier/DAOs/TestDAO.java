package midtier.DAOs;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import midtier.DatabaseHelper;

import static common.TestCreationConstants.TEST_CREATE_TABLE_NAME;
import static common.QuestionsConstants.QUESTIONS_TABLE_NAME;
import static common.TestResultsConstants.*;

/**
 * Created by andrewmcmullen on 11/1/15.
 */
public class TestDAO {

    public List<String> getListOfAvailableTestNames(){
        List<String> testNames = new ArrayList<String>();

        Cursor res = DatabaseHelper.query("select * from " + TEST_CREATE_TABLE_NAME);
        if(res.moveToFirst()){
            do{
                testNames.add(res.getString(3));
            }while(res.moveToNext());
        }
        res.close();

        return testNames;
    }

    public HashMap<String, Integer> getFaceIndexHashMap(String testName){
        HashMap<String, Integer> faceIndices = new HashMap<>();
        Cursor res = DatabaseHelper.query("select * from " + QUESTIONS_TABLE_NAME + " where TEST_NAME = '" + testName + "'" );
        if (res.moveToFirst()){
            do{
                String imageURI = res.getString(2);
                String index = res.getString(4);
                faceIndices.put(imageURI, Integer.parseInt(index));
            }while(res.moveToNext());
        }
        res.close();

        return faceIndices;
    }

    public HashMap<String, String> getQuestionsHashTable(String testName){
        HashMap<String, String> questions = new HashMap<>();
        Cursor res = DatabaseHelper.query("select * from " + QUESTIONS_TABLE_NAME + " where TEST_NAME = '" + testName + "'" );
        if (res.moveToFirst()){
            do{
                String imageURI = res.getString(2);
                String name = res.getString(3);
                questions.put(imageURI, name);
            }while(res.moveToNext());
        }
        res.close();

        return questions;
    }

    public List<String> getPatientFromTestname(String testname){

        List<String> names = new ArrayList<String>();
        Log.d("Q:", "select * from " + TEST_CREATE_TABLE_NAME + " where TEST_NAME = '" + testname + "'");
        Cursor res = DatabaseHelper.query("select * from " + TEST_CREATE_TABLE_NAME + " where TEST_NAME = '" + testname + "'");
        res.moveToFirst();
        String patient_name = res.getString(2);
        Log.d("X", patient_name);
        StringTokenizer tokenizer = new StringTokenizer(patient_name, " ");
        String fname = tokenizer.nextToken();
        Log.d("FNAMEAAA:", fname);
        String lname = tokenizer.nextToken();
        names.add(fname);
        names.add(lname);
        return names;
    }

    public String getCurrentDate(){
        Calendar currentDate = Calendar.getInstance();
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        return " " + currentMonth + "/" + currentDay + "/" + currentYear;
    }


    public HashMap<Integer, Integer> getScores(String patientName){
        HashMap<Integer, Integer> scores = new HashMap<>();

        Log.d("MemCare", "select * from " + RESULTS_TABLE_NAME + " where PATIENT_FIRSTNAME = '" + patientName + "'");

        Cursor res = DatabaseHelper.query("select * from " + RESULTS_TABLE_NAME + " where " + RESULTS_COLUMN_1 + "= '" + patientName + "'" );
        res.moveToFirst();
        do{
            int id = Integer.parseInt(res.getString(0));
            int score = Integer.parseInt(res.getString(6));
            scores.put(id, score);

        }while(res.moveToNext());

        res.close();

        return scores;
    }

}
