package com.example.chua.midtermexam

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.example.chua.midtermexam.AlarmClock.AlarmClockFragment
import com.example.chua.midtermexam.Stopwatch.StopwatchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_alarm -> {
                transaction.replace(R.id.fragment_layout, AlarmClockFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_stop -> {
                transaction.replace(R.id.fragment_layout,
                        StopwatchFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_layout, Fragment()).commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

}