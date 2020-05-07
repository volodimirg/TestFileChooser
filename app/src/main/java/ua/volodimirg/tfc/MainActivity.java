package ua.volodimirg.tfc;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aditya.filebrowser.Constants;
import com.aditya.filebrowser.FileChooser;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    Button fPicker;
    Button fPicker2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT> Build.VERSION_CODES.M&& checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
        }


        button = (Button)findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        fPicker=(Button) findViewById(R.id.fpicker);
        fPicker2=(Button) findViewById(R.id.fpicker2);


        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //File root = Environment.` `;
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(1000)
                        .withHiddenFiles(true)
                        .start();
            }
        });
        fPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myFileIntent=new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent,10);
            }
        });
        fPicker2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(getApplicationContext(), FileChooser.class);
                i2.putExtra(Constants.SELECTION_MODE, Constants.SELECTION_MODES.MULTIPLE_SELECTION.ordinal());
                startActivityForResult(i2, 11);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            textView.setText(filePath);
        }
        if (requestCode == 10 && resultCode == RESULT_OK) {
            String path=data.getData().getPath();
            textView.setText(path);
        }
        if (requestCode == 11 && data!=null) {
            if (resultCode == RESULT_OK) {
                ArrayList<Uri> selectedFiles  = data.getParcelableArrayListExtra(Constants.SELECTED_ITEMS);
                textView.setText(selectedFiles.toString());
            }
        }
    }

}
