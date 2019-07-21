package com.cursoandroid.meusconvidados.business;

import android.content.Context;

import com.cursoandroid.meusconvidados.constants.DataBaseConstants;
import com.cursoandroid.meusconvidados.constants.GuestConstants;
import com.cursoandroid.meusconvidados.entities.GuestCount;
import com.cursoandroid.meusconvidados.entities.GuestEntity;
import com.cursoandroid.meusconvidados.repository.GuestRepository;

import java.util.List;

public class GuestBusiness {
    private GuestRepository mGuestRepository;

    public GuestBusiness(Context context) {
        this.mGuestRepository = GuestRepository.getInstance(context);
    }

    public Boolean insert(GuestEntity guestEntity) {
        return this.mGuestRepository.insert(guestEntity);
    }

    public Boolean update(GuestEntity guestEntity) {
        return this.mGuestRepository.update(guestEntity);
    }

    public Boolean remove(int id){
        return this.mGuestRepository.remove(id);
    }

    public GuestEntity load(int id) {
        return this.mGuestRepository.load(id);

    }

    public GuestCount loadDashboard() {
        return this.mGuestRepository.loadDashboard();
    }

    // Lista de convidados
    public List<GuestEntity> getInvited() {
        return this.mGuestRepository.getGuestByQuery("select * from " + DataBaseConstants.GUEST.TABLE_NAME);
    }

    public List<GuestEntity> getPresent() {
        return this.mGuestRepository.getGuestByQuery("select * from " + DataBaseConstants.GUEST.TABLE_NAME +
                " where " + DataBaseConstants.GUEST.COLUMNS.PRESENCE + " = " + GuestConstants.CONFIRMATION.PRESENT);
    }

    public List<GuestEntity> getAbsent() {
        return this.mGuestRepository.getGuestByQuery("select * from " + DataBaseConstants.GUEST.TABLE_NAME +
                " where " + DataBaseConstants.GUEST.COLUMNS.PRESENCE + " = " + GuestConstants.CONFIRMATION.ABSENT);
    }

}
