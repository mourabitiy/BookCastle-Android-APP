package com.android.bookcastle.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIs {
    String BASE_URL = "https://www.googleapis.com/books/v1/";

    @GET("volumes?q=popular&maxResults=5")
    Call<String> getPopularBooks();
}
