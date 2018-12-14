package potaskun.enot.myconnectserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {
    public HashMap<String, Object> hm;
    public static String JsonURL;
    private static ArrayList<HashMap<String, Object>> myBooks;
    private static final String FIRST = "firstname";
    private static final String LAST = "lastname";
    public ListView listView;

    /** @param result */
    public void JSONURL(String result){
        try{
            System.out.println("json_test"+ result);
            //создали читателя json объектов и отдали ему строку - result
            JSONObject json  = new JSONObject(result);
            //дальше находим вход в наш json им является ключевое слово data
            JSONArray urls = json.getJSONArray("data");
            System.out.println("test-g"+urls);
            //проходим циклом по всем нашим параметрам
            for(int i=0; i< urls.length(); i++){
                hm = new HashMap<>();
                //читаем что в себе хранит параметр firstname
                hm.put(FIRST, urls.getJSONObject(i).getString("firstName"));
                //читаем что в себе хранит параметр lastname
                hm.put(LAST, urls.getJSONObject(i).getString("lastName"));

                System.out.println("test-g"+hm);
                myBooks.add(hm);
                //дальше добавляем полученные параметры в наш адаптер
                SimpleAdapter adapter = new SimpleAdapter(SecondActivity.this, myBooks, R.layout.list, new String[]{FIRST,LAST, }, new int[]{R.id.text1, R.id.text2});
                //выводим в листвбю
                listView.setAdapter(adapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("log_tag", "Error parsing data " + e.toString());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.url);
        listView = (ListView) findViewById(R.id.list);
        myBooks = new ArrayList<HashMap<String, Object>>();
        //принимаем параметр который мы послылали в manActivity
        Bundle extras = getIntent().getExtras();
        //превращаем в тип стринг для парсинга
        String json = extras.getString(JsonURL);
        //передаем в метод парсинга
        JSONURL(json);
    }
}
