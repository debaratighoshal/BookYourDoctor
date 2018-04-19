package healthcare.dev.debarati.bookyourdoctor;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView;
import android.view.View;

import java.util.Arrays;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import android.widget.DatePicker.OnDateChangedListener;
import java.util.Calendar;


public class DepartmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        final Spinner spinner = (Spinner) findViewById(R.id.dep_spinner);
        final RequestQueue queue = Volley.newRequestQueue(this);
        spinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout

        Resources resources = getApplicationContext().getResources();
        InputStream rawResource = resources.openRawResource(R.raw.config);
        Properties properties = new Properties();
        try {
            properties.load(rawResource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, properties.getProperty("api_host_url") + properties.getProperty("department_url"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.


                        try {
                            JSONObject deparments = new JSONObject(response.toString());

                            if(deparments.get("Sucess").equals("1"))
                            {
                                Toast.makeText(getApplicationContext(), "Loaded Departments", Toast.LENGTH_LONG).show();
                                List<String> depList = new ArrayList<String>();
                                depList.add("Select your Department");
                                for(int i=0;i<deparments.getJSONArray("DepartmentDetails").length();i++)
                                {
                                    depList.add(deparments.getJSONArray("DepartmentDetails").getJSONObject(i).get("departmentname").toString());
                                }
                                String[] depArray = new String[depList.size()];
                                depList.toArray(depArray );

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),   android.R.layout.simple_spinner_item,depArray);
                               // ArrayAdapter<String> adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,depList.toArray());
// Specify the layout to use when the list of choices appears
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                                spinner.setAdapter(adapter);

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "API failed!", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.i("Error", new String(error.networkResponse.data, "UTF-8"));
                    Toast.makeText(getApplicationContext(), new String(error.networkResponse.data, "UTF-8"), Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        queue.add(stringRequest);

    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if(!(parent.getItemAtPosition(pos).toString().equals("Select your Department"))) {
            Toast.makeText(getApplicationContext(), new String(parent.getItemAtPosition(pos).toString()), Toast.LENGTH_LONG).show();
            final DatePicker bookingDate= (DatePicker) findViewById(R.id.datePicker1);
            bookingDate.setVisibility(View.VISIBLE);
            final Calendar c = Calendar.getInstance();
            bookingDate.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {

                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Toast.makeText(getApplicationContext(), new String("Date Changed : " +view.toString()), Toast.LENGTH_LONG).show();


                }
            });

        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


}

