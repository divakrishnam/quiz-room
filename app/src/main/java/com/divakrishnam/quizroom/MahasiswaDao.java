package com.divakrishnam.quizroom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MahasiswaDao {
    @Query("SELECT * FROM mahasiswa")
    List<Mahasiswa> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Mahasiswa mahasiswa);

    @Update
    int update(Mahasiswa mahasiswa);

    @Delete
    int delete(Mahasiswa mahasiswa);
}
