package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.bookcastle.utils.NetworkChangeListener;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    ImageView mDogImageView;
    Button nextDogButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mDogImageView = findViewById(R.id.dogImageView);
//        nextDogButton = findViewById(R.id.nextDogButton);

       // nextDogButton.setOnClickListener(View -> loadDogImage());

        // image of a dog will be loaded once at the start of the app
        //loadDogImage();
    }

//    private void loadDogImage() {
//
//        // getting a new volley request queue for making new requests
//        RequestQueue volleyQueue = Volley.newRequestQueue(MainActivity.this);
//        // url of the api through which we get random dog images
//        String url = "https://dog.ceo/api/breeds/image/random";
//
//        // since the response we get from the api is in JSON, we
//        // need to use `JsonObjectRequest` for parsing the
//        // request response
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                // we are using GET HTTP request method
//                Request.Method.GET,
//                // url we want to send the HTTP request to
//                url,
//                // this parameter is used to send a JSON object to the
//                // server, since this is not required in our case,
//                // we are keeping it `null`
//                null,
//
//                // lambda function for handling the case
//                // when the HTTP request succeeds
//                (Response.Listener<JSONObject>) response -> {
//                    // get the image url from the JSON object
//                    String dogImageUrl;
//                    try {
//                        dogImageUrl = response.getString("message");
//                        // load the image into the ImageView using Glide.
//                        Glide.with(MainActivity.this).load(dogImageUrl).into(mDogImageView);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                },
//
//                // lambda function for handling the case
//                // when the HTTP request fails
//                (Response.ErrorListener) error -> {
//                    // make a Toast telling the user
//                    // that something went wrong
//                    Toast.makeText(MainActivity.this, "Some error occurred! Cannot fetch dog image", Toast.LENGTH_LONG).show();
//                    // log the error message in the error stream
//                    Log.e("MainActivity", "loadDogImage error: ${error.localizedMessage}");
//                }
//        );
//
//        // add the json request object created above
//        // to the Volley request queue
//        volleyQueue.add(jsonObjectRequest);
//    }


}