package com.silencedut.androidplugin;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
