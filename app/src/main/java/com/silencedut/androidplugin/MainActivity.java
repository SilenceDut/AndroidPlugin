package com.silencedut.androidplugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ryg.dynamicload.DLBasePluginActivity;
import com.ryg.dynamicload.DLBasePluginFragmentActivity;
import com.ryg.dynamicload.internal.DLIntent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends DLBasePluginFragmentActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DLIntent intent = new DLIntent(getPackageName(),SecondActivity.class);
                startPluginActivityForResult(intent, 0);
            }
        });

        String dexOrApkPath = Environment.getExternalStorageDirectory() + "/pathclassloder.apk";

        Context context=getApplicationContext();//获取Context对象；
        File dexOutputDir = context.getDir("dex", Context.MODE_PRIVATE);

        //art 可以加载,dalvik不可以
        ClassLoader dexClassLoader = new PathClassLoader(dexOrApkPath, null,getClassLoader());

        final String vmVersion = System.getProperty("java.vm.version");

        boolean isArt = vmVersion != null && vmVersion.startsWith("2");

        Log.i(TAG,"isArt:"+isArt);
        try {

            Class cls = dexClassLoader.loadClass("com.silencedut.daynighttogglebuttonsample.MainActivity");
            Log.i(TAG,"cls.getSimpleName():"+cls.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "dexOutputDir.getAbsolutePath() :" + dexOutputDir);

        Log.i(TAG, "[onCreate] dexClassLoader :" + dexClassLoader.toString());
        while (dexClassLoader.getParent()!=null){
            dexClassLoader = dexClassLoader.getParent();
            Log.i(TAG,"[onCreate] dexClassLoader getParent(): " + dexClassLoader.toString());
        }

        ClassLoader classLoader = getClassLoader();

        if (classLoader != null){
            Log.i(TAG, "[onCreate] classLoader :" + classLoader.toString());
            while (classLoader.getParent()!=null){
                classLoader = classLoader.getParent();
                Log.i(TAG,"[onCreate] classLoader getParent(): " + classLoader.toString());
            }
        }
    }

}
