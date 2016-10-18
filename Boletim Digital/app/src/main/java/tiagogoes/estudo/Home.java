package tiagogoes.estudo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.onesignal.OneSignal;

import org.json.JSONObject;

public class Home extends AppCompatActivity {

    EditText cpf, senha;
    static final String token = "hXangk0TQA";
    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!networkConnectivity(this)) {
            Toast.makeText(this, "Verifique sua conexão com a internet!", Toast.LENGTH_LONG).show();
        }

        OneSignal.startInit(this).setNotificationOpenedHandler(new Home.ExampleNotificationOpenedHandler()).init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logar(View v) {

        if (networkConnectivity(this)) {
            cpf = (EditText) findViewById(R.id.txtCpf);
            senha = (EditText) findViewById(R.id.txtSenha);

            String userNome = cpf.getText().toString();
            String userSenha = senha.getText().toString();
            tipo = "login";

            if (userNome.equals("") || userSenha.equals("")) {
                exibeMsg("Informe o usuário e a senha!");
            } else {
                Service service = new Service(this);
                service.execute(tipo, userNome, userSenha);
            }
        } else {
            exibeMsg("Verifique sua conexão com a internet");
        }
    }

    public void exibeMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    //Metodo que verifica a conexao com a internet
    private boolean networkConnectivity(Context c) {
        boolean status = false;
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            status = true;
        }
        return status;
    }


    // This fires when a notification is opened by tapping on it or one is received while the app is running.
    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
            try {
                if (additionalData != null) {
                    if (additionalData.has("actionSelected"))
                        Log.d("OneSignalExample", "OneSignal notification button with id " + additionalData.getString("actionSelected") + " pressed");

                    Log.d("OneSignalExample", "Full additionalData:\n" + additionalData.toString());
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
