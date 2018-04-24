package www.geekteam.xin.faceinteacher.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import www.geekteam.xin.faceinteacher.R;

public class StatisticsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        //setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backlog);
        toolbar.setTitleMarginStart(1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatisticsActivity.this.finish();
            }
        });
        WebView webView = (WebView) findViewById(R.id.webView);
        String url ="http://geek-team.xin:8081/chart";
        webView.loadUrl(url);
    }
}
