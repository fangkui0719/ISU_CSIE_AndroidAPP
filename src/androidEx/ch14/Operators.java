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
    	// ���o�ǻ������
    	Bundle bundle = this.getIntent().getExtras();
    	if (bundle == null) return;
    	// ���o��� 

		txtName = (EditText) findViewById(R.id.txtName);
		txtMoney = (EditText) findViewById(R.id.txtMoney);
		btn_In = (RadioButton) findViewById(R.id.btn_Income);
		//btn_Out = (RadioButton) findViewById(R.id.btn_Outlay);
	}
	
    public void btnOK_Click(View view) {
    	// �إߦ^�Ǹ��
        Intent rIntent = new Intent();
        // �إ߶Ǧ^��
        Bundle rbundle = new Bundle();
        rbundle.putString("rName", txtName.getText().toString());
        rbundle.putString("rMoney",txtMoney.getText().toString());
        if (btn_In.isChecked()){
        	rbundle.putString("rIO","���J");
        }
        else {
        	rbundle.putSerializable("rIO", "��X"); 
        }
        rbundle.putString("RESULT", "�s�W�@�����!\n");	
        rIntent.putExtras(rbundle);    // �[�W���
        setResult(RESULT_OK, rIntent); // �]�w�Ǧ^
        finish(); // ��������
    }
	    
}
