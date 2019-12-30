package com.divakrishnam.quizroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MahasiswaDialog.MahasiswaDialogListener, MahasiswaAdapter.MahasiswaAdapterListener, View.OnClickListener {

    private ProgressBar pbMahasiswa;
    private TextView tvPesan;
    private FloatingActionButton fabTambah;
    private RecyclerView rvMahasiswa;

    private MahasiswaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private MahasiswaDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbMahasiswa = findViewById(R.id.pb_mahasiswa);
        tvPesan = findViewById(R.id.tv_pesan);
        fabTambah = findViewById(R.id.fab_tambahmahasiswa);
        rvMahasiswa = findViewById(R.id.rv_mahasiswa);

        db = DatabaseClient.getInstance(getApplicationContext()).getMahasiswaDatabase();

        loadMahasiswa();
        fabTambah.setOnClickListener(this);
    }

    private void loadMahasiswa() {
        showLoading(true);

        new AsyncTask<Void, Void, List<Mahasiswa>>(){
            @Override
            protected List<Mahasiswa> doInBackground(Void... voids) {
                List<Mahasiswa> mahasiswas = db.mahasiswaDao().getAll();
                return mahasiswas;
            }

            @Override
            protected void onPostExecute(List<Mahasiswa> mahasiswas) {
                if (mahasiswas != null && !mahasiswas.isEmpty()){
                    showMessage(false, "");
                }else{
                    showMessage(true, "Data tidak ada");
                }
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                rvMahasiswa.setLayoutManager(mLayoutManager);
                mAdapter = new MahasiswaAdapter(getApplicationContext(), mahasiswas, MainActivity.this);
                rvMahasiswa.setAdapter(mAdapter);
            }
        }.execute();

        showLoading(false);
    }

    @Override
    public void onClick(View view) {
        if (view == fabTambah){
            showMahasiswaDialog();
        }
    }

    private void showMahasiswaDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MahasiswaDialog kategoriDialog = MahasiswaDialog.newInstance("Mahasiswa");
        kategoriDialog.show(fragmentManager, "dialog_barang");
    }

    @Override
    public void onFinishMahasiswaDialog() {
        loadMahasiswa();
    }

    @Override
    public void onFinishMahasiswaAdapter() {
        loadMahasiswa();
    }

    private void showMessage(Boolean state, String message){
        if (state) {
            tvPesan.setText(message);
            tvPesan.setVisibility(View.VISIBLE);
        } else {
            tvPesan.setVisibility(View.GONE);
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            pbMahasiswa.setVisibility(View.VISIBLE);
        } else {
            pbMahasiswa.setVisibility(View.GONE);
        }
    }
}
