package com.example.mediaexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.*;
import android.provider.MediaStore;
import android.content.Intent;
import android.net.Uri;
import java.io.File;
import android.os.Environment;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.content.pm.PackageManager;
import android.widget.*; //Toast
import android.support.v4.app.ActivityCompat;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    private static final DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final String app_dir = "example";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickFunction(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.take_picture:

                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File pictureFile = configFileName("P", ".jpg");
                Uri uri = Uri.fromFile(pictureFile);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                                PackageManager.PERMISSION_GRANTED) {

                    Log.d("Camera Permission******", "Denied");
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                    return;
                }
                startActivityForResult(intentCamera, 1);
                break;

            case R.id.set_location:
                Intent intentMap = new Intent(this, MapsActivity.class);
                startActivityForResult(intentMap, 2);
                break;

            case R.id.accelerometer:
                Intent intentAcce = new Intent(this,Accelerometer.class);
                startActivityForResult(intentAcce, 3);
                break;
        }

    }

    private File configFileName(String prefix, String extension) {

        return new File(getExternalStorageDir(),
                prefix + sdf.format(new Date()) + extension);
    }


    public static File getExternalStorageDir() {
        File result = new File(Environment.getExternalStorageDirectory(),"example");
        Log.d("path*******",Environment.getExternalStorageDirectory().getAbsolutePath()  );

       if (!isExternalStorageWritable()) {
           Log.d("Picture*******","No Picture Storage Permission "  );
            return null; }

        if (!result.exists() && !result.mkdirs()) {
            Log.d("Picture*******","Same file name or no such directory"  );
            return null; }

        return result;
    }

    //Store the file
    public static boolean isExternalStorageWritable() {

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d("Picture*******","Media mounted " + state );
            return true;
        }

        return false;
    }

@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
           /* ImageView imageView = (ImageView)findViewById(R.id.camera_result);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            System.out.println(imageBitmap);
            imageView.setImageBitmap(imageBitmap); */
            Toast.makeText(this, "photo is taken successfully", Toast.LENGTH_LONG).show();
       }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }



}
