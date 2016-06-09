package edu.csuft.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import edu.csuft.bean.Cities;
import edu.csuft.bean.City;
import edu.csuft.bean.ResponseBody;
import edu.csuft.bean.SimpleCity;
import edu.csuft.db.SqlDbHelper;
import edu.csuft.interfaces.IActivity;
import edu.csuft.interfaces.IFragment;
import edu.csuft.utils.Logger;
import edu.csuft.utils.WeatherUtil;

public class ManagerCityActivity extends BaseActivity {

    /** 集合，解析json数据后存在此集合中 */
    private ArrayList<City> cityList = null;
    /** 自动填充数据 */
    private AutoCompleteTextView actv_add_city = null;
    /** 添加 */
    private Button btn_add_city = null;
    /** ListView对象，用来显示当前已经关注的城市 */
    private ListView lv_add_city = null;
    /** 用来存储城市名，并可以根据城市名来得到城市编号 */
    private HashMap<String,String> cityMap = null;
    /** 用来在ListView中显示城市 */
    private ArrayList<SimpleCity> simpleCityList = null;
    private SqlDbHelper helper;

    //适配器，后面在删除操作时需要用上
    ArrayAdapter simpleCityAdapter = null;

    private ArrayList<ResponseBody> responseBodyList = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_manager_city;
    }

    @Override
    public void initView() {
        actv_add_city = findView(R.id.actv_add_city);
        btn_add_city = findView(R.id.btn_add_city);
        lv_add_city = findView(R.id.lv_add_city);
    }

    @Override
    public void initData() {
        responseBodyList = (ArrayList<ResponseBody>) getIntent().getSerializableExtra(IActivity.SAVE_DATA);
        helper = new SqlDbHelper(this);

        initListData();
        initAutoTextData();
        initMapSetData();
    }

    private String getCitycode(String city){

        return cityMap.get(city);
    }

    /**
     * 从本地将所有数据读取出来
     */
    private void initListData(){
        simpleCityList = new ArrayList<>();
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        Cursor cursor = readableDatabase.query("weather_city", new String[]{"city", "citycode"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String citycode = cursor.getString(cursor.getColumnIndex("citycode"));
            Logger.i("MANAGER","city-->"+city + "-->"+citycode);
            simpleCityList.add(new SimpleCity(city,citycode));
        }
        cursor.close();
        readableDatabase.close();

        simpleCityAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,simpleCityList);
        lv_add_city.setAdapter(simpleCityAdapter);

        //注册ContextMenu
        registerForContextMenu(lv_add_city);
    }

    /**
     * 设置AutoText中的数据
     */
    private void initAutoTextData(){
        Gson gson = new Gson();
        try {
            InputStream open = getAssets().open("citylist.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(open));

            Cities cities = gson.fromJson(reader, Cities.class);
            cityList = cities.getCities();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_activated_1,cityList);
        actv_add_city.setAdapter(adapter);
        actv_add_city.setThreshold(1);
    }

    /**
     * 初始化HashMap数据，当选中某个城市后，通过在hashmap中匹配，找出citycode
     */
    private void initMapSetData(){
        cityMap = new HashMap<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从cityList中读取每组数据放入map中，之后需要根据城市名来去的citycode
                //放在子线程中是因为太耗时，并且不需要马上使用
                for(int i=0; i<cityList.size(); i++){
                    cityMap.put(cityList.get(i).toString(),cityList.get(i).getArea_id());
                }
            }
        }).start();
    }

    @Override
    public void initListener() {
        btn_add_city.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String city = getCityFromToString(actv_add_city.getText().toString());
            String citycode = getCitycode(actv_add_city.getText().toString());
            /**
             * 向数据库中写入城市名和城市编码
             */
            SQLiteDatabase writableDatabase = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("city",city);
            values.put("citycode",citycode);
            writableDatabase.insert("weather_city",null,values);
            writableDatabase.close();

            inputToShared(city,citycode);

            simpleCityList.add(new SimpleCity(city,citycode));
            simpleCityAdapter.notifyDataSetChanged();

            addCityToResponseBodyList(city,citycode);
        }
    };

    /**
     * 将添加后的城市放入Shared中，在MainActivity中时读取设置
     * @param city
     * @param citycode
     */
    private void inputToShared(String city, String citycode){
        SharedPreferences.Editor editor = getSharedPreferences(IActivity.CURRENT_ITEM,MODE_PRIVATE).edit();
        editor.putString(IActivity.CURRENT_ITEM_CITY,city);
        editor.putString(IActivity.CURRENT_ITEM_CITYCODE,citycode);
        editor.commit();
    }

    /**
     * 从城市字符串中截取最低级城市
     * @param cities 城市字符串
     * @return 需要存储的城市名
     */
    private String getCityFromToString(String cities){
        int index = cities.indexOf(',');
        String city = cities.substring(0,index);

        return city;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_manager_city,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        /*
          获得选中选项
        */
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //选中项的位置
        int position = menuInfo.position;

        switch (item.getItemId()){
            //将某个城市设置为默认显示城市
            case R.id.action_default:
                SharedPreferences.Editor editor = getSharedPreferences(IActivity.DEFAULT_CITY,MODE_PRIVATE).edit();
                //将城市名和城市编码作为标志
                editor.putString(IActivity.CITY,simpleCityList.get(position).getCity());
                editor.putString(IActivity.CITYCODE,simpleCityList.get(position).getCitycode());
                editor.commit();

                Toast.makeText(this,"将"+simpleCityList.get(position).getCity()+"设为默认城市",Toast.LENGTH_SHORT).show();
                break;
            //删除某个城市
            case R.id.action_delete:
                //删除
                deleteFromDbAndList(position);
                break;
        }

        return true;
    }

    /**
     * 将城市从数据库和集合中删除
     * @param position 位置
     */
    private void deleteFromDbAndList(int position){
        SimpleCity city = simpleCityList.get(position);

        //从数据库中删除数据
        WeatherUtil.doDeleteDataFromDatabase(city.getCity(),city.getCitycode());

        //从列表中删除
        simpleCityList.remove(position);
        //更新界面
        simpleCityAdapter.notifyDataSetChanged();

        //从ResponseBodyList中删除，当返回到MaiActivity时，界面就没有改城市数据了
        deleteFromResponseBodyList(city);
    }

    /**
     * 从ResponseBodyList中删除某个城市的数据
     * @param city
     */
    private void deleteFromResponseBodyList(SimpleCity city){
        for(int i=0; i<responseBodyList.size(); i++){
            if(city.getCity().equals(responseBodyList.get(i).getResult().getCity())
                    && city.getCitycode().equals(responseBodyList.get(i).getResult().getCitycode())){
                responseBodyList.remove(i);
                break;
            }
        }
    }

    /**
     * 当增加了城市数据时，便马上在集合中增加数据，当返回到上一个界面时，便可以直接使用
     * @param city
     * @param citycode
     */
    private void addCityToResponseBodyList(final String city, final String citycode){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResponseBody responseBody = WeatherUtil.request(city,citycode);
                responseBodyList.add(responseBody);
            }
        }).start();
    }

    /**
     * 返回
     */
    private void enterMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(IFragment.WEATHERDATA,responseBodyList);
        startActivity(intent);
        //销毁当前界面
        this.finish();
    }

    @Override
    public void onBackPressed() {
        enterMainActivity();
    }
}