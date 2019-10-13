package androidEx.ch14;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;


public class Operators extends Activity {
    private EditText txtName, txtMoney;
    private RadioButton btn_In;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.operators);
    	// 取得傳遞的資料
    	Bundle bundle = this.getIntent().getExtras();
    	if (bundle == null) return;
    	// 取得資料 

		txtName = (EditText) findViewById(R.id.txtName);
		txtMoney = (EditText) findViewById(R.id.txtMoney);
		btn_In = (RadioButton) findViewById(R.id.btn_Income);
		//btn_Out = (RadioButton) findViewById(R.id.btn_Outlay);
	}
	
    public void btnOK_Click(View view) {
    	// 建立回傳資料
        Intent rIntent = new Intent();
        // 建立傳回值
        Bundle rbundle = new Bundle();
        rbundle.putString("rName", txtName.getText().toString());
        rbundle.putString("rMoney",txtMoney.getText().toString());
        if (btn_In.isChecked()){
        	rbundle.putString("rIO","收入");
        }
        else {
        	rbundle.putSerializable("rIO", "支出"); 
        }
        rbundle.putString("RESULT", "新增一筆資料!\n");	
        rIntent.putExtras(rbundle);    // 加上資料
        setResult(RESULT_OK, rIntent); // 設定傳回
        finish(); // 結束活動
    }
	    
}
