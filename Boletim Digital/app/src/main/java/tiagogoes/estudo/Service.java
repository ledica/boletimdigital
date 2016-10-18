package tiagogoes.estudo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static tiagogoes.estudo.Home.token;

/**
 * Created by Tiago on 27/09/2016.
 */

public class Service extends AsyncTask<String, Void, String> {

    Context context;
    ProgressDialog dialog;
    String tipo;


    Service(Context c) {
        this.context = c;
        dialog = new ProgressDialog(this.context);
    }

    @Override
    protected void onPreExecute() {
        dialog.setTitle("Verificando dados...");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        tipo = params[0];
        //String login_URL = "http://192.168.0.13/android/login.php"; LOCAL
        String login_URL;

        if (tipo.equalsIgnoreCase("Login")) {
            login_URL = "http://appsreview.96.lt/api.php"; // Servidor
            try {
                Log.i("msg", params[1]);
                String user_name = params[1];
                String user_senha = params[2];

                URL url = new URL(login_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //Requisicao
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("cpf", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" + URLEncoder.encode("user_senha", "UTF-8") + "=" + URLEncoder.encode(user_senha, "UTF-8") + "&" + URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Resposta
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String resultado = "";
                String linha;

                while ((linha = bufferedReader.readLine()) != null) {
                    resultado += linha;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return resultado;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (tipo.equalsIgnoreCase("cadastro")) {
            login_URL = "http://appsreview.96.lt/cadastro.php"; // Servidor
            try {
                String nomecad = params[1];
                String sobrenomecad = params[2];
                String usuariocad = params[3];
                String senhacad = params[4];

                URL url = new URL(login_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //Requisicao
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("nomecad", "UTF-8") + "=" + URLEncoder.encode(nomecad, "UTF-8") + "&" + URLEncoder.encode("sobrenomecad", "UTF-8") + "=" + URLEncoder.encode(sobrenomecad, "UTF-8") + "&" +
                        URLEncoder.encode("usuariocad", "UTF-8") + "=" + URLEncoder.encode(usuariocad, "UTF-8") + "&" + URLEncoder.encode("senhacad", "UTF-8") + "=" + URLEncoder.encode(senhacad, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                //Resposta
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String resultado = "";
                String linha;

                while ((linha = bufferedReader.readLine()) != null) {
                    resultado += linha;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return resultado;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }


    @Override
    protected void onPostExecute(String aVoid) {
        dialog.dismiss();
        if (tipo.equalsIgnoreCase("login")) {
            if (aVoid.equalsIgnoreCase("true")) {
                //context.startActivity(new Intent(context, Home.class));
                Toast.makeText(context, "Login OK!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Usuário ou senha incorretos!", Toast.LENGTH_SHORT).show();
            }
        } else if (tipo.equalsIgnoreCase("cadastro")) {
            dialog.setMessage(aVoid);
            dialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
