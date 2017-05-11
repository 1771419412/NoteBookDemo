package com.example.notebookdemo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    private List<NoteBook> mNoteBooks;
    private ImageView updateImag;
    private EditText updateTitle;
    private Spinner updateSpinner;
    private EditText updateContent;
    private Button btnUpdate;
    private ImageView addImag;
    private Button btnAddImag;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private final int IMAGE_CODE = 0;
    private Bitmap mBitmap;

    private ImageView msearch;
    private ImageView mback;
    private TextView mtitle;
    private TextView mTime;

    private String mTitle;
    private String mType;
    private String mContent;
    private String mImg;
    private String mPath;
    private String mUpdateDate;
    private String upTitle;
    private String upContent;
    private String mStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mType = intent.getStringExtra("type");
        mContent = intent.getStringExtra("content");
        mImg = intent.getStringExtra("img");

        Log.e("UpadteActivity", mTitle);
        Log.e("UpadteActivity", mType);
        Log.e("UpadteActivity", mContent);
        Log.e("UpadteActivity", mImg);
        initViews();
        selectImag();
        initList();
        initBiao();

    }

    private void initBiao() {
        msearch = (ImageView) findViewById(R.id.search);
        msearch.setVisibility(View.GONE);
        mtitle = (TextView) findViewById(R.id.text_title);
        mtitle.setText("编辑记事本");
        mback = (ImageView) findViewById(R.id.back);
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void initViews() {
        updateTitle = (EditText) findViewById(R.id.update_edt_title);
        updateImag = (ImageView) findViewById(R.id.update_imag);
        updateContent = (EditText) findViewById(R.id.update_edt_content);
        mTime= (TextView) findViewById(R.id.time1);
        mTime.setText(getCreateDate());
        if (updateTitle != null) {
            updateTitle.setText(mTitle);
        }
        final Bitmap bitmap = BitmapFactory.decodeFile(mImg);
        updateImag.setImageBitmap(bitmap);
        if (updateContent != null) {
            updateContent.setText(mContent);
        }
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateDate = getCreateDate();
                upTitle =updateTitle.getText().toString().trim();
                upContent = updateContent.getText().toString().trim();
                List<NoteBook> noteBooks = NoteDao.queryNote(mTitle);
                if (noteBooks==null)
                    return;
                NoteBook noteBook = noteBooks.get(0);
                Log.e("jjy", "onClick: "+noteBook.getImage() );
                if(mPath==null){
                    noteBook.setImage(mImg);
                }else {
                    noteBook.setImage(mPath);
                }

                noteBook.setTitle(upTitle);
                noteBook.setContent(upContent);
                noteBook.setCreatedate(mUpdateDate);
                noteBook.setType(mStr);
                NoteDao.update(noteBook);

                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                  startActivity(intent);
                finish();
                NoteDao.query();

                Log.i("JJY", "onClick: " + mPath);
            }
        });
    }

    private void selectImag() {
        addImag = (ImageView) findViewById(R.id.update_imag);
        btnAddImag = (Button) findViewById(R.id.btn_update_imag);
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

    private String getCreateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        java.util.Date date = new java.util.Date();
        String str = sdf.format(date);
        return str;
    }

    private void initList() {
        updateSpinner = (Spinner) findViewById(R.id.update_spinner);
        String[] mItems = getResources().getStringArray(R.array.languages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        updateSpinner.setAdapter(adapter);
        mStr = (String) updateSpinner.getSelectedItem();
        updateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] languages = getResources().getStringArray(R.array.languages);
                mStr = (String) updateSpinner.getSelectedItem();
                Toast.makeText(UpdateActivity.this, "你点击的是：" + languages[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
