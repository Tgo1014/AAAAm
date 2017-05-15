package tgo1014.aguaaguaaguamineral.Utils;

import android.app.Notification;
import android.content.Context;
import android.widget.Toast;

import br.com.goncalves.pugnotification.notification.PugNotification;
import tgo1014.aguaaguaaguamineral.Activities.MainActivity;
import tgo1014.aguaaguaaguamineral.R;

public class Utils {


    static void notificacao(Context context, String titulo, String mensagem){

        PugNotification.with(context)
                .load()
                .title(titulo)
                .message(mensagem)
                .smallIcon(R.drawable.ic_stat_name)
                .largeIcon(R.drawable.ic_stat_name)
                .flags(Notification.DEFAULT_ALL)
                .click(MainActivity.class)
                .simple()
                .build();
    }

    public static void toastRapido (Context c, String text){
        Context context = c.getApplicationContext();
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void toastDevagar(Context c, String text){
        Context context = c.getApplicationContext();
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static String arrumarHora(String s){
        return (String.valueOf(s).length() < 2) ? '0' + String.valueOf(s) : String.valueOf(s);
    }

}
