package VisitPlann.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import VisitPlann.com.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Login extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private String email, password;
    private String URL = "http://188.251.46.46:80/api/account/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_final);
        email = password = "";
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

    }

    public void login(View view) {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonBody.toString();

        if (!email.equals("") && !password.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("On response",response);


                            if (response.equals("200")) {
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (response.equals("failure")) {
                                Toast.makeText(Login.this, "Invalid Login Id/Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("erro", error.toString());

                }
            }) {
                //    @Override
                //    public Map<String, String> getHeaders() throws AuthFailureError {
                //        Map<String, String > data =new HashMap<>();

                //  data.put("Accept", "application/json");
                // data.put("Content-Type", "application/json; charset=UTF-8");

                //        data.put("email", email);
                //        data.put("password", password);
                //        return data;
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.d("Data", response.data.toString());
                    Log.d("status", String.valueOf(response.statusCode));
                    Log.d("response", "[raw json]: " + (new String(response.data)));




                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }




            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }

    }


    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }


}