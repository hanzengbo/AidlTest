package media.xiaoyi.com.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by HanZengbo on 2017/7/6.
 */
public class Book implements Parcelable{
    private String name;
    private float price;


    public Book(){
        name = "";
        price = 0.0f;
    }

    public Book(String name, float price){
        this.name = name;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(float price){
        this.price = price;
    }

    public boolean equals(Book other){
        if(other == null){
            return false;
        }
        return TextUtils.equals(name, other.name);
    }

    protected Book(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(price);
    }

    public void readFromParcel(Parcel parcel){
        name = parcel.readString();
        price = parcel.readFloat();
    }
}
