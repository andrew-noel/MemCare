package midtier.DAOs;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import common.CaregiverTableIndex;
import midtier.TO.CaregiverTO;
import midtier.TO.CaregiverTOImpl;
import midtier.DatabaseHelper;

import static common.CaregiverTableConstants.*;
import static common.PatientTableConstants.PATIENT_TABLE_NAME;

public class CaregiverDAO implements CaregiverDAOInt {


    public CaregiverTO retrieve_Caregiver(String username) {
        CaregiverTO caregiverDAO;
        String firstname;
        String lastname;
        String id;

        Cursor res = DatabaseHelper.query("select * from " + CAREGIVER_TABLE_NAME + " where USER_NAME = '" + username + "'");
        res.moveToNext();
        firstname = res.getString(CaregiverTableIndex.INDEX_FIRST_NAME.toValue());
        lastname = res.getString(CaregiverTableIndex.INDEX_LAST_NAME.toValue());
        id = res.getString(CaregiverTableIndex.INDEX_ID.toValue());

        caregiverDAO = new CaregiverTOImpl(firstname, lastname, username, id);
        return caregiverDAO;
    }


    public List<String> getListOfPatientNames(String username) {


        List<String> names = new ArrayList<String>();
        String wholeName;

        Cursor res = DatabaseHelper.query("select * from " + PATIENT_TABLE_NAME + " where CAREGIVER = '" + username + "'");
        if(res.moveToFirst()){
            do{
                wholeName = res.getString(1) + " " + res.getString(2);

                names.add(wholeName);
            }while(res.moveToNext());
        }


        res.close();

        return names;
    }

    public List<String> getTestForUser(String username){


        List<String> tests = new ArrayList<String>();
        String testName;

        Cursor res = DatabaseHelper.query("select * from TEST_TABLE where OWNER_USERNAME = '" + username + "'");
        if(res.moveToFirst()){
            do{
                testName = res.getString(3);

                tests.add(testName);
            }while(res.moveToNext());
        }


        res.close();

        return tests;

    }

}
