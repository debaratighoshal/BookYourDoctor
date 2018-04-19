package healthcare.dev.debarati.bookyourdoctor;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Debarati on 14-04-2018.
 */

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity_user);
        Button buttonRegister = (Button)findViewById(R.id.buu1);
        final EditText fname = (EditText) findViewById(R.id.edd1);
        final EditText lname = (EditText) findViewById(R.id.edd2);
        final EditText pwd = (EditText) findViewById(R.id.edd6);
        final EditText address = (EditText) findViewById(R.id.edd3);
        final EditText phoneno = (EditText) findViewById(R.id.edd4);
        final EditText email = (EditText) findViewById(R.id.edd5);
        final RequestQueue queue = Volley.newRequestQueue(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String fname_txt = fname.getText().toString().trim();
                String lname_txt = lname.getText().toString().trim();
                String pwd_txt = pwd.getText().toString().trim();
                String address_txt = address.getText().toString().trim();
                String phoneno_txt = phoneno.getText().toString().trim();
                String email_txt = email.getText().toString().trim();
                String username_txt = email.getText().toString().trim();
                if(email_txt.isEmpty() || pwd_txt.isEmpty() || fname_txt.isEmpty() || lname_txt.isEmpty()
                        || address_txt.isEmpty() || phoneno_txt.isEmpty()) {
                    String message = "Empty fields not allowed";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (!Patterns.EMAIL_ADDRESS.matcher((CharSequence) email_txt).matches()) {
                        String message = "Enter valid email..";
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        if(pwd_txt.length()<6)
                        {
                            String message = "Password must be atleast 6 characters..";
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            try{
                                Resources resources = getApplicationContext().getResources();
                                InputStream rawResource = resources.openRawResource(R.raw.config);
                                Properties properties = new Properties();
                                properties.load(rawResource);


                                Toast.makeText(getApplicationContext(), properties.getProperty("api_host_url") + properties.getProperty("new_user_url"), Toast.LENGTH_LONG).show();
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, properties.getProperty("api_host_url") + properties.getProperty("new_user_url"),
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // Display the first 500 characters of the response string.

                                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                                                Log.i("Registration response: ",response.toString());
                                                try {
                                                    JSONObject userRegistrationResponse = new JSONObject(response.toString());
                                                    Toast.makeText(getApplicationContext(), userRegistrationResponse.get("Message").toString(), Toast.LENGTH_LONG).show();
                                                    if(userRegistrationResponse.get("Sucess").equals("1"))
                                                    {
                                                        Intent homeIntent = new Intent(RegisterActivity.this,HomeActivity.class);
                                                        startActivity(homeIntent);
                                                    }
                                                }
                                                catch(Exception e)
                                                {
                                                    e.printStackTrace();
                                                }

                                                Intent homeIntent = new Intent(RegisterActivity.this,HomeActivity.class);
                                                startActivity(homeIntent);
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
                                        String fname_txt = fname.getText().toString().trim();
                                        String lname_txt = lname.getText().toString().trim();
                                        String pwd_txt = pwd.getText().toString().trim();
                                        String address_txt = address.getText().toString().trim();
                                        String phoneno_txt = phoneno.getText().toString().trim();
                                        String email_txt = email.getText().toString().trim();
                                        String username_txt = email.getText().toString().trim();
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("username", username_txt);
                                        params.put("pwd", pwd_txt);
                                        params.put("fname", fname_txt);
                                        params.put("lname", lname_txt);
                                        params.put("address", address_txt);
                                        params.put("phoneno", phoneno_txt);
                                        params.put("email", email_txt);
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


                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            //on success return to login page

                        }
                    }

                }

            } });

    }
}
