package tgo1014.aguaaguaaguamineral.Fragments;

import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.pixplicity.easyprefs.library.Prefs;

import java.text.ParseException;
import java.util.Date;

import tgo1014.aguaaguaaguamineral.R;
import tgo1014.aguaaguaaguamineral.Utils.Alarme;
import tgo1014.aguaaguaaguamineral.Utils.Utils;

public class MainFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private static final int HORA_INICIAL_ID = 1;
    private static final int HORA_FINAL_ID = 2;

    EditText editMinutos;
    TextView txtHoraInicial;
    TextView txtHorarioInicial;
    TextView txtHoraFinal;
    TextView txtHorarioFinal;
    TextView txtRepetir;
    TextView txtMinutos;
    Button   btnConfirmar;
    Switch   swtGeral;

    LinearLayout linearLayoutInicial;
    LinearLayout linearLayoutFinal;

    Alarme   alarme = new Alarme();


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Configura view
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        //Configura elementos da tela
        txtHoraInicial = (TextView) v.findViewById(R.id.txtHoraInicial);
        txtHorarioInicial = (TextView) v.findViewById(R.id.txtHorarioInicial);
        txtHoraFinal = (TextView) v.findViewById(R.id.txtHoraFinal);
        txtHorarioFinal = (TextView) v.findViewById(R.id.txtHorarioFinal);
        txtRepetir = (TextView) v.findViewById(R.id.txtRepetir);
        txtMinutos = (TextView) v.findViewById(R.id.txtMinutos);
        editMinutos = (EditText) v.findViewById(R.id.editMinutos);
        btnConfirmar = (Button) v.findViewById(R.id.btnConfirmar);
        swtGeral = (Switch) v.findViewById(R.id.swtGeral);
        linearLayoutFinal = (LinearLayout) v.findViewById(R.id.LinearHoraFinal);
        linearLayoutInicial = (LinearLayout) v.findViewById(R.id.LinearHoraInicial);

        //Seta listeners
        btnConfirmar.setOnClickListener(this);
        swtGeral.setOnCheckedChangeListener(this);
        linearLayoutFinal.setOnClickListener(this);
        linearLayoutInicial.setOnClickListener(this);

        //Recupera status
        swtGeral.setChecked(Prefs.getBoolean("swtGeral", false));
        editMinutos.setText(Prefs.getString("editMinutos", "30"));
        txtHorarioInicial.setText(Prefs.getString("txtHorarioInicial","00:00"));
        txtHorarioFinal.setText(Prefs.getString("txtHorarioFinal","00:00"));

        ativaDesativaElementos(swtGeral.isChecked());

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnConfirmar:
                //intervalo de repetição do aviso
                int intervalo = Integer.parseInt(editMinutos.getText().toString());
                //Horarios
                Date horarioInicial = null;
                Date horarioFinal = null;

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                try {
                    horarioInicial = sdf.parse(txtHorarioInicial.getText().toString());
                    horarioFinal = sdf.parse(txtHorarioFinal.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try{
                    //Verifica se o hoário incial é depois do horário final
                    if (horarioInicial != null && horarioInicial.before(horarioFinal)){
                        //Verifica se o intervalo digitado entre os lembretes é um número
                        if (intervalo > 0){
                            alarme.inicia(getContext(), intervalo);
                            Prefs.putString("editMinutos", String.valueOf(intervalo));
                            Utils.toastRapido(view.getContext(), "Agora você tá legal a cada " + intervalo + " minutos!");
                        }
                    }  else {
                        Utils.toastRapido(getContext(), "Horario inicial depois do horário final");
                    }
                }catch (Exception e){
                    Utils.toastRapido(view.getContext(), "Intervalo inválido!");
                }

                break;
            case R.id.LinearHoraInicial:
                showTimePicker(HORA_INICIAL_ID, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);
                break;

            case R.id.LinearHoraFinal:
                showTimePicker(HORA_FINAL_ID, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);
                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        ativaDesativaElementos(isChecked);
        Prefs.putBoolean("swtGeral", isChecked);

        if (!isChecked){
            alarme.cancela(getContext());
        }
    }

    public void ativaDesativaElementos(boolean status){
            editMinutos.setEnabled(status);
            txtHoraInicial.setEnabled(status);
            txtHorarioInicial.setEnabled(status);
            txtHoraFinal.setEnabled(status);
            txtHorarioFinal.setEnabled(status);
            txtRepetir.setEnabled(status);
            txtMinutos.setEnabled(status);
            btnConfirmar.setEnabled(status);
    }

    public void showTimePicker(final int id, int hora, int minuto, boolean is24HourViews) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hora, int minuto) {

                        String hr = (String.valueOf(hora).length() < 2) ? '0' + String.valueOf(hora) : String.valueOf(hora);
                        String min = (String.valueOf(minuto).length() < 2) ? '0' + String.valueOf(minuto) : String.valueOf(minuto);
                        String horario = hr + ":" + min;

                        if (id == HORA_INICIAL_ID){
                            txtHorarioInicial.setText(horario);
                            Prefs.putString("txtHorarioInicial", horario);
                        }
                        if (id == HORA_FINAL_ID){
                            txtHorarioFinal.setText(horario);
                            Prefs.putString("txtHorarioFinal", horario);
                        }
                    }
                }, hora, minuto,is24HourViews);
        timePickerDialog.show(); //Show the dialog
    }

}
