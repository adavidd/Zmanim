package co.il.zmanim.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.il.zmanim.MessageObject;

@Dao
public interface MessageDao {

    @Query("Select * from messages")
    List<MessageObject> getMessagesList();

    @Insert
    void insertMessage(MessageObject messageObject);

    @Update
    void updateMessage(MessageObject messageObject);

    @Delete
    void deleteMessage(MessageObject messageObject);

}
