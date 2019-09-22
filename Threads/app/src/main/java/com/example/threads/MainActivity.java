package com.example.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.threads.model.Book;
import com.example.threads.model.BooksResponse;
import com.example.threads.model.VolumeInfo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ManiActivity";

    final String BASE_URL =
            "https://www.googleapis.com/books/v1/volumes?";
    final String QUERY_PARAM = "q";
    final String MAX_RESULTS = "maxResults";
    final String PRINT_TYPE = "printType";


    private ProgressBar progressBar;

    private TextView textView;
    private TextView textViewWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
//        textViewWifi = findViewById(R.id.textViewWifi);
        progressBar = findViewById(R.id.progressBar);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Progresbar-ot se prikazuva, podatocite se krijat pred da se napravi povik
                progressBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);

//                Se kreira Uri koe se sostoi od Base Url i opcionalni query parametri
                Uri uri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, "pride")
                        .appendQueryParameter(MAX_RESULTS, "5")
                        .appendQueryParameter(PRINT_TYPE, "books")
                        .build();

                try {
//                    Od prethodno kreiranoto Uri se kreira URL
                    URL url = new URL(uri.toString());

                    OkHttpClient okHttpClient = new OkHttpClient();
//                    Od prethodno kreiraniot URL se kreira OkHttp request
//                    Tuka moze da se dodadat Headers
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
//                    So newCall se kreira nov povik vo koj se prakja requestot
//                    Posel toa se povikuva metodata enqueue. Ovaa metoda go izvrsuva povikot.
//                    Vo enqueue se prakja implementacija na Callback, vo koj treba da implementirame
//                    sto treba da pravi applikacijata koja povikot e uspesen (onResponse) i koga
//                    povikot ne e uspesen (onFailure).
                    okHttpClient.newCall(request)
                            .enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                                    Ovaa metoda kje se izvrsi koga povikot kje padne, vo slucajov nie
//                                    go informirame korisnikot (so Toast) deka se slucila nekoja greska
//                                    i go sokrivame progressBarot. Toa mora da go napravime so netodata
//                                    post zatoa sto ovoj kod se izvrsuva vo worker thread a ne vo Main thread.
                                    progressBar.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                            textView.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

//                                    Tuka go dobivame responsot. Tuka treba da go obrabotime ovoj
//                                    response i da gi prikazeme podatocite.

//                                    Vo response.body() se naogaat podatocite koi gi vrakja apito
//                                    So response.code() moze da proverime dali responsot e uspesen (Ako
//                                    response.code() == 200)

                                    final ResponseBody body = response.body();
                                    if (body != null) {

//                                        Go zemame responsot kako string
                                        final String result = body.string();

                                        try {
//                                            Kreirame JSONObject od responsot. JSONObject zatoa sto
//                                            ovoj povik ni vrakja objekt (ne niza).
                                            JSONObject object = new JSONObject(result);

//                                            Go zemame Stringot kind i integerot totalItems od json objektot
                                            String kind = (String) object.get("kind");
                                            int totalItems = object.getInt("totalItems");

//                                            Ja zemame nizata items od json objektot
                                            JSONArray jsonArray = object.getJSONArray("items");

//                                            Kreirame prazna niza koja kje ja popolnime so objekti od
//                                            json nizata koja ja zedovme pogore
                                            List<Book> items = new ArrayList<>();

//                                            Treba da gi mapirame site objekti od jsonArray vo items,
//                                            zatoa mora da gi iterirame site objekti od jsonArray i objekt
//                                            po objekt da gi konvertirame vo Book objekti i da gi dodavame
//                                            vo items listata
                                            for (int i = 0; i < jsonArray.length(); i++) {
//                                                Go zemame json objektot od jsonArray
                                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

//                                                od json objektot gi zemame stringovite so klucevi "kind" i "id"
                                                String bookKind = jsonObject.getString("kind");
                                                String id = jsonObject.getString("id");

//                                                "volumeInfo" e slozen objekt i za toa kje treba da kreirame klasa
//                                                no prvo treba da go zememe jsonObjektot i od toj objekt da gi izvleceme
//                                                podatocite koi gi ima volumeInfo
                                                JSONObject volumeInfoJson = jsonObject.getJSONObject("volumeInfo");

                                                String title = volumeInfoJson.getString("title");
//                                                Za nekoi knigi vo volumeInfo ne dobivame description podatok.
//                                                Zatoa morame prvo da proverime dali postoi podatokot
                                                String description = "";
                                                if (volumeInfoJson.has("description")) {
                                                    description = volumeInfoJson.getString("description");
                                                }
//                                                volumeInfo ima lista od Stringovi vo poleto "authors"
                                                List<String> authors = new ArrayList<>();
                                                JSONArray jsonAuthors = volumeInfoJson.getJSONArray("authors");
                                                for (int j = 0; j < jsonAuthors.length(); j++) {
                                                    String author = (String) jsonAuthors.get(j);
                                                    authors.add(author);
                                                }

//                                                Otkako gi zedovme site informacii za volumeInfo, kreirame nov objekt
                                                VolumeInfo volumeInfo = new VolumeInfo(title, authors, description);

//                                                Tuka vekje gi imame site potrebni podatoci za da kreirame objekt
//                                                od klasata Book
                                                Book book = new Book(bookKind, id, volumeInfo);
//                                                Novo kreiraniot objekt go dodavame vo listata od knigi
                                                items.add(book);
                                            }

//                                            Tuka gi imame site podatoci za da kreirame objekt od klasata
//                                            BookResponse i so ova vekje imame Java objekt so koj mozeme da
//                                            manipulirame
                                            final BooksResponse booksResponse = new BooksResponse();
                                            booksResponse.setKind(kind);
                                            booksResponse.setTotalItems(totalItems);
                                            booksResponse.setItems(items);

//                                            morame da se prefrlime vo main thread
                                            textView.post(new Runnable() {
                                                @Override
                                                public void run() {
//                                                    go krieme progressBar-or a go prikazuvame textViewto
                                                    progressBar.setVisibility(View.GONE);
                                                    textView.setVisibility(View.VISIBLE);
//                                                    Tuka idealno bi se kreiral adapter i bi se postavil vo
//                                                    recyclerView, no momentalno samo prikazuvame nekoj podatok
//                                                    vo textViewto
                                                    textView.setText(String.valueOf(booksResponse.getTotalItems()));
                                                }
                                            });
                                        } catch (JSONException e) {
//                                            isto kako i vo onFalure metodot pogore
                                            e.printStackTrace();
                                            progressBar.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.GONE);
                                                    textView.setVisibility(View.VISIBLE);
                                                }
                                            });

                                        }
                                    }
                                }
                            });

