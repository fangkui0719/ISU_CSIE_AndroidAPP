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
    // ��������ܼ�
	private int thisYear, thisMonth, thisDate;
	private TextView txtDate;
	// Intent �^��
	private static final int SET_RESULT = 1;
	// ��ܬɭ��ܼ�
	private TextView output;
	// SQL �����ܼ�
	private static String DATABASE_TABLE = "cost";
	private SQLiteDatabase db;
    private MyDBHelper dbHelper;
	
    
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// ���oButton����
        Button btn_NewData = (Button) findViewById(R.id.btn_NewData);
        // ���UButton����ť�̪���
        btn_NewData.setOnClickListener(btnAddDataListener);
        // �^�ǭ�
        output = (TextView) findViewById(R.id.output);
		// ���o���
		Date opDate = new Date();
		thisYear  = opDate.getYear() + 1900;
		thisMonth = opDate.getMonth() + 1;
		thisDate  = opDate.getDate();	
		txtDate   = (TextView) findViewById(R.id.Date);
		txtDate.setText(thisYear + "/" + thisMonth + "/"+thisDate);  
        // �إ�SQLiteOpenHelper����
        dbHelper = new MyDBHelper(this);
	}
			
	// �s�ظ�ƫ��s�ƥ�
	View.OnClickListener btnAddDataListener = new View.OnClickListener() {
        public void onClick(View v) {
        	TextView txtDate;
        	txtDate  = (TextView) findViewById(R.id.Date);
            // �إ�Intent����
        	Intent intent = new Intent(MainActivity.this, Operators.class);
        	// �إ߶ǻ���ƪ�Bundle����
        	Bundle bundle = new Bundle(); 
        	bundle.putString("sendDate", txtDate.getText().toString());
        	intent.putExtras(bundle);  // �[�W���
        	// �Ұʬ��ʥB���^�Ǹ��
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
        	 output.setText("�s�W�@�����\n" + date + "\t\t" + bundle.getString("rName") + "\t\t" + bundle.getString("rMoney") + "\t\t" + bundle.getString("rIO"));	   	 	
             db = dbHelper.getWritableDatabase(); // �}�Ҹ�Ʈw
        	 long id; 
        	 ContentValues cv = new ContentValues();
        	 cv.put("Date", date);
             cv.put("Name", bundle.getString("rName"));
             cv.put("Money",bundle.getString("rMoney"));
             cv.put("IO", bundle.getString("rIO"));
             id = db.insert(DATABASE_TABLE, null, cv);         
             output.setText(output.getText().toString() +  "\n�Ҧ����: " + id);
             db.close();
         } 	
        break;
      }
    }
    
    
    // ��Ƭd��
    public void btn_List_Click(View view) {
    	// �d�߾�Ӹ�ƪ�
    	SqlQuery("SELECT * FROM " + DATABASE_TABLE);     	
    }
    // SQL�j�M
    public void btn_SQL_Click(View view){
    	EditText txtSQL = (EditText) findViewById(R.id.txtSQL);
    	// �����JSQL���O���d��
    	SqlQuery(txtSQL.getText().toString());
    }
	// ����SQL�d��
    public void SqlQuery(String sql) {
    	String[] colNames;
    	String str = "";    	
    	Cursor c = db.rawQuery(sql, null);
    	colNames = c.getColumnNames();
    	// ������W��
      	for (int i = 0; i < colNames.length; i++)
    		str += colNames[i] + "\t\t";
    	str += "\n";
    	c.moveToFirst();  // ��1��
    	// �������
    	for (int i = 0; i < c.getCount(); i++) {
    		str += c.getString(0) + "\t\t";
    		str += c.getString(1) + "\t\t";
    		str += c.getString(2) + "\t\t";
    		str += c.getString(3) + "\n";
    		c.moveToNext();  // �U�@��
    	}
    	output.setText(str.toString()); 
    }
}
