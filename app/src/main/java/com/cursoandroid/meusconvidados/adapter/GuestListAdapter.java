package com.cursoandroid.meusconvidados.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cursoandroid.meusconvidados.R;
import com.cursoandroid.meusconvidados.entities.GuestEntity;
import com.cursoandroid.meusconvidados.listener.OnGuestListenerInteractionListener;
import com.cursoandroid.meusconvidados.viewholder.GuestViewHolder;

import java.util.List;

public class GuestListAdapter extends RecyclerView.Adapter<GuestViewHolder> {

    private List<GuestEntity> mGuestEntityList;
    private OnGuestListenerInteractionListener mOnGuestListenerInteractionListener;

    public GuestListAdapter(List<GuestEntity> guestEntityList, OnGuestListenerInteractionListener listener) {
        this.mGuestEntityList = guestEntityList;
        this.mOnGuestListenerInteractionListener = listener;

    }


    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View carView = layoutInflater.inflate(R.layout.row_guest_list, parent, false);

        return new GuestViewHolder(carView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position) {
        GuestEntity guestEntity = this.mGuestEntityList.get(position);
        holder.bindData(guestEntity, mOnGuestListenerInteractionListener);
    }

    @Override
    public int getItemCount() {
        return this.mGuestEntityList.size();
    }
}