//                    BooksAsyncTask booksAsyncTask = new BooksAsyncTask();
//                    booksAsyncTask.execute(url);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class BooksAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String result = "";
            HttpURLConnection connection = null;
            InputStream inputStream = null;

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setDoInput(true);
                connection.connect();

                int responseCode = connection.getResponseCode();

                inputStream = connection.getInputStream();

                result = convertIsToString(inputStream, 500);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }

        private String convertIsToString(InputStream inputStream, int len) throws IOException {
            StringBuilder sb = new StringBuilder();
            InputStreamReader isReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isReader);
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString();
        }
    }


    private void connectionManager() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiInfo != null && wifiInfo.isConnected()) {
                textViewWifi.setText("Wifi connection!");
            } else {
                textViewWifi.setText("No Wifi connection!");
            }


            if (networkInfo != null && networkInfo.isConnected()) {
                textView.setText("Internet connection!");
            } else {
                textView.setText("No internet connection!");
            }
        }
    }


    public void startAsyncTask() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAsyncTask task = new MyAsyncTask();
                task.execute("My task String");
            }
        });
    }

    class MyAsyncTask extends AsyncTask<String, Void, String> {

        String otherData;

        @Override
        protected void onPreExecute() {
            // se izvrsuva vo Main thread
            otherData = "Some other data";
        }

        @Override
        protected String doInBackground(String... strings) {
            // Vo Worker Thread
            Log.d("MyAsincTask", strings[0]);
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "New Name Button";
        }

        @Override
        protected void onPostExecute(String s) {
            // se izvrsuva vo Main Thread
            textView.setText(s);
        }
    }


    public void threads() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Does something very long

                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


//                        textView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                textView.setText("New Thread");
//                                textView.setAllCaps(true);
//                            }
//                        });

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("New Thread");
                        textView.setAllCaps(true);
                    }
                });

            }
        });
        thread.start();
    }

}
