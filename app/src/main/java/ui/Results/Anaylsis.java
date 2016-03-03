package ui.Results;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


import lehigh.cse.memcare.R;
import midtier.DAOs.TestDAO;

public class Anaylsis extends AppCompatActivity {

    TestDAO testDaO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anaylsis);
        testDaO = new TestDAO();

        Bundle b = getIntent().getExtras();
        String patientName = b.getString("patientName");
        HashMap<Integer, Integer> patientData = new HashMap<>();
        patientData = testDaO.getScores(patientName);
        Set<Integer> keys = patientData.keySet();
        ArrayList<Integer> list = new ArrayList<>();
        list.addAll(keys);
        Collections.sort(list);
        List<DataPoint> dataPointList = new ArrayList<>();
        for(int x: list){
            dataPointList.add(new DataPoint(x, patientData.get(x)));
        }


        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] dataPoints = new DataPoint[dataPointList.size()];
        int counter = 0;
        for(DataPoint y: dataPointList){
            dataPoints[counter++] = y;
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);

        /*
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {

                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        */

        graph.addSeries(series);
        graph.setTitle("Data for " + patientName);
        graph.setTitleTextSize(80);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Percent Correct");

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);

        //graph.getViewport().setMaxY(100);
        graph.getGridLabelRenderer().setPadding(35);
        //graph.getGridLabelRenderer().setLabelVerticalWidth(100);
        graph.setLayoutParams(new RelativeLayout.LayoutParams(1600,800));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_anaylsis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
