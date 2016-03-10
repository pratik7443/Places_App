package pratik.mynavapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 09-03-2016.
 */
public class PlaceGetSet implements Parcelable{

    int lId;
    String lName;
    String lByCar, lByTrain;
    double lLat, lLon;

    public PlaceGetSet(int lId, String lName, String lByCar, String lByTrain, double lLat, double lLon) {
        this.lId = lId;
        this.lName = lName;
        this.lByCar = lByCar;
        this.lByTrain = lByTrain;
        this.lLat = lLat;
        this.lLon = lLon;
    }


    protected PlaceGetSet(Parcel in) {
        lId = in.readInt();
        lName = in.readString();
        lByCar = in.readString();
        lByTrain = in.readString();
        lLat = in.readDouble();
        lLon = in.readDouble();
    }

    public static final Creator<PlaceGetSet> CREATOR = new Creator<PlaceGetSet>() {
        @Override
        public PlaceGetSet createFromParcel(Parcel in) {
            return new PlaceGetSet(in);
        }

        @Override
        public PlaceGetSet[] newArray(int size) {
            return new PlaceGetSet[size];
        }
    };

    public int getlId() {
        return lId;
    }

    public String getlName() {
        return lName;
    }

    public String getlByCar() {
        return lByCar;
    }

    public String getlByTrain() {
        return lByTrain;
    }

    public double getlLat() {
        return lLat;
    }

    public double getlLon() {
        return lLon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(lId);
        parcel.writeString(lName);
        parcel.writeString(lByCar);
        parcel.writeString(lByTrain);
        parcel.writeDouble(lLat);
        parcel.writeDouble(lLon);
    }
}
