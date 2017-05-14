package dk.aau.gr6406.trainez;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {

    private String excName;
    private String videoPath;
    private int repetitions;
    private String category;
    private int defaultVal;

    public Exercise() {
    }

    public Exercise(String excName, String videoPath, int repetetions, String category, int defaultVal) {
        this.excName = excName;
        this.videoPath = videoPath;
        this.repetitions = repetetions;
        this.category = category;
        this.defaultVal = defaultVal;
    }


    public String getExcName() {
        return excName;
    }

    public void setExcName(String excName) {
        this.excName = excName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetetions) {
        this.repetitions = repetetions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(int defaultVal) {
        this.defaultVal = defaultVal;
    }



    protected Exercise(Parcel in) {
        excName = in.readString();
        videoPath = in.readString();
        repetitions = in.readInt();
        category = in.readString();
        defaultVal = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(excName);
        dest.writeString(videoPath);
        dest.writeInt(repetitions);
        dest.writeString(category);
        dest.writeInt(defaultVal);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Exercise> CREATOR = new Parcelable.Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };
}