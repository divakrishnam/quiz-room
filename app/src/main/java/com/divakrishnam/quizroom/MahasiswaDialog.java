package com.divakrishnam.quizroom;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MahasiswaDialog extends DialogFragment implements View.OnClickListener {

    public static final String USER_ID = "userid";
    public final static String NPM = "npm";
    public final static String NAMA_DEPAN = "namadepan";
    public final static String NAMA_BELAKANG = "namabelakang";
    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    public final static String EMAIL = "email";
    public final static String NO_TELEPON = "notelepon";
    public final static String JENIS_KELAMIN = "jenkel";
    public final static String PRODI = "prodi";

    private EditText etNpm, etNamaDepan, etNamaBelakang, etUsername, etPassword, etEmail, etNoTelepon;
    private Button btnSimpan, btnBatal;
    private RadioGroup rgJenisKelamin;
    private RadioButton rbLaki, rbPerempuan;
    private Spinner spProdi;

    private MahasiswaDatabase db;

    private String mNpm, mNamaDepan, mNamaBelakang, mUsername, mPassword, mEmail, mNoTelepon, mJenkel, mProdi;
    private int mUserId;

    public MahasiswaDialog() {

    }

    public static MahasiswaDialog newInstance(String title) {
        MahasiswaDialog frag = new MahasiswaDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_mahasiswa, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etNpm = view.findViewById(R.id.et_npm);
        etNamaDepan = view.findViewById(R.id.et_namadepan);
        etNamaBelakang = view.findViewById(R.id.et_namabelakang);
        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        etEmail = view.findViewById(R.id.et_email);
        etNoTelepon = view.findViewById(R.id.et_notelepon);
        rgJenisKelamin = view.findViewById(R.id.rg_jeniskelamin);
        rbLaki = view.findViewById(R.id.rb_laki);
        rbPerempuan = view.findViewById(R.id.rb_perempuan);
        spProdi = view.findViewById(R.id.sp_prodi);

        btnSimpan = view.findViewById(R.id.btn_simpan);
        btnBatal = view.findViewById(R.id.btn_batal);

        db = DatabaseClient.getInstance(getContext()).getMahasiswaDatabase();

        btnSimpan.setOnClickListener(this);
        btnBatal.setOnClickListener(this);

        mUserId = getArguments().getInt(USER_ID);
        mNpm = getArguments().getString(NPM);
        mNamaDepan = getArguments().getString(NAMA_DEPAN);
        mNamaBelakang = getArguments().getString(NAMA_BELAKANG);
        mUsername = getArguments().getString(USERNAME);
        mPassword = getArguments().getString(PASSWORD);
        mEmail = getArguments().getString(EMAIL);
        mNoTelepon = getArguments().getString(NO_TELEPON);
        mJenkel = getArguments().getString(JENIS_KELAMIN);
        mProdi = getArguments().getString(PRODI);

        if (mUserId != 0 && mNpm != null && mNamaDepan != null && mNamaBelakang != null && mUsername != null && mPassword != null && mEmail != null && mNoTelepon != null && mJenkel != null && mProdi != null) {
            etNpm.setText(mNpm);
            etNamaDepan.setText(mNamaDepan);
            etNamaBelakang.setText(mNamaBelakang);
            etUsername.setText(mUsername);
            etPassword.setText(mPassword);
            etEmail.setText(mEmail);
            etNoTelepon.setText(mNoTelepon);

            if(mJenkel.equals("L")){
                rbLaki.setChecked(true);
            }else if (mJenkel.equals("P")){
                rbPerempuan.setChecked(true);
            }

            Log.d("lili", mJenkel+mProdi);
        }

        spinnerProdi();
    }

    private void ubah(final Mahasiswa mahasiswa) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                int status = db.mahasiswaDao().update(mahasiswa);
                return status;
            }

            @Override
            protected void onPostExecute(Integer status) {
                if (status > 0) {
                    Toast.makeText(getContext(), "Data tersimpan", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Data gagal tersimpan", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

        MahasiswaDialogListener listener = (MahasiswaDialogListener) getActivity();
        listener.onFinishMahasiswaDialog();
    }

    private void simpan(final Mahasiswa mahasiswa) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                Mahasiswa mhs = new Mahasiswa();
                mhs.setNpm(mahasiswa.getNpm());
                mhs.setNamaDepan(mahasiswa.getNamaDepan());
                mhs.setNamaBelakang(mahasiswa.getNamaBelakang());
                mhs.setUsername(mahasiswa.getUsername());
                mhs.setPassword(mahasiswa.getPassword());
                mhs.setEmail(mahasiswa.getEmail());
                mhs.setJenisKelamin(mahasiswa.getJenisKelamin());
                mhs.setNoTelepon(mahasiswa.getNoTelepon());
                mhs.setProgramStudi(mahasiswa.getProgramStudi());

                long status = db.mahasiswaDao().insert(mahasiswa);
                return status;
            }

            @Override
            protected void onPostExecute(Long status) {
                if (status > 0) {
                    Toast.makeText(getContext(), "Data tersimpan", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Data gagal tersimpan", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

        MahasiswaDialogListener listener = (MahasiswaDialogListener) getActivity();
        listener.onFinishMahasiswaDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        String title = getArguments().getString("title", "Judul");
        getDialog().setTitle(title);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public void onClick(View view) {
        if (view == btnSimpan) {
            simpanMahasiswa();
            getDialog().dismiss();
        } else if (view == btnBatal) {
            getDialog().dismiss();
        }
    }

    private void simpanMahasiswa() {
        String npm = etNpm.getText().toString();
        String namaDepan = etNamaDepan.getText().toString();
        String namaBelakang = etNamaBelakang.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        String noTelepon = etNoTelepon.getText().toString();
        String jenkel = "";
        if (rbLaki.isChecked()){
            jenkel = "L";
        }else if (rbPerempuan.isChecked()){
            jenkel = "P";
        }
        String prodi = spProdi.getSelectedItem().toString();

        if (!npm.isEmpty() && !namaDepan.isEmpty() && !namaBelakang.isEmpty() && !username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !noTelepon.isEmpty() && !jenkel.isEmpty() && !prodi.isEmpty()) {

            if (mUserId != 0 && mNpm != null && mNamaDepan != null && mNamaBelakang != null && mUsername != null && mPassword != null && mEmail != null && mNoTelepon != null && mJenkel != null && mProdi != null) {
                Mahasiswa mahasiswa = new Mahasiswa(mUserId, npm, namaDepan, namaBelakang, username, password, email, jenkel, noTelepon, prodi);
                ubah(mahasiswa);
            } else {
                Log.d("lala", npm+namaDepan+namaBelakang+username+password+email+noTelepon+jenkel+prodi);
                Mahasiswa mahasiswa = new Mahasiswa(npm, namaDepan, namaBelakang, username, password, email, jenkel, noTelepon, prodi);
                simpan(mahasiswa);
            }
        } else {
            Toast.makeText(getContext(), "Kolom belum terisi", Toast.LENGTH_LONG).show();
        }
    }

    private void spinnerProdi() {
        List<String> list = new ArrayList<>();
        list.add("Teknik Informatika");
        list.add("Manajemen Bisnis");
        list.add("Manajemen Logistik");
        list.add("Akuntansi");
        list.add("Transportasi");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProdi.setAdapter(dataAdapter);
        if (mProdi != null){
            int spinnerPosition = dataAdapter.getPosition(mProdi);
            spProdi.setSelection(spinnerPosition);
            Log.d("lili", mProdi);
        }
    }

    public interface MahasiswaDialogListener {
        void onFinishMahasiswaDialog();
    }
}
