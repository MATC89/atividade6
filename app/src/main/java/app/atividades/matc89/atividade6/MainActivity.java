package app.atividades.matc89.atividade6;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText myNumero;
    EditText myMensagem;
    Button btnSendSms;

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
                    enviarSmsClick();
                }
            });
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PLAYGROUND-2", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);
            btnSendSms.setEnabled(false);
        } else {
            Log.d("PLAYGROUND-3", "Permission is granted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("PLAYGROUND!!", "Permission has been granted");
               // textView.setText("SMS enviada!");
                btnSendSms.setEnabled(true);
            } else {
                Log.d("PLAYGROUND@@", "Permission has been denied or request cancelled");
               // textView.setText("SMS não enviada!");
                btnSendSms.setEnabled(false);
            }
        }
    }

    public void enviarSmsClick() {

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(myNumero.getText().toString(), null, myMensagem.getText().toString(), null, null);

    }

}
