package com.highgreat.sven.ioc;

import android.app.Activity;
import android.os.Bundle;


public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InjectUtils.inject(this);
    }
}
