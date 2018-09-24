package com.vdroid.looperhandler

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log


class MainActivity : AppCompatActivity()
{
    lateinit var mHtHandler:Handler
    lateinit var mUiHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ht = HandlerThread("MySuperAwsomeHandlerThread")
        ht.start()

        val callback = Handler.Callback { msg ->
            if (msg.what == 0)
            {
                Log.e("FragmentActivity.TAG", "got a meaasage in " + Thread.currentThread() + ", now sleeping... ")
                try
                {
                    Thread.sleep(2000)
                }
                catch (e: InterruptedException)
                {
                    e.printStackTrace()
                }

                Log.d("FragmentActivity.TAG", "woke up, notifying ui thread...")

                //communicate with other threads
                mUiHandler.sendEmptyMessage(1)

            }
            else if (msg.what == 1)
            {
                Log.d("FragmentActivity.TAG", "got a notification in " + Thread.currentThread())
            }

            false
        }

        mHtHandler = Handler(ht.looper, callback)//BG thread
        mUiHandler = Handler(callback)//UI thread
        mHtHandler.sendEmptyMessageDelayed(0, 3000)


    }
}
