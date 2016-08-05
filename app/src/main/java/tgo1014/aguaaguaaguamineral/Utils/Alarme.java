package tgo1014.aguaaguaaguamineral.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class Alarme {

    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    public void inicia (Context c, int intervalo){

        //Recebe o contexto da aplicação em execução
        Context context = c.getApplicationContext();

        //Instancia o intent que será agendado
        Intent alarmIntent = new Intent(context, ServicoDeNotificacoes.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

        //Pega o horário atual
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        //Instancia serviço de alarmes
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //Cancela qualquer alarme já existente
        alarmManager.cancel(pendingIntent);

        //Agenda a notificação pelo tempo determinado pelo usuário
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + intervalo * 60 * 1000, intervalo * 60 * 1000, pendingIntent);

    }

    public void cancela(Context c){

        Intent intent = new Intent(c, ServicoDeNotificacoes.class);
        PendingIntent sender = PendingIntent.getBroadcast(c, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(sender);

        //Informa cancelamento
        Utils.toastRapido(c,"Lembrete cancelado");
    }

}