package tgo1014.aguaaguaaguamineral.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import tgo1014.aguaaguaaguamineral.R;

public class Alarme {

    public void inicia (Context c, int intervalo){

        //Recebe o contexto da aplicação em execução
        Context context = c.getApplicationContext();

        //Instancia o intent que será agendado
        Intent alarmIntent = new Intent(context, ServicoDeNotificacoes.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

        //Pega o horário atual
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        //Instancia serviço de alarmes
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //Cancela qualquer alarme já existente
        alarmManager.cancel(pendingIntent);

        //Agenda a notificação pelo tempo determinado pelo usuário
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + intervalo * 60 * 1000, intervalo * 60 * 1000, pendingIntent);

    }

    public void cancela(Context context){

        Intent intent = new Intent(context, ServicoDeNotificacoes.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(sender);

        //Informa cancelamento
        Utils.toastRapido(context,context.getString(R.string.aviso_lembrete_canc));
    }
}