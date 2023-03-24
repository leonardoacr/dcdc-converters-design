package com.example.dcdcconvertersdesign.utils.simulationutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.dcdcconvertersdesign.views.SimulationView;
import com.github.mikephil.charting.charts.LineChart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class FileSaver {
    private static final String TAG = "FileSaver";

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void savePNG(File directory, LineChart chart, String fileNameKey, SimulationView view) {
        // Save the chart to PNG
        String fileName = fileNameKey + ".png";
        File file = new File(directory, fileName);
        int count = 1;
        while (file.exists()) {
            fileName = fileNameKey + count + ".png";
            file = new File(directory, fileName);
            count++;
        }

        try {
            // Get a bitmap of the chart
            Bitmap chartBitmap = chart.getChartBitmap();

            // Save the bitmap to a file using the MediaStore API
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            String relativePath = Environment.DIRECTORY_DOWNLOADS + "/DCDCConvertersDesign/Screenshots/";
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath);

            Uri uri = view.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
            OutputStream outputStream = view.getContentResolver().openOutputStream(uri);
            chartBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();

            view.alertBox("File saved to " + directory.getAbsolutePath());
            Log.d(TAG, "PNG FILE SAVED TO: " + file.getAbsolutePath());
            showInFileManager(file, view);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "ERROR: " + e);
            view.alertBox("Failed to save file. ERROR: " + e);
        }
    }


    private static String generateCSVFromChartData() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < GraphUtils.xGlobal.length; i++) {
            stringBuilder.append(GraphUtils.xGlobal[i]).append(",").
                    append(GraphUtils.yGlobal[i]).append("\n");
        }
        return stringBuilder.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void saveCSV(File directory, String fileNameKey, SimulationView view) {
        // Save the chart to CSV
        String directoryPath = directory.getAbsolutePath();
        String fileName = fileNameKey + ".csv";
        File file = new File(directory, fileName);
        int count = 1;
        while (file.exists()) {
            fileName = fileNameKey + count + ".csv";
            file = new File(directory, fileName);
            count++;
        }

        String csv = generateCSVFromChartData(); // This method generates the CSV data

        try {
            if (file.exists()) {
                if (file.delete()) {
                    Log.d(TAG, "Existing file deleted: " + file.getAbsolutePath());
                } else {
                    view.alertBox("Failed to delete existing file: " + file.getAbsolutePath());
                    return;
                }
            }

            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv");
            String relativePath = Environment.DIRECTORY_DOWNLOADS + "/DCDCConvertersDesign/CSV/";
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath);

            Uri uri = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                uri = view.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
            }

            OutputStream outputStream = view.getContentResolver().openOutputStream(uri);
            outputStream.write(csv.getBytes());
            outputStream.close();
            view.alertBox("File saved to " + directoryPath);
            showInFileManager(file, view);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "ERROR: " + e);
            view.alertBox("Failed to save file. ERROR: " + e);
        }
    }

    private static void showInFileManager(File directory, SimulationView view) {
        String directoryPath = directory.getPath();
        Log.d(TAG, "Directory to open: " + directoryPath);

        String authority = view.getApplicationContext().getPackageName() + ".fileprovider";
        Uri uri = FileProvider.getUriForFile(view, authority, directory);
        Log.d(TAG, "URI: " + uri);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(uri, "*/*");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            view.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            view.alertBox("No app found to handle the file.");
        }
    }
}
