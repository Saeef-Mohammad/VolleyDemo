package com.saeefmd.official.volleydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_results);

        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonParse();
            }
        });
    }

    private void jsonParse() {

        String url = "https://api.myjson.com/bins/kp9wz";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray jsonArray = response.getJSONArray("employees");

                            for (int i=0; i<jsonArray.length(); i++) {

                                JSONObject emplyee = jsonArray.getJSONObject(i);

                                String firstName = emplyee.getString("firstname");
                                int age = emplyee.getInt("age");
                                String mail = emplyee.getString("mail");

                                mTextViewResult.append(firstName + ", " + age + ", " + mail + "\n\n");

                                //Log.i("Wrong: ", mTextViewResult.getText().toString());
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Wrong: ", error.getMessage());
            }
        });

        mQueue.add(request);
    }
}
