package com.app.compare_my_trade;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.compare_my_trade.utils.PreferenceUtils;
import com.google.android.gms.common.api.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyBackgroundService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            funNotification();
                            Log.e("Service", "back Service is running...");
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void funNotification() {
        final String URL = "http://motortraders.zydni.com/api/sellers/notification/list";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray unreadNotifications = response.getJSONArray("data");
                    int last = unreadNotifications.length() - 1;
                    try {

                        int notificationCount = PreferenceUtils.getLength(MyBackgroundService.this);
                        Log.i("fdfd", String.valueOf(notificationCount));
                        Log.i("fdfd", String.valueOf(unreadNotifications.length()));
                        if (notificationCount == 0 || unreadNotifications.length() == 0) {
                            if (unreadNotifications.length() != notificationCount) {
                                int j = 0;
                                for (int i = 0; i < unreadNotifications.length(); i++) {
                                    if (PreferenceUtils.getLength(MyBackgroundService.this) <= i) {
                                        JSONObject object = unreadNotifications.getJSONObject(j);
                                        j++;
//                                        Log.i("msd", object.toString());
                                        String title = object.getString("title");
                                        String notification_message = object.getString("notification_message");
                                        String car_id = object.getString("car_id");
                                        String id = object.getString("id");
                                        NotificationCompat.Builder builder =
                                                new NotificationCompat.Builder(MyBackgroundService.this, "My Notification");

                                        builder.setContentTitle(title);
                                        builder.setContentText(notification_message);
                                        builder.setSmallIcon(R.drawable.logo);
                                        builder.setAutoCancel(true);
//                                    builder.setDefaults(Notification.DEFAULT_SOUND);
                                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                        builder.setSound(alarmSound);
                                        builder.setWhen(System.currentTimeMillis());

                                        Intent intent = new Intent(MyBackgroundService.this, CarDetails.class);
                                        intent.putExtra("v_id", car_id);
                                        intent.putExtra("n_id",id);
                                        PendingIntent pendingIntent = PendingIntent.getActivity(MyBackgroundService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        builder.setContentIntent(pendingIntent);
                                        // Add as notification
                                        NotificationManager notificationManager = (NotificationManager) MyBackgroundService.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                        notificationManager.notify(j+1, builder.build());
                                    }
                                }
                                PreferenceUtils.saveLength(unreadNotifications.length(), MyBackgroundService.this);
                            }
                            PreferenceUtils.saveLength(unreadNotifications.length(), MyBackgroundService.this);
                        } else {
                            if (unreadNotifications.length() != notificationCount) {
                                int j = 0;
                                for (int i = 0; i < unreadNotifications.length(); i++) {
                                    if (PreferenceUtils.getLength(MyBackgroundService.this) <= i) {
                                        JSONObject object = unreadNotifications.getJSONObject(j);
                                        j++;

                                        String title = object.getString("title");
                                        String notification_message = object.getString("notification_message");
                                        String car_id = object.getString("car_id");
                                        String id = object.getString("id");

                                        NotificationCompat.Builder builder =
                                                new NotificationCompat.Builder(MyBackgroundService.this, "My Notification2");

                                        builder.setContentTitle(title);
                                        builder.setContentText(notification_message);
                                        builder.setSmallIcon(R.drawable.logo);
                                        builder.setAutoCancel(true);
//                                    builder.setDefaults(Notification.DEFAULT_SOUND);
                                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                        builder.setSound(alarmSound);
                                        builder.setWhen(System.currentTimeMillis());

                                        Intent intent = new Intent(MyBackgroundService.this, CarDetails.class);
                                        intent.putExtra("v_id", car_id);
                                        intent.putExtra("n_id",id);

                                        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(MyBackgroundService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        builder.setContentIntent(pendingIntent);

                                        // Add as notification
                                        NotificationManager notificationManager = (NotificationManager) MyBackgroundService.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                        notificationManager.notify(1, builder.build());
                                        Log.i("msdd", object.toString());
                                    }
                                }
                                PreferenceUtils.saveLength(unreadNotifications.length(), MyBackgroundService.this);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer  " + PreferenceUtils.getTokan(MyBackgroundService.this));
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MyBackgroundService.this);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }
}