package com.example.foregroundservice

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService: Service() {

    var counter:Int = 0
    var terminate:Boolean = false

   // override fun onBind(p0: Intent?): IBinder? = null

    inner class MyServiceBinder: Binder() {
        fun getService():MyService = this@MyService
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d(TAG,"Service: onBind")
        val myServiceBinder: MyServiceBinder = MyServiceBinder()
        val serviceNotification:ServiceNotification = ServiceNotification()
        startForeground(1,serviceNotification.showNotification(this))
        Thread{loop()}.start()
        return myServiceBinder
    }

    override fun unbindService(conn: ServiceConnection) {
        Log.d(TAG,"Service: unBind")
        super.unbindService(conn)
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG,"Service: onRebind")
        super.onRebind(intent)
    }

    override fun onCreate() {
        Log.d(TAG,"Service: onCreate")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.d(TAG,"Service: onDestroy")
        terminate = true
        super.onDestroy()
    }

    override fun startForegroundService(service: Intent?): ComponentName? {
        Log.d(TAG,"Service: startForegroundService")

        //startForeground(1,serviceNotification.showNotification(this@MyService))
        return super.startForegroundService(service)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG,"Service: onStartCommand")
        val serviceNotification:ServiceNotification = ServiceNotification()
        //serviceNotification.showNotification(this)
        //startForeground(1,serviceNotification.showNotification(this))
        Thread{loop()}.start()
        return START_STICKY
    }

    fun loop(){
        counter = 0
        while (!terminate){
            counter++
            Thread.sleep(1000)
            Log.d(TAG,"Service Loop: $counter")
        }
    }

    fun getNumber():Int{
        Log.d(TAG,"Service: getNumber")
        return counter
    }
}