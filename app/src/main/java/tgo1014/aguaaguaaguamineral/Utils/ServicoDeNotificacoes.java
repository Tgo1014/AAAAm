package tgo1014.aguaaguaaguamineral.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.util.Log;

import com.pixplicity.easyprefs.library.Prefs;

public class ServicoDeNotificacoes extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String horaInicial;
        String horaFinal;
        String horaAtual;

        Calendar cal = Calendar.getInstance();

        //Remove 3 horas devico a TimeZone do Brasil
        horaAtual = Utils.arrumarHora(String.valueOf((cal.get(Calendar.HOUR_OF_DAY) - 3))) + ":" + Utils.arrumarHora(String.valueOf(cal.get(Calendar.MINUTE)));
        horaInicial = Prefs.getString("txtHorarioInicial", "");
        horaFinal = Prefs.getString("txtHorarioFinal", "");

        if (horaFinal != null && horaInicial != null){
            //Verifica se o horário atual está dentro do intervalo determinado pelo usuário
            if (Integer.parseInt(horaAtual.replace(":", "")) < (Integer.parseInt(horaFinal.replace(":", ""))) && (Integer.parseInt(horaAtual.replace(":", ""))) > (Integer.parseInt(horaInicial.replace(":", "")))) {
                Utils.notificacao(context, "Bebeu água?! Tá com sede?!", "Beba água mineral pra ficar legal!");
                Log.i(context.getPackageName(), "Hora Atual: " + horaAtual);
                Log.i(context.getPackageName(), "Hora Inicial: " + horaInicial);
                Log.i(context.getPackageName(), "Hora Final: " + horaFinal);
            } else {
                Log.i(context.getPackageName(), "Lembrete fora do horário selecionado.");
            }
        }
    }
}
