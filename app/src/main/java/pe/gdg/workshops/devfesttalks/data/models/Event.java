package pe.gdg.workshops.devfesttalks.data.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Event.
 */
public final class Event implements Parcelable {

    /**
     * The constant CREATOR.
     */
    public final static Parcelable.Creator<Event> CREATOR = new Creator<Event>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Event createFromParcel(Parcel in) {
            Event instance = new Event();
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.type = ((String) in.readValue((String.class.getClassLoader())));
            instance.level = ((String) in.readValue((String.class.getClassLoader())));
            instance.location = ((String) in.readValue((String.class.getClassLoader())));
            instance.category = ((String) in.readValue((String.class.getClassLoader())));
            instance.date = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.speaker, (java.lang.String.class.getClassLoader()));
            return instance;
        }

        public Event[] newArray(int size) {
            return (new Event[size]);
        }

    };

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("speaker")
    @Expose
    private List<String> speaker = new ArrayList<>();

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets speaker.
     *
     * @return the speaker
     */
    public List<String> getSpeaker() {
        return speaker;
    }

    /**
     * Sets speaker.
     *
     * @param speaker the speaker
     */
    public void setSpeaker(List<String> speaker) {
        this.speaker = speaker;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(type);
        dest.writeValue(level);
        dest.writeValue(location);
        dest.writeValue(category);
        dest.writeValue(date);
        dest.writeList(speaker);
    }

    public int describeContents() {
        return 0;
    }

}
