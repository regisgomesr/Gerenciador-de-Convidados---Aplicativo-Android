package com.cursoandroid.meusconvidados.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cursoandroid.meusconvidados.R;
import com.cursoandroid.meusconvidados.business.GuestBusiness;
import com.cursoandroid.meusconvidados.constants.GuestConstants;
import com.cursoandroid.meusconvidados.entities.GuestEntity;

public class GuestFormActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private GuestBusiness mGuestBusiness;
    private int mGuestID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_form);

        this.mViewHolder.mEditName = (EditText) this.findViewById(R.id.edit_name);
        this.mViewHolder.mEditDocument = (EditText) this.findViewById(R.id.edit_document);
        this.mViewHolder.mRadioNotConfirmed = (RadioButton) this.findViewById(R.id.radio_not_confirmed);
        this.mViewHolder.mRadioPresent = (RadioButton) this.findViewById(R.id.radio_present);
        this.mViewHolder.mRadioAbsent = (RadioButton) this.findViewById(R.id.radio_absent);
        this.mViewHolder.mButtonSave = (Button) this.findViewById(R.id.button_save);

        this.mGuestBusiness = new GuestBusiness(this);

        this.setListeners();

        this.loadDataFromActivity();
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.button_save) {
            this.handleSave();
        }
    }

    private void setListeners() {
        this.mViewHolder.mButtonSave.setOnClickListener(this);
    }


    private void handleSave(){

        if (!this.validateSave()) {
            return;

        }

        GuestEntity guestEntity = new GuestEntity();
        guestEntity.setName(this.mViewHolder.mEditName.getText().toString());
        guestEntity.setDocument(this.mViewHolder.mEditDocument.getText().toString());

        if (this.mViewHolder.mRadioNotConfirmed.isChecked()) {
            guestEntity.setConfirmed(GuestConstants.CONFIRMATION.NOT_CONFIRMED);
        } else if (this.mViewHolder.mRadioPresent.isChecked()) {
            guestEntity.setConfirmed(GuestConstants.CONFIRMATION.PRESENT);
        } else {
            guestEntity.setConfirmed(GuestConstants.CONFIRMATION.ABSENT);
        }

        if (this.mGuestID == 0){

            // Salva entidade Convidado
            if (this.mGuestBusiness.insert(guestEntity)) {
                Toast.makeText(this, getString(R.string.salvo_com_sucesso), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getString(R.string.erro_ao_salvar), Toast.LENGTH_LONG).show();
            }

        } else {

            // Id diferente de 0. Significa que veio um convidado e vamos atualizar

            guestEntity.setId(this.mGuestID);
            if (this.mGuestBusiness.update(guestEntity)) {
                Toast.makeText(this, getString(R.string.salvo_com_sucesso), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getString(R.string.erro_ao_salvar), Toast.LENGTH_LONG).show();
            }

        }



        finish();
    }

    // Metodo que verifica se tem algum bundle passado, e se existir carrega para edição do convidado
    private void loadDataFromActivity() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
           this.mGuestID = bundle.getInt(GuestConstants.BundleConstants.GUEST_ID);

           GuestEntity guestEntity = this.mGuestBusiness.load(this.mGuestID);
           this.mViewHolder.mEditName.setText(guestEntity.getName());
           this.mViewHolder.mEditDocument.setText(guestEntity.getDocument());

           if (guestEntity.getConfirmed() == GuestConstants.CONFIRMATION.PRESENT){
               this.mViewHolder.mRadioPresent.setChecked(true);
           } else if (guestEntity.getConfirmed() == GuestConstants.CONFIRMATION.ABSENT) {
               this.mViewHolder.mRadioAbsent.setChecked(true);
           } else {
               this.mViewHolder.mRadioNotConfirmed.setChecked(true);
           }

        }

    }

    private Boolean validateSave() {

        if (this.mViewHolder.mEditName.getText().toString().equals("")) {
            this.mViewHolder.mEditName.setError(getString(R.string.nome_obrigatorio));
            return false;
        }

        return true;
    }

    private static class ViewHolder {

        EditText mEditName;
        EditText mEditDocument;
        RadioButton mRadioNotConfirmed;
        RadioButton mRadioPresent;
        RadioButton mRadioAbsent;
        Button mButtonSave;
    }
}
