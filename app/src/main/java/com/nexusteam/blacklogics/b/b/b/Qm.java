package b.b.b;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.nexusteam.internal.beans.ProjectFileBean;

public class Qm implements Parcelable {

    @SerializedName("activity_name")
    private String activityName;

    @SerializedName("sc_id")
    private String scId;

    @SerializedName("layout_name")
    private String layoutName;

    @SerializedName("info")
    private String info;

    @SerializedName("timestamp")
    private long timestamp;

    public Qm() {
        this.timestamp = System.currentTimeMillis();
    }

    public Qm(String activityName, String scId, String layoutName, String info) {
        this.activityName = activityName;
        this.scId = scId;
        this.layoutName = layoutName;
        this.info = info;
        this.timestamp = System.currentTimeMillis();
    }

    protected Qm(Parcel in) {
        activityName = in.readString();
        scId = in.readString();
        layoutName = in.readString();
        info = in.readString();
        timestamp = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(activityName);
        dest.writeString(scId);
        dest.writeString(layoutName);
        dest.writeString(info);
        dest.writeLong(timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Qm> CREATOR = new Creator<Qm>() {
        @Override
        public Qm createFromParcel(Parcel in) {
            return new Qm(in);
        }

        @Override
        public Qm[] newArray(int size) {
            return new Qm[size];
        }
    };


    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public long getTimestamp() {
        return timestamp;
    }


    public boolean isValid() {
        return activityName != null && !activityName.isEmpty() 
            && scId != null && !scId.isEmpty();
    }


    public ProjectFileBean toProjectFileBean() {
        if (!isValid()) return null;
        

        String baseFileName = activityName;
        if (baseFileName.endsWith("Activity")) {
            baseFileName = baseFileName.substring(0, baseFileName.length() - 8);
        }
        baseFileName = baseFileName.toLowerCase();
        

        ProjectFileBean projectFile = new ProjectFileBean(
            ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY, 
            baseFileName
        );
        

        if (layoutName != null && !layoutName.isEmpty()) {
            projectFile.presetName = layoutName;
        }
        

        projectFile.setActivityOptions(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR);
        

        projectFile.orientation = ProjectFileBean.ORIENTATION_PORTRAIT;
        
        return projectFile;
    }


    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Qm fromJson(String json) {
        return new Gson().fromJson(json, Qm.class);
    }

    @Override
    public String toString() {
        return "Qm{" +
                "activityName='" + activityName + '\'' +
                ", scId='" + scId + '\'' +
                ", layoutName='" + layoutName + '\'' +
                ", info='" + info + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}