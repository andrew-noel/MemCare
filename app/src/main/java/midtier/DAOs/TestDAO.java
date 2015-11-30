package midtier.DAOs;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import midtier.DatabaseHelper;

import static common.TestCreationConstants.TEST_CREATE_TABLE_NAME;
import static common.QuestionsConstants.QUESTIONS_TABLE_NAME;

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

}
