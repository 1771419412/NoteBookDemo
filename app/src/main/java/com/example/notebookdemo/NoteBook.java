package com.example.notebookdemo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 雪无痕 on 2017/5/3.
 */
@Entity
public class NoteBook {
    @Id
    private Long id;
    private String image;
    private String title;
    private String type;
    private String content;
    private String createdate;
    @Generated(hash = 1494098671)
    public NoteBook(Long id, String image, String title, String type,
            String content, String createdate) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.type = type;
        this.content = content;
        this.createdate = createdate;
    }
    @Generated(hash = 2066935268)
    public NoteBook() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getCreatedate() {
        return this.createdate;
    }
    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }
    


    }
   


