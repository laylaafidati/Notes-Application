package com.example.mcs_midexam2101701563_laylanurulafidati;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<Notes> notesList;
    Context ctx;
    ArrayList<Integer> array_id;

    public NotesAdapter(List<Notes> notesList, Context ctx, ArrayList<Integer> array_id) {
        this.notesList = notesList;
        this.ctx = ctx;
        this.array_id = array_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View notesView = inflater.inflate(R.layout.rv_template_list, parent, false);
        return new ViewHolder(notesView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notes notes = notesList.get(position);
        int id = array_id.get(position);
        holder.tv_titleShow.setText(notes.title);
        holder.tv_date.setText(notes.date);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tv_titleShow, tv_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_titleShow = (TextView)itemView.findViewById(R.id.tv_titleShow);
            tv_date = (TextView)itemView.findViewById(R.id.tv_dateShow);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = (int)itemView.getTag();
            DBHelper dbHelper = new DBHelper(v.getContext(), DBHelper.TABLE_NAME_NOTES, null, DBHelper.DB_VERSION);
            Intent intent = new Intent(v.getContext(), ThirdActivity.class);
            intent.putExtra("id", id);
            Toast.makeText(v.getContext(), ""+id, Toast.LENGTH_SHORT).show();
            // v.getContext().startActivity(intent) error when running => android.util.AndroidRuntimeException: Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
            // I solve this problem by following the tutorial on Youtube:
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            int id = (int)itemView.getTag();
            DBHelper dbHelper = new DBHelper(ctx,DBHelper.TABLE_NAME_NOTES, null, DBHelper.DB_VERSION);
            dbHelper.dbDelete(id);

            final int position = getAdapterPosition();
            Toast.makeText(v.getContext(), "Notes deleted", Toast.LENGTH_LONG).show();
            notesList.remove(position);
            notifyItemRemoved(position);

//            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(v.getContext());
//            deleteDialog.setTitle("Delete")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            notesList.remove(position);
//                            notifyItemRemoved(position);
//                        }
//                    })
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//            deleteDialog.create().show();

            return true;
        }
    }
}
