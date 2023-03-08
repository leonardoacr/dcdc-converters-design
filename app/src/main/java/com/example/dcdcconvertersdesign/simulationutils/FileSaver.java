package com.example.dcdcconvertersdesign.simulationutils;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.dcdcconvertersdesign.model.SimulationModel;
import com.example.dcdcconvertersdesign.view.SimulationActivity;
import com.github.mikephil.charting.charts.LineChart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSaver {
    private static final String TAG = "FileSaver";
   public static void savePNG(String directoryPath, LineChart chart, String fileNameKey,
                              SimulationActivity view) {
        // Save the chart to PNG
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String fileName = fileNameKey + ".png";
        File file = new File(directoryPath, fileName);
        int count = 1;
        while (file.exists()) {
            fileName = fileNameKey + " (" + count + ").png";
            file = new File(directoryPath, fileName);
            count++;
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            Bitmap chartBitmap = chart.getChartBitmap();
            chartBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            Log.d(TAG, "File directory: " + directoryPath);
            Log.d(TAG, "File created: " + file.exists());
            view.alertBox("File saved to " + directoryPath);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to save file. ERROR: " + e);
            view.alertBox("Failed to save file. ERROR: " + e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

    public static void saveCSV(String directoryPath, String fileNameKey, SimulationActivity view) {
        // Save the chart to CSV
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
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
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(csv.getBytes());
            outputStream.close();
            Log.d(TAG, "File directory: " + directoryPath);
            Log.d(TAG, "File created: " + file.exists());
            view.alertBox("File saved to " + directoryPath);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to save file. ERROR: " + e);
            view.alertBox("Failed to save file. ERROR: " + e);
        }
    }
}
