package tgo1014.aguaaguaaguamineral.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

import com.pixplicity.easyprefs.library.Prefs;

public class ServicoDeNotificacoes extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String horaInicial = null, horafinal = null, horaAtual = null;

        //Define o formato de hora
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        horaAtual = sdf.format(Calendar.getInstance().getTime());
        horaInicial = Prefs.getString("txtHorarioInicial", "");
        horafinal = Prefs.getString("txtHorarioFinal", "");

        if (java.sql.Date.valueOf(horaAtual).after(java.sql.Date.valueOf(horaInicial)) && java.sql.Date.valueOf(horaAtual).before(java.sql.Date.valueOf(horafinal))){
            Utils.notificacao(context, "Bebeu água?! Tá com sede?!", "Beba água mineral pra ficar legal!");
        }
    }
}
