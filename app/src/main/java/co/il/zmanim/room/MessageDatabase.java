package co.il.zmanim.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import co.il.zmanim.MessageObject;

@Database(entities = MessageObject.class, exportSchema = false, version = 1)
public abstract class MessageDatabase extends RoomDatabase {

    private static final String DB_NAME = "message_db";
    private static MessageDatabase instance;

    public static synchronized MessageDatabase getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MessageDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();

        }

        return instance;
    }


    public abstract MessageDao messageDao();
}
