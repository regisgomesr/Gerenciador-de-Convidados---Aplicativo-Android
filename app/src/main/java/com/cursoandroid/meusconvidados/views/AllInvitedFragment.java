package com.cursoandroid.meusconvidados.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cursoandroid.meusconvidados.R;
import com.cursoandroid.meusconvidados.adapter.GuestListAdapter;
import com.cursoandroid.meusconvidados.business.GuestBusiness;
import com.cursoandroid.meusconvidados.constants.GuestConstants;
import com.cursoandroid.meusconvidados.entities.GuestCount;
import com.cursoandroid.meusconvidados.entities.GuestEntity;
import com.cursoandroid.meusconvidados.listener.OnGuestListenerInteractionListener;

import java.util.List;

public class AllInvitedFragment extends Fragment {

    private ViewHolder mViewHolder = new ViewHolder();
    private GuestBusiness mGuestBusiness;
    private OnGuestListenerInteractionListener mOnGuestListenerInteractionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view = inflater.inflate(R.layout.fragment_all_invited, container, false);

        Context context = view.getContext();

     // obter a recycler
     this.mViewHolder.mRecyclerAllInvited = (RecyclerView) view.findViewById(R.id.recycler_all_invited);
     this.mViewHolder.mTextPresentCount = (TextView) view.findViewById(R.id.text_present_count);
     this.mViewHolder.mTextAbsentCount = (TextView) view.findViewById(R.id.text_absent_count);
     this.mViewHolder.mTextAllInvitedCount = (TextView) view.findViewById(R.id.text_all_invited);

     this.mGuestBusiness = new GuestBusiness(context);


        this.mOnGuestListenerInteractionListener = new OnGuestListenerInteractionListener() {
            @Override
            public void onListClick(int id) {
                // Abrir activity de formulario
                Bundle bundle = new Bundle();
                bundle.putInt(GuestConstants.BundleConstants.GUEST_ID, id);

                Intent intent = new Intent(getContext(), GuestFormActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);

            }

            @Override
            public void onDeleteClick(int id) {
                mGuestBusiness.remove(id);

                Toast.makeText(getContext(), R.string.convidado_removido_sucesso, Toast.LENGTH_LONG).show();

                loadDashboard();
                loadGuests();

            }
        };



     // Definir um layout
     this.mViewHolder.mRecyclerAllInvited.setLayoutManager(new LinearLayoutManager(context));

     return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        this.loadDashboard();
        this.loadGuests();

    }

    private void loadGuests() {

        List<GuestEntity> guestEntityList = this.mGuestBusiness.getInvited();

        // Definir um adapter
        GuestListAdapter guestListAdapter = new GuestListAdapter(guestEntityList, this.mOnGuestListenerInteractionListener);
        this.mViewHolder.mRecyclerAllInvited.setAdapter(guestListAdapter);

        guestListAdapter.notifyDataSetChanged();

    }

    private void loadDashboard() {

        GuestCount guestCount = this.mGuestBusiness.loadDashboard();

        this.mViewHolder.mTextPresentCount.setText(String.valueOf(guestCount.getPresentCount()));
        this.mViewHolder.mTextAbsentCount.setText(String.valueOf(guestCount.getAbsentCount()));
        this.mViewHolder.mTextAllInvitedCount.setText(String.valueOf(guestCount.getAllInvitedCount()));
    }

    private static class ViewHolder {
        RecyclerView mRecyclerAllInvited;
        TextView mTextPresentCount;
        TextView mTextAbsentCount;
        TextView mTextAllInvitedCount;

    }

}
