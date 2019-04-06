package ar.com.sustentate.mw.models;


import javax.validation.constraints.NotNull;
import java.util.Date;

public class Event {
    private String _id;
    private String _rev;

    @NotNull
    private String title;

    @NotNull
    private String description;

    private boolean published;

    private String imageUrl;

    //private boolean promoted;

    @NotNull
    private String address;

    @NotNull
    private Long price;

    @NotNull
    private String link;

    @NotNull
    private Date startDateTime;

    @NotNull
    private EventType type;

    private Contact contact;

    public Event(String aTitle, String aDescription, String imageUrl, String anAddress, String aLink, Date aDateTime, EventType type, Contact aContact, Long price) {
        this.title = aTitle;
        this.description = aDescription;
        this.published = false;
        //this.promoted = false;
        this.imageUrl = imageUrl;
        this.address = anAddress;
        this.link = aLink;
        this.startDateTime = aDateTime;
        this.type = type;
        this.contact = aContact;
        this.price = price;
    }

    public Event() {
    }

    public String getId() {
        return this._id;
    }

    public String getRev() {
        return this._rev;
    }

    /*public boolean getPromoted() {
        return this.published;
    }*/

    public String getImageUrl() { return imageUrl; }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPublished() {
        return published;
    }

    public String getAddress() {
        return address;
    }

    public String getLink() {
        return link;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public Contact getContact() {
        return contact;
    }

    public EventType getType() {
        return type;
    }

    public Long getPrice() {
        return price;
    }
    public void setUrlImage(String url) {
    	this.imageUrl = url;
    }
}