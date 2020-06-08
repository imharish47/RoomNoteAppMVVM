package com.company47.roomnoteappmvvm.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public static final String DATABASE_NAME="note_database";
    private static NoteDatabase instance;
    public abstract NoteDao getNoteDaoInstance();

    public static synchronized NoteDatabase getInstance(Context context){
        if (instance==null){
            instance= Room
                    .databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private  static  class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
    private NoteDao mNoteDao;

        public PopulateDbAsyncTask(NoteDatabase noteDatabase) {
            mNoteDao = noteDatabase.getNoteDaoInstance();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.insert(new Note("Arsenal"," w:30,l:2,d:6",1));
            mNoteDao.insert(new Note("Manchester United","w:24,l:6,d:8",2));
            mNoteDao.insert(new Note("Chelsea","w:20,l:10,d:8",3));
            mNoteDao.insert(new Note("Liverpool","w:18,l:10,d:10",4));

            return null;
        }
    }


}
