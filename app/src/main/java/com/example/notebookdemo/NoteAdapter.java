package com.example.notebookdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/**
 * Created by 雪无痕 on 2017/5/3.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<NoteBook> mNoteBooks;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String mTitle;
    private RecyclerView mRecyclerView;

    public NoteAdapter(List<NoteBook> noteBooks, Context context) {
        this.mNoteBooks = noteBooks;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.note_book_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NoteBook noteBook = mNoteBooks.get(position);
        final Bitmap bitmap = BitmapFactory.decodeFile(noteBook.getImage());
        holder.mImagPath.setImageBitmap(bitmap);
        holder.mTitleNote.setText(noteBook.getTitle());
        holder.mTypeNote.setText(noteBook.getType());
        holder.mText.setText(noteBook.getContent());
        holder.mDateNote.setText(noteBook.getCreatedate());
        if (bitmap != null) {
            Log.e("JJY", "onBindViewHolder: ");
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("jjy", "onClick: ");
                mTitle = noteBook.getTitle();
                String type = noteBook.getType();
                String content = noteBook.getContent();
                String img = noteBook.getImage();
                Intent intent = new Intent(mContext, UpdateActivity.class);
                intent.putExtra("title", mTitle);
                intent.putExtra("type", type);
                intent.putExtra("content", content);
                intent.putExtra("img", img);
                mContext.startActivity(intent);

            }
        });
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("确认删除吗？");
                builder.setTitle("删除");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        NoteDao.delete(noteBook);
                        RefreshNotesList();

                       mNoteBooks.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(0, mNoteBooks.size());


                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });

    }


        public void RefreshNotesList(){
            int size=mNoteBooks.size();
            if(size>0){
                mNoteBooks.remove(mNoteBooks);
                notifyDataSetChanged();
            }

        }

    @Override
    public int getItemCount() {
        return mNoteBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item;
        private ImageView mImagPath;
        private TextView mTitleNote;
        private TextView mTypeNote;
        private TextView mDateNote;
        private TextView mText;


        public ViewHolder(View itemView) {
            super(itemView);
            item = (LinearLayout) itemView.findViewById(R.id.item);
            mImagPath = (ImageView) itemView.findViewById(R.id.imagPath);
            mTitleNote = (TextView) itemView.findViewById(R.id.title_note);
            mTypeNote = (TextView) itemView.findViewById(R.id.type_note);
            mDateNote = (TextView) itemView.findViewById(R.id.tv_date);
            mText = (TextView) itemView.findViewById(R.id.tv_content);

        }
    }
}
