package tgo1014.aguaaguaaguamineral.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.hawk.Hawk;

import java.util.Calendar;

import tgo1014.aguaaguaaguamineral.R;

public class ServicoDeNotificacoes extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String horaInicial;
        String horaFinal;
        String horaAtual;

        Calendar cal = Calendar.getInstance();

        //Remove 3 horas devico a TimeZone do Brasil
        //TODO utilizar horário do sistema independente do fuso
        horaAtual = Utils.arrumarHora(String.valueOf((cal.get(Calendar.HOUR_OF_DAY) - 3))) + ":" + Utils.arrumarHora(String.valueOf(cal.get(Calendar.MINUTE)));
        horaInicial = Hawk.get(context.getString(R.string.pref_hora_inicial), "");
        horaFinal = Hawk.get(context.getString(R.string.pref_hora_final), "");

        if (horaFinal != null && horaInicial != null) {
            //Verifica se o horário atual está dentro do intervalo determinado pelo usuário
            if (Integer.parseInt(horaAtual.replace(":", "")) < (Integer.parseInt(horaFinal.replace(":", ""))) && (Integer.parseInt(horaAtual.replace(":", ""))) > (Integer.parseInt(horaInicial.replace(":", "")))) {
                Utils.notificacao(context, context.getString(R.string.titulo_notificacao), context.getString(R.string.corpo_notificacao));
            }
        }
    }
}
