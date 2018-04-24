package www.geekteam.xin.faceinteacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
          private  Button login;
          private EditText etId;
          private EditText etPwd;
          private Button reg;
          public static String tId;
          public static String tPwd;
          public static LoginActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        setContentView(R.layout.activity_login);
       login=findViewById(R.id.subBtn);
       etId=findViewById(R.id.accountEt);
       etPwd=findViewById(R.id.pwdEt);
        reg=findViewById(R.id.regBtn);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            tId=etId.getText().toString().trim();
            tPwd=etPwd.getText().toString().trim();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new LoginTask().execute();
                }
            }).start();
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       new DataTask().execute();
                   }
               }).start();

           }
       });

    }

}
