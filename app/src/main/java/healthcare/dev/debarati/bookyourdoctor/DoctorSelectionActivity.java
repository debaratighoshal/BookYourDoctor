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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import healthcare.dev.debarati.bookyourdoctor.domain.GetDocDetails;
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

public class DoctorSelectionActivity extends AppCompatActivity {
    List<DocSchedule> docListFromAPI = new ArrayList<DocSchedule>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_selection);
        //final TextView docDetails = (TextView) findViewById(R.id.docdtls2);
        final ListView lv1=(ListView)findViewById(R.id.listview);

        final RequestQueue queue = Volley.newRequestQueue(this);

        if(getIntent().getExtras()!=null && !getIntent().getExtras().isEmpty() && getIntent().getExtras().containsKey("depIdSelected")) {
            if (getIntent().getExtras() != null && !getIntent().getExtras().isEmpty() && getIntent().getExtras().containsKey("dateSelected")) {

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

                StringRequest stringRequest = new StringRequest(Request.Method.GET, properties.getProperty("api_host_url") + properties.getProperty("get_doctor_url")
                        +"?departmentid="+getIntent().getExtras().getString("depIdSelected")+"&bookingdate="+getIntent().getExtras().getString("dateSelected"),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {



                                try {
                                    JSONObject getDocResponse = new JSONObject(response.toString());
                                    GetDocDetails getDocDetails =new GetDocDetails() ;
                                    Gson gson = new Gson();
                                    getDocDetails= gson.fromJson(response.toString(),GetDocDetails.class);


                                    if(getDocResponse.get("Sucess").equals("1"))
                                    {
                                        Toast.makeText(getApplicationContext(), "Api call success : "/*+getDocDetails.toString()*/, Toast.LENGTH_LONG).show();
                                        //docDetails.setText(getDocDetails.toString());
                                        for(int i=0;i<getDocDetails.getDoctorSchedule().size();i++)
                                        {
                                            docListFromAPI.add(getDocDetails.getDoctorSchedule().get(i));
                                        }
                                        lv1.setAdapter(new Customadapter());
                                        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                                                DocSchedule entry= (DocSchedule) parent.getAdapter().getItem(pos);
                                                Toast.makeText(getApplicationContext(), "Api call success : "+entry.toString(), Toast.LENGTH_LONG).show();
                                                Intent doctorSelectIntent = new Intent(DoctorSelectionActivity.this,AppointmentActivity.class);
                                                Bundle depBundle = new Bundle();
                                                depBundle.putInt("scheduleid",entry.getScheduleid());
                                                depBundle.putString("dateSelected",getIntent().getExtras().getString("dateSelected"));
                                                depBundle.putInt("doctorid",entry.getDoctorde().getDoctorid());
                                                depBundle.putInt("userid",getIntent().getExtras().getInt("userid"));
                                                doctorSelectIntent.putExtras(depBundle);
                                                startActivity(doctorSelectIntent);
                                            }
                                        });

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
        }

    }
    public  class Customadapter extends BaseAdapter {   //for listViewer//
        @Override
        public int getCount() {
            return docListFromAPI.size();
        }
        @Override
        public Object getItem(int position) {
            return docListFromAPI.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view=inflater.inflate(R.layout.row,parent,false);
            TextView tv1=(TextView)view.findViewById(R.id.tv1);
            TextView tv2=(TextView)view.findViewById(R.id.tv2);
            TextView tv3=(TextView)view.findViewById(R.id.tv3);
            TextView tv4=(TextView)view.findViewById(R.id.tv4);
            tv1.setText(docListFromAPI.get(position).getScheduleday());
            tv2.setText(docListFromAPI.get(position).getSchdet().getStarttime());
            tv3.setText(docListFromAPI.get(position).getSchdet().getEndtime());
            tv4.setText(docListFromAPI.get(position).getDoctorde().getDoctorname());
            return view;
        }
    }
}
