package com.example.downloadimagesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final static String[] WRITE_PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final static int REQUEST_CODE = 1000;
    public final static String URL_LFC = "https://upload.wikimedia.org/wikipedia/en/thumb/0/0c/Liverpool_FC.svg/758px-Liverpool_FC.svg.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDownloadImage = findViewById(R.id.btnDownloadImage);
        btnDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    requestExternalStoragePermission();
                } else {
                    startDownloadingImage();
                }
            }

        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings_item) {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    private void requestExternalStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            openSnackbar();
        } else {
            ActivityCompat.requestPermissions(this, WRITE_PERMISSION, REQUEST_CODE);
        }
    }

    private void startDownloadingImage() {
        DownloadImageAsyncTask asyncTask = new DownloadImageAsyncTask(this);
        asyncTask.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownloadingImage();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    openSnackbar();
                } else {
                    Snackbar snackbar = Snackbar.make((View) findViewById(R.id.btnDownloadImage).getParent(), "We need write external storage permission to...", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);                        }
                    });
                    snackbar.show();
                }
            }
        }
    }

    private void openSnackbar() {
        Snackbar snackbar = Snackbar.make((View) findViewById(R.id.btnDownloadImage).getParent(), "We need write external storage permission to...", Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this, WRITE_PERMISSION, REQUEST_CODE);
            }
        });
        snackbar.show();
    }

    static class DownloadImageAsyncTask extends AsyncTask<Void, Integer, Boolean> {

        private WeakReference<Context> weakContext;

        public DownloadImageAsyncTask(Context context) {
            this.weakContext = new WeakReference<>(context);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            int count;
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                URL url = new URL(URL_LFC);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                inputStream = urlConnection.getInputStream();
                int contentLength = urlConnection.getContentLength();

//                File extDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File extDir = weakContext.get().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
//                File imageFile = new File(extDir + "/MyApp");
//                if (!imageFile.exists()) {
//                    imageFile.mkdirs();
//                }

                Uri uri = Uri.parse(URL_LFC);

                String fileName = uri.getLastPathSegment();

//                File imageFile = new File(extDir + fileName);

                OutputStream output = new FileOutputStream(extDir + "/" +fileName);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    publishProgress((int)(total*100/contentLength));
                    output.write(data, 0, count);
                }

                output.flush();

                output.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                return false;
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
