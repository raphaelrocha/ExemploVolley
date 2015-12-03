package com.exemplovolley;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.exemplovolley.conn.ServerInfo;
import com.exemplovolley.conn.VolleyConnection;
import com.exemplovolley.interfaces.CustomVolleyCallbackInterface;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;


public class MainActivity extends BaseActivity implements CustomVolleyCallbackInterface {

    private VolleyConnection mVolleyConnection;
    private EditText mEdit;
    private TextView mTvResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        AQUI VOCE INSTANCIA A CLASSE DE CONEXAO.
        CADA ACTIVITY DEVE TER SUA INSTANCIA
        * */
        mVolleyConnection = new VolleyConnection(this);

        mEdit = (EditText) findViewById(R.id.edt_mensagem);
        mTvResponse = (TextView) findViewById(R.id.tv_response);

        Button bt_object = (Button) findViewById(R.id.bt_object);
        Button bt_array = (Button) findViewById(R.id.bt_array);

        bt_object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String dados = mensagem.getText().toString().trim();

                HashMap<String,String> params = new HashMap<String, String>();
                params.put("method","object");
                params.put("data",mEdit.getText().toString().trim());
                /*
                AQUI VOCÊ FAZ A CHAMADA PARA O SERVIDOR
                */
                mVolleyConnection.callServerApiByJsonObjectRequest(ServerInfo.SERVER_URL, params, "MAIN_TAG");
            }
        });

        bt_array.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String dados = mensagem.getText().toString().trim();

                HashMap<String,String> params = new HashMap<String, String>();
                params.put("method","array");
                params.put("data",mEdit.getText().toString().trim());
                /*
                AQUI VOCÊ FAZ A CHAMADA PARA O SERVIDOR
                */
                mVolleyConnection.callServerApiByJsonArrayRequest(ServerInfo.SERVER_URL, params, "main");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //METODOS DE RETORNO
    @Override
    public void deliveryResponse(JSONObject response, String flag) {
        Log.i("Script", "Object Response: " + response.toString());
        mTvResponse.setText("JsonObject:\n"+response.toString());
        //longAlert("Object Resposta: " + response.toString());
    }

    @Override
    public void deliveryResponse(JSONArray response, String flag){
        Log.i("Script", "Array Response: " + response.toString());
        //longAlert("Array Resposta: " + response.toString());
        mTvResponse.setText("JsonArray:\n"+response.toString());
    }

    @Override
    public void deliveryError(VolleyError error, String flag){
        Log.i("Script", "Erro: " + error);
        longAlert("Erro: " + error);
    }

    @Override
    public void onStop(){
        super.onStop();
        //REMOVE A REQUISINÇÃO DA FILA SE A ACTIVITY FOR FECHADA
        mVolleyConnection.canceRequest();
    }
}
