package com.example.notebookdemo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 雪无痕 on 2017/5/5.
 */

public class BaseApplication extends Application{
    private static DaoSession sDaoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        setCreateDatabase();
    }
    private void setCreateDatabase(){
        //创建数据库note.db"
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"note.db",null);
        //获取可写数据库
        SQLiteDatabase db=helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster=new DaoMaster(db);
        //获取Dao对象管理者
        sDaoSession=daoMaster.newSession();

    }
    public static DaoSession getDaoInstant(){
        return sDaoSession;
    }
}
