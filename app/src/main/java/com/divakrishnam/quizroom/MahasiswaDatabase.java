package com.divakrishnam.quizroom;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Mahasiswa.class}, version = 1)
public abstract class MahasiswaDatabase extends RoomDatabase {
    public abstract MahasiswaDao mahasiswaDao();
}
