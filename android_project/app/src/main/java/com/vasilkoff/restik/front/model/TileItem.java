package com.vasilkoff.restik.front.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;

public class TileItem implements AsymmetricItem {
  private int columnSpan;
  private int rowSpan;
  private String title;
  private String imageUrl;

  public TileItem() {
    this(1, 1, "Untitled","");
  }

  public TileItem(int columnSpan, int rowSpan, String title, String image) {
        this.columnSpan = columnSpan;
        this.rowSpan = rowSpan;
        this.title = title;
        setImageUrl(image);
  }

  public TileItem(Parcel in) {
    readFromParcel(in);
  }

  @Override public int getColumnSpan() {
    return columnSpan;
  }

  @Override public int getRowSpan() {
    return rowSpan;
  }

  public String getTitle() {
    return title;
  }

  @Override public String toString() {
    return String.format("%s: %sx%s", title, rowSpan, columnSpan);
  }

  @Override public int describeContents() {
    return 0;
  }

  private void readFromParcel(Parcel in) {
    columnSpan = in.readInt();
    rowSpan = in.readInt();
    title = in.readString();
  }

  @Override public void writeToParcel(@NonNull Parcel dest, int flags) {
    dest.writeInt(columnSpan);
    dest.writeInt(rowSpan);
    dest.writeString(title);
  }

  /* Parcelable interface implementation */
  public static final Parcelable.Creator<TileItem> CREATOR = new Parcelable.Creator<TileItem>() {
    @Override public TileItem createFromParcel(@NonNull Parcel in) {
      return new TileItem(in);
    }

    @Override @NonNull public TileItem[] newArray(int size) {
      return new TileItem[size];
    }
  };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
