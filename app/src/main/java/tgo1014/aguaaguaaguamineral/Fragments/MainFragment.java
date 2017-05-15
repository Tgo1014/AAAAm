package tgo1014.aguaaguaaguamineral.Fragments;

import android.app.TimePickerDialog;
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

import com.orhanobut.hawk.Hawk;

import java.text.SimpleDateFormat;
import java.util.Date;

import tgo1014.aguaaguaaguamineral.R;
import tgo1014.aguaaguaaguamineral.Utils.Alarme;
import tgo1014.aguaaguaaguamineral.Utils.Utils;

public class MainFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final int HORA_INICIAL_ID = 1;
    private static final int HORA_FINAL_ID = 2;

    EditText editMinutos;
    TextView txtHoraInicial;
    TextView txtHorarioInicial;
    TextView txtHoraFinal;
    TextView txtHorarioFinal;
    TextView txtRepetir;
    TextView txtMinutos;
    Button btnConfirmar;
    Switch swtGeral;

    LinearLayout linearLayoutInicial;
    LinearLayout linearLayoutFinal;

    Alarme alarme = new Alarme();


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
        swtGeral.setChecked(Hawk.get(getString(R.string.pref_status), false));
        editMinutos.setText(Hawk.get(getString(R.string.pref_minutos), getString(R.string.minutos_padrao)));
        txtHorarioInicial.setText(Hawk.get(getString(R.string.pref_hora_inicial), getString(R.string.hora_padrao)));
        txtHorarioFinal.setText(Hawk.get(getString(R.string.pref_hora_final), getString(R.string.hora_padrao)));

        ativaDesativaElementos(swtGeral.isChecked());

        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

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

                    //Verifica se o hoário incial é depois do horário final
                    if (horarioInicial != null && horarioInicial.before(horarioFinal)) {
                        //Verifica se o intervalo digitado entre os lembretes é um número
                        if (intervalo > 0) {
                            alarme.inicia(getContext(), intervalo);
                            Hawk.put(getString(R.string.pref_minutos), String.valueOf(intervalo));
                            Utils.toastRapido(view.getContext(), String.format(getString(R.string.aviso_ativado), intervalo));
                        }
                    } else {
                        Utils.toastRapido(getContext(), getString(R.string.aviso_hora_inicial_depois_final));
                    }
                } catch (Exception e) {
                    Utils.toastRapido(view.getContext(), getString(R.string.aviso_intervalo_invalido));
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
        Hawk.put(getString(R.string.pref_status), isChecked);

        if (!isChecked) {
            alarme.cancela(getContext());
        }
    }

    public void ativaDesativaElementos(boolean status) {

        if (getView() != null) {
            if (status) {
                getView().findViewById(R.id.linarLayout_switch).getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                (getView().findViewById(R.id.linearLayout_Opcoes)).setVisibility(View.VISIBLE);
            } else {
                getView().findViewById(R.id.linarLayout_switch).getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                (getView().findViewById(R.id.linearLayout_Opcoes)).setVisibility(View.GONE);
            }
        }
    }

    public void showTimePicker(final int id, int hora, int minuto, boolean is24HourViews) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
                        String hr = Utils.arrumarHora(String.valueOf(hora));
                        String min = Utils.arrumarHora(String.valueOf(minuto));
                        String horario = hr + ":" + min;

                        if (id == HORA_INICIAL_ID) {
                            txtHorarioInicial.setText(horario);
                            Hawk.put(getString(R.string.pref_hora_inicial), horario);
                        }
                        if (id == HORA_FINAL_ID) {
                            txtHorarioFinal.setText(horario);
                            Hawk.put(getString(R.string.pref_hora_final), horario);
                        }
                    }
                }, hora, minuto, is24HourViews);
        timePickerDialog.show(); //Show the dialog
    }

}
