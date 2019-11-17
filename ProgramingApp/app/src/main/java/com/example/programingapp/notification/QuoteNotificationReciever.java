package com.example.programingapp.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.programingapp.utils.ConvertUtil;
import com.example.programingapp.model.Quote;
import com.example.programingapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.programingapp.RetrofitClient.BASE_URL;

public class QuoteNotificationReciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("NotificationReciever", "onReceive: ");




        RandomQuoteAsyncTask randomQuoteAsyncTask = new RandomQuoteAsyncTask(context);
        randomQuoteAsyncTask.execute();
    }




    class RandomQuoteAsyncTask extends AsyncTask<Void, Void, Quote>{


        WeakReference<Context> weakReference;

        public RandomQuoteAsyncTask(Context context) {
            this.weakReference = new WeakReference<>(context);
        }

        @Override
        protected Quote doInBackground(Void... voids) {

            Uri uri = Uri.parse(BASE_URL + "quotes/random");

            Quote quotes = null;

            try {
                URL url = new URL(uri.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                if(responseCode == 200){
                    InputStream inputStream = httpURLConnection.getInputStream();
                    String results = ConvertUtil.convertIsToString(inputStream);


                    JSONObject jsonObject = new JSONObject(results);

                    String quoteEn = (String) jsonObject.get("en");

                    String author = (String) jsonObject.get("author");


                    quotes = new Quote(author, quoteEn, 1.0, "", 0);

                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return quotes;







        }



        @Override
        protected void onPostExecute(Quote quotes) {
            super.onPostExecute(quotes);

            String quoteResult = quotes.getEn() + "\n\n"  + quotes.getAuthor();

            NotificationManager notificationManager = (NotificationManager) weakReference.get().getSystemService(Context.NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel notificationChannel = new NotificationChannel("CHANNEL_ID", "Channel_1", NotificationManager.IMPORTANCE_DEFAULT);
                if(notificationManager != null){
                    notificationManager.createNotificationChannel(notificationChannel);
                }
            }

            Notification notification = new NotificationCompat.Builder(weakReference.get(),"CHANNEL_ID" )
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Quote of the day")
                    .setContentText(quoteResult)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(quoteResult))
                    .build();

            if(notificationManager != null){
                notificationManager.notify(1, notification);
            }

        }
    }






}



