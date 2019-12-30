package com.divakrishnam.quizroom;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "mahasiswa", indices = {@Index(value = "npm", unique = true)})
public class Mahasiswa implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int userId;
    private String npm;
    private String namaDepan;
    private String namaBelakang;
    private String username;
    private String password;
    private String email;
    private String jenisKelamin;
    private String noTelepon;
    private String programStudi;

    @Ignore
    public Mahasiswa(String npm, String namaDepan, String namaBelakang, String username, String password, String email, String jenisKelamin, String noTelepon, String programStudi) {
        this.npm = npm;
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.username = username;
        this.password = password;
        this.email = email;
        this.jenisKelamin = jenisKelamin;
        this.noTelepon = noTelepon;
        this.programStudi = programStudi;
    }

    public Mahasiswa(int userId, String npm, String namaDepan, String namaBelakang, String username, String password, String email, String jenisKelamin, String noTelepon, String programStudi) {
        this.userId = userId;
        this.npm = npm;
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.username = username;
        this.password = password;
        this.email = email;
        this.jenisKelamin = jenisKelamin;
        this.noTelepon = noTelepon;
        this.programStudi = programStudi;
    }

    public Mahasiswa() {

    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public void setProgramStudi(String programStudi) {
        this.programStudi = programStudi;
    }

    public int getUserId() {
        return userId;
    }

    public String getNpm() {
        return npm;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public String getProgramStudi() {
        return programStudi;
    }
}
