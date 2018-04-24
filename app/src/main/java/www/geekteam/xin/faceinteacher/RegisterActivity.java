package www.geekteam.xin.faceinteacher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
        private EditText RegaccountEt;
        private EditText regpwdEt;
        private EditText reregpwdEt;
        private Button subBtn;
      public  static   String name;
       public static String pwd;
        String pwd2;
   public static RegisterActivity registerActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerActivity=this;
        setContentView(R.layout.activity_register);

        RegaccountEt =findViewById(R.id.RegaccountEt);
        regpwdEt =findViewById(R.id.regpwdEt);
        reregpwdEt=findViewById(R.id.reregpwdEt);
        subBtn=findViewById(R.id.subBtn);

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=RegaccountEt.getText().toString().trim();
                pwd=regpwdEt.getText().toString().trim();
                pwd2=reregpwdEt.getText().toString().trim();
                if(name.length()==0||pwd.length()==0||pwd2.length()==0){
                    Toast.makeText(RegisterActivity.this,"账号或密码不能为空",Toast.LENGTH_LONG).show();
                }
                if(pwd.equals(pwd2)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            new RegisterTask().execute();
                        }
                    })
                    .start();

                }else {
                    Toast.makeText(RegisterActivity.this,"两次密码不一致！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
