package com.highgreat.sven.ioc;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.app_text)
    private Button textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"点击了",Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @OnClick({R.id.app_text,R.id.app_text1})
    public boolean click(View view){
//        Toast.makeText(this,"---->"+textView,Toast.LENGTH_SHORT).show();

        NewsDialog newsDialog = new NewsDialog(this);
        newsDialog.show();

        return false;
    }

    @OnLongClick({R.id.app_text,R.id.app_text1})
    public boolean longClick(View view){
        Toast.makeText(this, "长按了", Toast.LENGTH_SHORT).show();
        return true;
    }

}
