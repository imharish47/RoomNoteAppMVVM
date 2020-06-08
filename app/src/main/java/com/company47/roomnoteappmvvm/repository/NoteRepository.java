package com.company47.roomnoteappmvvm.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.company47.roomnoteappmvvm.room.Note;
import com.company47.roomnoteappmvvm.room.NoteDao;
import com.company47.roomnoteappmvvm.room.NoteDatabase;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;


    public NoteRepository(Application application) {
        NoteDatabase database=NoteDatabase.getInstance(application.getApplicationContext());
        this.noteDao=database.getNoteDaoInstance();
        this.allNotes=noteDao.getAllNotes();

    }

    //have to code coz rooms does'nt provide database operations
    // on mainThread so have to run them on separate thread
    public void insert(Note note){
        new InsertAsyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    /**
     * <__________________________>
     *
     */

    //INSERT
public static class InsertAsyncTask extends AsyncTask<Note,Void,Void>{
    NoteDao noteDao;

        public InsertAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }


    //UPDATE
    public static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        NoteDao noteDao;

        public UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    //DELETE NOTE
    public static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    //DELETE ALL
    public static class DeleteAllNoteAsyncTask extends AsyncTask<Void,Void,Void>{
        NoteDao noteDao;

        public DeleteAllNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... notes) {
            noteDao.deleteAllNotes();
            return null;
        }
    }







}
