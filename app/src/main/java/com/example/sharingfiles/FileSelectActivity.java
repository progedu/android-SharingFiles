package com.example.sharingfiles;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

public class FileSelectActivity extends AppCompatActivity {

    private Intent mResultIntent =
            new Intent("com.example.sharingfiles.ACTION_RETURN_FILE");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_select);

        // Load file
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.image);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setImageURI(uri);

        // Create file
        File imagePath = new File(getFilesDir(), "images");
        if(!imagePath.isDirectory()) {
            imagePath.mkdir();
        }
        File newFile = new File(imagePath, "image.jpg");
        FileOutputStream outputStream;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            outputStream = new FileOutputStream(newFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Created File Log
        File[] files = imagePath.listFiles();
        for(File file: files) {
            Log.d("File in images:", file.toString());
        }

        // Sharing Setting
        Uri fileUri = FileProvider.getUriForFile(
                FileSelectActivity.this,
                "com.example.sharingfiles.fileprovider",
                newFile);

        mResultIntent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION);

        mResultIntent.setDataAndType(
                fileUri,
                getContentResolver().getType(fileUri));
        setResult(Activity.RESULT_OK, mResultIntent);
    }

    public void select(View view) {
        finish();
    }
}
