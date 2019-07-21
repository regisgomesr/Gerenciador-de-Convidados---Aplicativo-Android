package com.cursoandroid.meusconvidados.viewholder;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cursoandroid.meusconvidados.R;
import com.cursoandroid.meusconvidados.entities.GuestEntity;
import com.cursoandroid.meusconvidados.listener.OnGuestListenerInteractionListener;

public class GuestViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextName;
    private ImageView mImageRemove;
    private Context mContext;

    public GuestViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        this.mTextName = (TextView) itemView.findViewById(R.id.text_name);
        this.mImageRemove = (ImageView) itemView.findViewById(R.id.image_remove);
        this.mContext = context;
    }


    // Onde trata os eventos
    public void bindData(final GuestEntity guestEntity, final OnGuestListenerInteractionListener listener) {

        this.mTextName.setText(guestEntity.getName());

        this.mTextName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onListClick(guestEntity.getId());
            }
        });

        this.mImageRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.remocao_convidado)
                        .setMessage(R.string.deseja_remover)
                        .setIcon(R.drawable.remove)
                        .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listener.onDeleteClick(guestEntity.getId());
                            }
                        })
                        .setNeutralButton(R.string.nao, null)
                        .show();
            }
        });

        this.mTextName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.remocao_convidado)
                        .setMessage(R.string.deseja_remover)
                        .setIcon(R.drawable.remove)
                        .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listener.onDeleteClick(guestEntity.getId());
                            }
                        })
                        .setNeutralButton(R.string.nao, null)
                        .show();

                return true;
            }
        });
    }
}
