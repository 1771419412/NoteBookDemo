package com.example.notebookdemo;

import java.util.List;

/**
 * Created by 雪无痕 on 2017/5/5.
 */

public class NoteDao {
    public static void insert(NoteBook noteBook){
        BaseApplication.getDaoInstant().getNoteBookDao().insertOrReplace(noteBook);
    }
    public static List<NoteBook> query(){
        return BaseApplication.getDaoInstant().getNoteBookDao().loadAll();
    }

    public static List<NoteBook> queryNote(String title){
        return BaseApplication.getDaoInstant().getNoteBookDao()
                 .queryBuilder()
                .where(NoteBookDao.Properties.Title.eq(title)).list();
    }
    public static void update(NoteBook noteBook){
        BaseApplication.getDaoInstant().getNoteBookDao().update(noteBook);
    }

    public static void delete(NoteBook noteBook){
        BaseApplication.getDaoInstant().getNoteBookDao().delete(noteBook);
    }
}
