package com.divakrishnam.quizroom;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private Context mContext;
    private static DatabaseClient mInstance;

    private MahasiswaDatabase mahasiswaDatabase;

    private DatabaseClient(Context context) {
        mContext = context;

        mahasiswaDatabase = Room.databaseBuilder(mContext, MahasiswaDatabase.class, "QuizRoom.db").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public MahasiswaDatabase getMahasiswaDatabase() {
        return mahasiswaDatabase;
    }
}
