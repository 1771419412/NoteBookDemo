package com.example.notebookdemo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class AddActivity extends AppCompatActivity {
    private ImageView msearch;
    private ImageView mback;
    private TextView mTitle;
    private TextView mTime;

    private EditText edtTitle;
    private EditText edtContent;
    private String title;
    private String content;
    private Button btnSave;
    private Spinner addSpinner;
    private String mStr;
    private String mSaveDate;
    private int addImg;
    private ImageView addImag;
    private Button btnAddImag;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private final int IMAGE_CODE = 0;
    private Bitmap mBitmap;
    private String mPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initViews();
        initList();
        initData();
        selectImag();
    }

    private void selectImag() {
        addImag = (ImageView) findViewById(R.id.add_imag);
        btnAddImag = (Button) findViewById(R.id.btn_add_imag);
        btnAddImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });

    }

    private void setImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        startActivityForResult(intent, IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mBitmap = null;
            ContentResolver resolver = getContentResolver();
            if (requestCode == IMAGE_CODE) {

                try {
                    Uri originalUri = data.getData();
                    mBitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                    addImag.setImageBitmap(ThumbnailUtils.extractThumbnail(mBitmap, 100, 100));

                    //获取图片地址
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(originalUri, proj, null, null, null);

                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    mPath = cursor.getString(column_index);
                /*Bitmap bitmap = BitmapFactory.decodeFile(mPath);*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 获取数据的方法
     */
    private void initData() {

        edtTitle = (EditText) findViewById(R.id.edt_title);
        edtContent = (EditText) findViewById(R.id.edt_content);
        btnSave = (Button) findViewById(R.id.btn_save);
        mTime = (TextView) findViewById(R.id.time);
        mTime.setText(getCreateDate());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSaveDate = getCreateDate();
                title = edtTitle.getText().toString().trim();
                content = edtContent.getText().toString().trim();

                NoteBook noteBook = new NoteBook();
                if (mPath != null) {
                    noteBook.setImage(mPath);
                } else {
                    noteBook.setImage(String.valueOf(R.mipmap.android));
                }

                noteBook.setTitle(title);
                noteBook.setType(mStr);
                noteBook.setContent(content);
                noteBook.setCreatedate(mSaveDate);
                NoteDao.insert(noteBook);
                Log.e("aaa", "addNoteBook: " + NoteDao.query());
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                NoteDao.query();
                Log.i("JJY", "onClick: " + mPath);


            }
        });
    }

    private void initList() {
        addSpinner = (Spinner) findViewById(R.id.add_spinner);
        String[] mItems = getResources().getStringArray(R.array.languages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addSpinner.setAdapter(adapter);
        mStr = (String) addSpinner.getSelectedItem();
        addSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] languages = getResources().getStringArray(R.array.languages);
                mStr = (String) addSpinner.getSelectedItem();
                Toast.makeText(AddActivity.this, "你点击的是：" + languages[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private String getCreateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        java.util.Date date = new java.util.Date();
        String str = sdf.format(date);
        return str;
    }

    private void initViews() {
        msearch = (ImageView) findViewById(R.id.search);
        msearch.setVisibility(View.GONE);
        mTitle = (TextView) findViewById(R.id.text_title);
        mTitle.setText("添加记事本");
        mback = (ImageView) findViewById(R.id.back);
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
