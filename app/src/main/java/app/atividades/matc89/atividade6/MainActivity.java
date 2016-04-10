package app.atividades.matc89.atividade6;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String ACAO_ENVIADO = "sms_enviado";
    private static final String ACAO_ENTREGUE = "sms_entregue";

    EditText myNumero;
    EditText myMensagem;
    EnvioSmsReceiver myEnvioSmsReceiver;
    Button btnSendSms;

    public boolean possoEnviarMensagem() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 123);
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 143);
                return false;
            }
        }
        return false;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myNumero = (EditText) findViewById(R.id.editNumero);
        myMensagem = (EditText) findViewById(R.id.editMensagem);

        btnSendSms = (Button) findViewById(R.id.button);
        if (btnSendSms != null) {
            btnSendSms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        enviarSmsClick("5554", "Olá teste");
                }
            });
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PLAYGROUND", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);
            btnSendSms.setEnabled(false);
        } else {
            Log.d("PLAYGROUND", "Permission is granted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("PLAYGROUND", "Permission has been granted");
               // textView.setText("You can send SMS!");
                btnSendSms.setEnabled(true);
            } else {
                Log.d("PLAYGROUND", "Permission has been denied or request cancelled");
                //textView.setText("You can not send SMS!");
                btnSendSms.setEnabled(false);
            }
        }
    }

    protected void onResume() {
        super.onResume();
        myEnvioSmsReceiver = new EnvioSmsReceiver();

        registerReceiver(myEnvioSmsReceiver, new IntentFilter(ACAO_ENVIADO));
        registerReceiver(myEnvioSmsReceiver, new IntentFilter(ACAO_ENTREGUE));

    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(myEnvioSmsReceiver);
    }

   /* @TargetApi(Build.VERSION_CODES.M)
    public void enviarSmsClick(View v) {

        Log.i("enviarSmsClick!", "tes");
        PendingIntent pitEnviado = PendingIntent.getBroadcast(this, 0, new Intent(ACAO_ENVIADO), 0);
        PendingIntent pitEntregue = PendingIntent.getBroadcast(this, 0, new Intent(ACAO_ENTREGUE), 0);
        Log.i("enviarSmsClick!", "tes2");
        SmsManager smsManager = SmsManager.getDefault();
        Log.i("enviarSmsClick!", "tes3");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.SEND_SMS}, 143);
            smsManager.sendTextMessage(myNumero.getText().toString(), null, myMensagem.getText().toString(), null, null);
            return;
        }
        Log.i("enviarSmsClick!", "tes4");
    }*/

    private void enviarSmsClick(String numero, String mensagem) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(numero, null, mensagem, null, null);
    }

    public static class EnvioSmsReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent){
            String mensagem = null;
            String acao = intent.getAction();
            int resultado = getResultCode();

            if (resultado == Activity.RESULT_OK) {
                if (ACAO_ENTREGUE.equals(acao)) {
                    mensagem = "Mensagem enviada com sucesso";
                } else if (ACAO_ENTREGUE.equals(acao)) {
                    mensagem = "Entregue com sucesso.";
                }
            } else {
                if (ACAO_ENVIADO.equals(acao)) {
                    mensagem = "Falha ao enviar: " + resultado;
                } else if (ACAO_ENTREGUE.equals(acao)) {
                    mensagem = "Falha ao entregar: " + resultado;
                }
            }
            Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
        }
    }
}
