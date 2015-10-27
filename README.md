# ExemploVolley
Projeto básico com a implementação de conexão usando Volley.

Pacotes dentro do projeto
Conn - Neste pacote estão as classes de conexão
ServerInfo - Classe estática que possui uma String com a URL usada para conexão com o servidor
 VolleyConnectionQueue - Classe singleton que implementa a fila de requisições que foram realizadas em todo o conetxto da Aplicação.
 VolleyConnection - Classe que implementa os métodos que serão utilizados pela Activity para que a mesma possa realizar a conexão.

Interface - Neste pacote está a interface criada com os métodos "response" que devem ser implementados pela activity q fará o uso dos métodos "request".
 CustomVolleyCallbackInterface = É a interface que possui os métodos de retorno. Toda Activity que precisar usar a classe VolleyConnectio, precisará implementar esta interface com os métodos de retorno.
 São 3 métodos
 1 - deliveryError - Recebe o retorno em caso de erro na requisição
 2 - deliveryResponse - Este método possui sobrecarga, para retorno no formado JSONArray ou JSONObject. Ele recebe o retorno em caso de sucesso na requisição
 
Services - Aqui estão as classes de suporte que resolvem o problema que impede a app receber um response qndo trata-se de um ArrayRequest ou ObjectRequest
 CustomJsonArrayRequest - Usada para requisições JSONArray
 CustomJsonObjectRequest - Usada para requisições JSONObject
 
Activitys
  BaseActivity
  MainActivity
 
# Como usar
  Nesse exemplo, usarei uma Activty base que será extendida por todas as demais Activitys, ela se chamará BaseActivity
  
  No onCrete da BaseActivity, garanta que a fila será iniciada.
  
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VolleyConnectionQueue.getINSTANCE().startQueue(this);
    }
    

  Extenda a "MainActivity" de "BaseActivity" e implemente a interface "CustomVolleyCallbackInterface"
  
    "public class MainActivity extends BaseActivity implements CustomVolleyCallbackInterface {...."
    
  
  Na MainActivity, crie uma váriável global do tipo "VolleyConnection"
    "private VolleyConnection mVolleyConnection;"
    
  No onCreate da MainActivity, instancie o objeto "VolleyConnection"
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVolleyConnection = new VolleyConnection(this);
        .
        .
        .
    
  Pronto, agora você pode fazer a chamada para o seu servidor a qualquer momento da seguinte maneira:
  
     mVolleyConnection.callServerApiByJsonObjectRequest(<SERVER_URL>, <STRING_COM_O_METODO>, <STRING_DE_DADOS_NO_FORMATO_JSON>, <FLAG_PARA_CONTROLE_LOCAL>);
     ou
     mVolleyConnection.callServerApiByJsonArrayRequest(<SERVER_URL>, <STRING_COM_O_METODO>, <STRING_DE_DADOS_NO_FORMATO_JSON>, <FLAG_PARA_CONTROLE_LOCAL>);
     
     Lembre-se de tratar o retorno no método correto de acordo com o tipo de requisição.
     
     callServerApiByJsonObjectRequest >> deliveryResponse(JSONObject response, String flag)
     callServerApiByJsonArrayRequest >> deliveryResponse(JSONArray response, String flag)
     

  
