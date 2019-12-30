package com.divakrishnam.quizroom;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder>{

    private List<Mahasiswa> mList;
    private Context mContext;
    private Activity mActivity;

    public MahasiswaAdapter(Context context, List<Mahasiswa> list, Activity activity){
        mList = list;
        mContext = context;
        mActivity = activity;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa, parent, false);
        MahasiswaViewHolder mViewHolder = new MahasiswaViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, final int position) {
        final MahasiswaDatabase db = DatabaseClient.getInstance(mContext).getMahasiswaDatabase();

        //Mahasiswa mahasiswa = new Mahasiswa();

        holder.tvUserId.setText(" : "+mList.get(position).getUserId());
        holder.tvNpm.setText(" : "+mList.get(position).getNpm());
        holder.tvNama.setText(" : "+mList.get(position).getNamaDepan()+" "+mList.get(position).getNamaBelakang());
        holder.tvUsername.setText(" : "+mList.get(position).getUsername());
        holder.tvEmail.setText(" : "+mList.get(position).getEmail());
        holder.tvJenkel.setText(" : "+mList.get(position).getJenisKelamin());
        holder.tvNoTelp.setText(" : "+mList.get(position).getNoTelepon());
        holder.tvProdi.setText(" : "+mList.get(position).getProgramStudi());

        holder.btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity)mActivity).getSupportFragmentManager();
                MahasiswaDialog mahasiswaDialog = MahasiswaDialog.newInstance("Mahasiswa");
                Bundle bundle=new Bundle();
                bundle.putInt(MahasiswaDialog.USER_ID, mList.get(position).getUserId());
                bundle.putString(MahasiswaDialog.NPM, mList.get(position).getNpm());
                bundle.putString(MahasiswaDialog.NAMA_DEPAN, mList.get(position).getNamaDepan());
                bundle.putString(MahasiswaDialog.NAMA_BELAKANG, mList.get(position).getNamaBelakang());
                bundle.putString(MahasiswaDialog.USERNAME, mList.get(position).getUsername());
                bundle.putString(MahasiswaDialog.PASSWORD, mList.get(position).getPassword());
                bundle.putString(MahasiswaDialog.EMAIL, mList.get(position).getEmail());
                bundle.putString(MahasiswaDialog.NO_TELEPON, mList.get(position).getNoTelepon());
                bundle.putString(MahasiswaDialog.JENIS_KELAMIN, mList.get(position).getJenisKelamin());
                bundle.putString(MahasiswaDialog.PRODI, mList.get(position).getProgramStudi());
                mahasiswaDialog.setArguments(bundle);
                mahasiswaDialog.show(fragmentManager, "dialog_mahasiswa");
            }
        });

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, Integer>(){
                    @Override
                    protected Integer doInBackground(Void... voids) {
                        int status = db.mahasiswaDao().delete(mList.get(position));
                        return status;
                    }

                    @Override
                    protected void onPostExecute(Integer status) {
                        Log.d("status registrasi", ""+status);
                        if(status > 0){
                            Toast.makeText(mContext, "Data terhapus", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, "Data gagal terhapus", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();

                MahasiswaAdapterListener listener = (MahasiswaAdapterListener) mActivity;
                listener.onFinishMahasiswaAdapter();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MahasiswaViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUserId, tvNama, tvNpm, tvUsername, tvEmail, tvJenkel, tvNoTelp, tvProdi;
        private Button btnUbah, btnHapus;

        public MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUserId = itemView.findViewById(R.id.tv_userid);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvNpm = itemView.findViewById(R.id.tv_npm);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvJenkel = itemView.findViewById(R.id.tv_jenkel);
            tvNoTelp = itemView.findViewById(R.id.tv_notelepon);
            tvProdi = itemView.findViewById(R.id.tv_prodi);
            btnUbah = itemView.findViewById(R.id.btn_ubah);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
        }
    }

    public interface MahasiswaAdapterListener {
        void onFinishMahasiswaAdapter();
    }
}
