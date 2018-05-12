package ar.com.sustentate.mw.models;

import java.util.Date;

public class EcoTipResponse {
    private String text;
    private String title;
    private String imageUrl;
    private Date date;
    private long _id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        this._id = id;
    }
}
