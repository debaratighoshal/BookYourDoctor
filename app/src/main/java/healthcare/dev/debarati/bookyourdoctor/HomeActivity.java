package healthcare.dev.debarati.bookyourdoctor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.res.Resources;
import android.util.Log;
import java.io.IOException;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button button = (Button)findViewById(R.id.bu1);
        Button buttonRegister = (Button)findViewById(R.id.bu2);
        final EditText email = (EditText) findViewById(R.id.et1);
        final EditText pwd = (EditText) findViewById(R.id.et2);
        final RequestQueue queue = Volley.newRequestQueue(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent registerIntent = new Intent(HomeActivity.this,RegisterActivity.class);
                startActivity(registerIntent);

            } });


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String emailTxt = email.getText().toString().trim();
                String paswd = pwd.getText().toString().trim();
               final Intent depIntent = new Intent(HomeActivity.this,DepartmentActivity.class);


                if(emailTxt.isEmpty() || paswd.isEmpty()) {
                    String message = "Empty fields not allowed";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                else {
                    if (!Patterns.EMAIL_ADDRESS.matcher((CharSequence) emailTxt).matches()) {
                        String message = "Enter valid email..";
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    } else try {
                        Resources resources = getApplicationContext().getResources();
                        InputStream rawResource = resources.openRawResource(R.raw.config);
                        Properties properties = new Properties();
                        properties.load(rawResource);


                        Toast.makeText(getApplicationContext(), properties.getProperty("api_host_url") + properties.getProperty("login_url"), Toast.LENGTH_LONG).show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, properties.getProperty("api_host_url") + properties.getProperty("login_url"),
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Display the first 500 characters of the response string.


                                        try {
                                            JSONObject userLoginResponse = new JSONObject(response.toString());

                                            if(userLoginResponse.get("Sucess").equals("1"))
                                            {
                                                Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_LONG).show();
                                                Bundle user_id_bundle = new Bundle();
                                                user_id_bundle.putInt("userid",userLoginResponse.getJSONObject("UserDetails").getInt("userid"));
                                                depIntent.putExtras(user_id_bundle);
                                                startActivity(depIntent);
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(), "Invalid Login credentials!", Toast.LENGTH_LONG).show();
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
                                String user = email.getText().toString().trim();
                                String passwd = pwd.getText().toString().trim();
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("username", user);
                                params.put("pwd", passwd);
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


// Add the request to the RequestQueue.
                        queue.add(stringRequest);


                    } catch (Exception e) {
                        e.getMessage();
                    }
                    }
                }

        });

    }
}
