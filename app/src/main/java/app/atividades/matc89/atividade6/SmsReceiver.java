package app.atividades.matc89.atividade6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by arleyprates on 4/10/16.
 */
public class SmsReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent i) {
        SmsMessage sms = getMessagesFromIntent(i)[0];

        String numero = sms.getOriginatingAddress();
        String mensagem = sms.getMessageBody();

        Toast.makeText(context, "Mensagem recebida de " + numero + " - " + mensagem, Toast.LENGTH_LONG).show();
    }

    public static SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] pdusExtras = (Object[])intent.getSerializableExtra("pdus");

        SmsMessage[] messages = new SmsMessage[pdusExtras.length];

        for (int i = 0; i < pdusExtras.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[])pdusExtras[i]);
        }
        return messages;
    }
}
