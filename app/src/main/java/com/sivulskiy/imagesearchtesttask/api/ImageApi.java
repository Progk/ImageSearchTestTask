package com.sivulskiy.imagesearchtesttask.api;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Query;


/**
 * @author Sivulskiy Sergey
 */
public class ImageApi {

    private static final String BASE_URL = "https://api.flickr.com/services/rest";


    private static final HotelService SERVICE = new RestAdapter.Builder()
            .setEndpoint(BASE_URL)
            .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Accept", "application/json");
                }
            })
            .setClient(new OkClient())
            .build()
            .create(HotelService.class);

    public static HotelService getService() {
        return SERVICE;
    }


    public interface HotelService {
        @GET("/?safe_search=safe&" +
                "extras=url_q,url_o,geo&" +
                "method=flickr.photos.search&" +
                "api_key=0d840b5ba481714a54a7af5b55e311a7&format&" +
                "format=json&" +
                "nojsoncallback=1&" +
                "per_page=250&" +
                "page=1")
        void getImageList(@Query("text") String search_str,
                          Callback<JsonObject> callback);

    }
}
