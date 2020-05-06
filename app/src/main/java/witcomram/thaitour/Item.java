package witcomram.thaitour;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Item implements Parcelable {
    private long id;
    private String title;
    private String description;
    private String city;
    private String image;
    private int choice;
    private double latitude = 13.751422;
    private double longitude = 100.492571;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city =city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LatLng getLatLng() {
        LatLng latLng = new LatLng(latitude, longitude);
        return latLng;
    }

    public Item() {
    }

    public Item(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        city = in.readString();
        image = in.readString();
        choice = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(city);
        dest.writeString(image);
        dest.writeInt(choice);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public static final Creator<Item> CREATOR =
            new Creator<Item>() {

                @Override
                public Item createFromParcel(Parcel source) {
                    return new Item(source);
                }

                @Override
                public Item[] newArray(int size) {
                    return new Item[size];
                }

            };
}
