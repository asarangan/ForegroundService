package com.example.foregroundservice

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TextView

val TAG = "Foreground"


class MainActivity : AppCompatActivity() {
    private lateinit var myService: MyService
    private lateinit var myIntent: Intent

    private val serviceConnection:ServiceConnection =  object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.d(TAG, "onService Connected")
            myService = (p1 as MyService.MyServiceBinder).getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(TAG, "onService Disconnected")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"MainActivity: onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val serviceNotification:ServiceNotification = ServiceNotification()
//        serviceNotification.showNotification(this)

        myIntent = Intent(this, MyService::class.java)
//        startService(myIntent)
//        Log.d(TAG,"MainActivity: startService")

        //Start service
        findViewById<Button>(R.id.btnStartService).setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startService(myIntent)
                Log.d(TAG, "MainActivity: startForegroundService")
            }
        }

        //Bind
        findViewById<Button>(R.id.btnBind).setOnClickListener{
            Log.d(TAG,"MainActivity: Bind")
            bindService(myIntent,serviceConnection, Context.BIND_AUTO_CREATE)
        }

        //Get value
        findViewById<Button>(R.id.btnGetValue).setOnClickListener {
            findViewById<TextView>(R.id.tvCounter).text = myService.getNumber().toString()
        }

        //Stop service
        findViewById<Button>(R.id.btnStopService).setOnClickListener {
            stopService(myIntent)
        }

        //Unbind
        findViewById<Button>(R.id.btnUnbind).setOnClickListener {
            unbindService(serviceConnection)
        }
    }

    override fun onDestroy() {
        Log.d(TAG,"MainActivity: onDestroy")
        //unbindService(serviceConnection)
        super.onDestroy()
    }
}