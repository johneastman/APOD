package com.example.apod;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class APODRequest {

    private static final String DEBUG_APOD_URL_TAG = "APOD URL";
    private static final String DEBUG_APOD_MEDIA_TYPE_TAG = "APOD Media Type";

    private Context context;
    private String apiKey;

    public APODRequest(Context context, String apiKey) {
        this.context = context;
        this.apiKey = apiKey;
    }

    public void get(String date, final ResponseCallback responseCallback) {
        String url = getUrl(date);
        Log.d(DEBUG_APOD_URL_TAG, url);

        final RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        final APOD apod = APOD.getInstance();

                        final String title = getValue(response, "title", null);
                        apod.setTitle(title);

                        final String copyright = getValue(response, "copyright", "Public Domain");
                        apod.setCopyright(copyright);

                        final String description = getValue(response, "explanation", null);
                        apod.setDescription(description);

                        final String mediaType = getValue(response, "media_type", null);
                        apod.setMediaType(mediaType);

                        final String rawResponse = response.toString();
                        apod.setRawResponse(rawResponse);

                        Log.d(DEBUG_APOD_MEDIA_TYPE_TAG, mediaType);

                        if (mediaType.equals("image")) {
                            String imageUrl = getValue(response, "url", null);
                            ImageRequest imageRequest = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap response) {
                                    apod.setImage(response);
                                    responseCallback.onComplete(apod, null);
                                }
                            }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    String errorMessage = getVolleyErrorBody(error);
                                    responseCallback.onComplete(null, errorMessage);
                                }
                            });
                            queue.add(imageRequest);
                        }
                        responseCallback.onComplete(apod, null);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = getVolleyErrorBody(error);
                responseCallback.onComplete(null, errorMessage);
            }
        });
        queue.add(jsonObjectRequest);
    }

    private String getVolleyErrorBody(VolleyError error) {
        try {
            if (error.networkResponse != null) {
                String responseBody = new String(error.networkResponse.data, "UTF-8");
                JSONObject jsonObject = new JSONObject(responseBody);
                return jsonObject.getString("msg"); // NASA APOD-endpoint specific
            }
            return "";
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }

    /**
     * Get the value of a key from a JSON response.
     *
     * @param response JSON response
     * @param name Name of key in response
     * @param alternate This is returned if 'name' is not found in the response.
     * @return value if 'name' is found in response; 'alternate' otherwise.
     */
    private String getValue(JSONObject response, String name, String alternate) {
        try {
            return response.getString(name);
        } catch (JSONException e) {
            return alternate;
        }
    }

    private String getUrl(String date) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.nasa.gov")
                .appendPath("planetary")
                .appendPath("apod")
                .appendQueryParameter("api_key", this.apiKey);

        if (date != null) {
            builder.appendQueryParameter("date", date);
        }
        return builder.build().toString();
    }
}
