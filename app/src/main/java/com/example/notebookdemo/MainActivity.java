package com.example.notebookdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private TextView mtitle;
    private RecyclerView mRecyclerView;
    private NoteAdapter mNoteAdapter;
    private List<NoteBook> mNoteBooks;

    private ImageView msearch;
    private ImageView mback;
    private FloatingActionButton mFloatingActionButton;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

       recyclerViews();


    }
   @Override
    protected void onStart() {
        super.onStart();
        mNoteAdapter.RefreshNotesList();
    }


    private void recyclerViews() {
        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        initNoteBooks();
        LinearLayoutManager manager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mNoteAdapter=new NoteAdapter(mNoteBooks,MainActivity.this);
        mRecyclerView.setAdapter(mNoteAdapter);

    }

    private void initNoteBooks() {
         mNoteBooks= new ArrayList<NoteBook>();
         mNoteBooks = NoteDao.query();

    }




    private void initViews() {
        mtitle= (TextView) findViewById(R.id.text_title);
        mtitle.setText("我的记事本");
        msearch= (ImageView) findViewById(R.id.search);
        msearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        mback= (ImageView) findViewById(R.id.back);
        mback.setVisibility(View.GONE);
        mFloatingActionButton= (FloatingActionButton) findViewById(R.id.fab_search1);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,AddActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }


}
