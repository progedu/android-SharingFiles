package com.example.sharingfiles;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

public class FileSelectActivity extends AppCompatActivity {

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

        // Sharing Setting
        Uri fileUri = FileProvider.getUriForFile(
                FileSelectActivity.this,
                "com.example.sharingfiles.fileprovider",
                newFile);
    }

    public void select(View view) {

    }
}
