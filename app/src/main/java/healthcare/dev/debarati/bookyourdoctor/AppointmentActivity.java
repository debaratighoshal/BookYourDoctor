package healthcare.dev.debarati.bookyourdoctor;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import healthcare.dev.debarati.bookyourdoctor.domain.Availableschedule;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import healthcare.dev.debarati.bookyourdoctor.domain.DocSchedule;
import healthcare.dev.debarati.bookyourdoctor.domain.GetDocDetails;

public class AppointmentActivity extends AppCompatActivity {
    List<Availableschedule> timeListFromAPI = new ArrayList<Availableschedule>();
    Integer timeSlotIdSelected = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        final ListView lv1=(ListView)findViewById(R.id.listviewtime);
       // Toast.makeText(getApplicationContext(), "DATE : "+getIntent().getExtras().getString("dateSelected"), Toast.LENGTH_LONG).show();
        final RequestQueue queue = Volley.newRequestQueue(this);
        Resources resources = getApplicationContext().getResources();
        InputStream rawResource = resources.openRawResource(R.raw.config);
        Properties properties = new Properties();
        try {
            properties.load(rawResource);
        }
        catch(Exception e)
        {
            Log.d("error",e.getMessage());
        }
        final StringRequest bookingRequest = new StringRequest(Request.Method.POST, properties.getProperty("api_host_url") + properties.getProperty("booking_dr_url"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.


                        try {
                            JSONObject bookingResponse = new JSONObject(response.toString());

                            if(bookingResponse.get("Sucess").equals("1"))
                            {
                                Toast.makeText(getApplicationContext(), "Booking success! :"+bookingResponse.get("Message"), Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Booking failed! : "+bookingResponse.get("Message"), Toast.LENGTH_LONG).show();
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
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                Integer userid = getIntent().getExtras().getInt("userid");


                params.put("doctorid", getIntent().getExtras().get("doctorid").toString() );
                params.put("userid",userid.toString());
                params.put("timeslotid", timeSlotIdSelected.toString());
                params.put("bookingdate", getIntent().getExtras().get("dateSelected").toString());
                Log.i("data", params.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                headers.clear();
                try {
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                }
                catch(Exception e)
                {

                }

                Log.i("headers", headers.toString());
                return headers;
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, properties.getProperty("api_host_url") + properties.getProperty("appointment_url")
                +"?scheduleid="+getIntent().getExtras().getInt("scheduleid")+"&date="+getIntent().getExtras().getString("dateSelected")+"&doctorid="+ getIntent().getExtras().getInt("doctorid"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {
                            JSONObject getDocResponse = new JSONObject(response.toString());


                            if(getDocResponse.get("Sucess").equals("1"))
                            {
                                GetDocDetails getDocDetails =new GetDocDetails() ;
                                Gson gson = new Gson();
                                getDocDetails= gson.fromJson(response.toString(),GetDocDetails.class);

                                for(int i=0;i<getDocDetails.getAvailableschedule().size();i++)
                                {
                                    timeListFromAPI.add(getDocDetails.getAvailableschedule().get(i));
                                }
                                lv1.setAdapter(new CustomTimeAdapter());
                                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                                        Availableschedule entry= (Availableschedule) parent.getAdapter().getItem(pos);
                                        Toast.makeText(getApplicationContext(), "Selected slot : "+entry.toString()+" User iD: "+getIntent().getExtras().getInt("userid"), Toast.LENGTH_LONG).show();
                                        timeSlotIdSelected = entry.getTimeslotid();
                                        queue.add(bookingRequest);
                                    }
                                });
                                //Toast.makeText(getApplicationContext(), "Appointment Call Success : "+getDocDetails.getAvailableschedule().toString(), Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "fail!", Toast.LENGTH_LONG).show();
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
        }) {



        };
        queue.add(stringRequest);
                            }
    public  class CustomTimeAdapter extends BaseAdapter {   //for listViewer//
        @Override
        public int getCount() {
            return timeListFromAPI.size();
        }
        @Override
        public Object getItem(int position) {
            return timeListFromAPI.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view=inflater.inflate(R.layout.row1,parent,false);
            TextView tv1=(TextView)view.findViewById(R.id.slotFrom);
            TextView tv2=(TextView)view.findViewById(R.id.slotTo);
            TextView tv3=(TextView)view.findViewById(R.id.status);

            tv1.setText(timeListFromAPI.get(position).getSlotfrom());
            tv2.setText(timeListFromAPI.get(position).getSlotto());
            tv3.setText(timeListFromAPI.get(position).getStatus());

            return view;
        }
    }
}
