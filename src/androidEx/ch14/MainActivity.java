package androidEx.ch14;

import java.util.Date;
import androidEx.ch14.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class MainActivity extends Activity {
    // 日期相關變數
	private int thisYear, thisMonth, thisDate;
	private TextView txtDate;
	// Intent 回傳
	private static final int SET_RESULT = 1;
	// 顯示界面變數
	private TextView output;
	// SQL 相關變數
	private static String DATABASE_TABLE = "cost";
	private SQLiteDatabase db;
    private MyDBHelper dbHelper;
	
    
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 取得Button元件
        Button btn_NewData = (Button) findViewById(R.id.btn_NewData);
        // 註冊Button的傾聽者物件
        btn_NewData.setOnClickListener(btnAddDataListener);
        // 回傳值
        output = (TextView) findViewById(R.id.output);
		// 取得日期
		Date opDate = new Date();
		thisYear  = opDate.getYear() + 1900;
		thisMonth = opDate.getMonth() + 1;
		thisDate  = opDate.getDate();	
		txtDate   = (TextView) findViewById(R.id.Date);
		txtDate.setText(thisYear + "/" + thisMonth + "/"+thisDate);  
        // 建立SQLiteOpenHelper物件
        dbHelper = new MyDBHelper(this);
	}
			
	// 新建資料按鈕事件
	View.OnClickListener btnAddDataListener = new View.OnClickListener() {
        public void onClick(View v) {
        	TextView txtDate;
        	txtDate  = (TextView) findViewById(R.id.Date);
            // 建立Intent物件
        	Intent intent = new Intent(MainActivity.this, Operators.class);
        	// 建立傳遞資料的Bundle物件
        	Bundle bundle = new Bundle(); 
        	bundle.putString("sendDate", txtDate.getText().toString());
        	intent.putExtras(bundle);  // 加上資料
        	// 啟動活動且有回傳資料
        	startActivityForResult(intent, SET_RESULT);
        }
	};
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode,resultCode,data);
       switch(requestCode) {
         case SET_RESULT:
         {
        	 String date = txtDate.getText().toString();
        	 Bundle bundle = data.getExtras();
        	 output.setText("新增一筆資料\n" + date + "\t\t" + bundle.getString("rName") + "\t\t" + bundle.getString("rMoney") + "\t\t" + bundle.getString("rIO"));	   	 	
             db = dbHelper.getWritableDatabase(); // 開啟資料庫
        	 long id; 
        	 ContentValues cv = new ContentValues();
        	 cv.put("Date", date);
             cv.put("Name", bundle.getString("rName"));
             cv.put("Money",bundle.getString("rMoney"));
             cv.put("IO", bundle.getString("rIO"));
             id = db.insert(DATABASE_TABLE, null, cv);         
             output.setText(output.getText().toString() +  "\n所有資料: " + id);
             db.close();
         } 	
        break;
      }
    }
    
    
    // 資料查詢
    public void btn_List_Click(View view) {
    	// 查詢整個資料表
    	SqlQuery("SELECT * FROM " + DATABASE_TABLE);     	
    }
    // SQL搜尋
    public void btn_SQL_Click(View view){
    	EditText txtSQL = (EditText) findViewById(R.id.txtSQL);
    	// 執行輸入SQL指令的查詢
    	SqlQuery(txtSQL.getText().toString());
    }
	// 執行SQL查詢
    public void SqlQuery(String sql) {
    	String[] colNames;
    	String str = "";    	
    	Cursor c = db.rawQuery(sql, null);
    	colNames = c.getColumnNames();
    	// 顯示欄位名稱
      	for (int i = 0; i < colNames.length; i++)
    		str += colNames[i] + "\t\t";
    	str += "\n";
    	c.moveToFirst();  // 第1筆
    	// 顯示欄位值
    	for (int i = 0; i < c.getCount(); i++) {
    		str += c.getString(0) + "\t\t";
    		str += c.getString(1) + "\t\t";
    		str += c.getString(2) + "\t\t";
    		str += c.getString(3) + "\n";
    		c.moveToNext();  // 下一筆
    	}
    	output.setText(str.toString()); 
    }
}
