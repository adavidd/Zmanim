package co.il.zmanim;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "messages")
public class MessageObject {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "message")
    private String message;

    @ColumnInfo(name = "timeStarted")
    private long timeStarted;

    @ColumnInfo(name = "timeFinished")
    private long timeFinished;

    public MessageObject(int id, String message, long timeStarted, long timeFinished) {
        this.id = id;
        this.message = message;
        this.timeStarted = timeStarted;
        this.timeFinished = timeFinished;
    }

    @Ignore
    public MessageObject(String message, long timeStarted, long timeFinished) {
        this.message = message;
        this.timeStarted = timeStarted;
        this.timeFinished = timeFinished;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public long getTimeStarted() {
        return timeStarted;
    }

    public long getTimeFinished() {
        return timeFinished;
    }
}
