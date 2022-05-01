package VisitPlann.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etReenterPassword, etcity,etcountry, etgender;
    private TextView tvStatus;
    private Button btnRegister;
    private String URL = "http://188.251.46.46:80/api/account/register";
    private String name, email, password, reenterPassword,city,country, gender;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        etName = findViewById(R.id.etName);
        etEmail= findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etReenterPassword= findViewById(R.id.etReenterPassword);
        etcity= findViewById(R.id.etCity);
        etcountry= findViewById(R.id.etCountry);
        etgender=findViewById(R.id.etGender);


        tvStatus = findViewById(R.id.tvStatus);
        btnRegister= findViewById(R.id.btnRegister);
        name= email= password= reenterPassword= "";



    }
    public void save (View view){
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        reenterPassword = etReenterPassword.getText().toString().trim();
        if(!password.equals(reenterPassword)){
            Toast.makeText(this,"Password Mismatch" ,Toast.LENGTH_SHORT).show();
        }
        else if (!email.equals("") && !name.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.equals("success")) {
                        tvStatus.setText("Successfully registered");
                        btnRegister.setClickable(false);
                    } else if (response.equals("failure")) {
                        tvStatus.setText("Something went wrong");
                        btnRegister.setClickable(false);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.toString().trim() ,Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String > data =new HashMap<>();
                    data.put("name", name);
                    data.put("email", email);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }


    }


    public void login(View view) {
        Intent intent= new Intent( this, Login.class);
        startActivity(intent);
        finish();
    }

}
