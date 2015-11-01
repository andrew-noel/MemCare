package midtier.DAOs;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import midtier.DatabaseHelper;

import static common.TestCreationConstants.TEST_CREATE_TABLE_NAME;

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

}
