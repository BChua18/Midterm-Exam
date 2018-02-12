package com.example.chua.midtermexam.AlarmClock

import android.app.AlarmManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast

import com.example.chua.midtermexam.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AlarmClockFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AlarmClockFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlarmClockFragment : android.support.v4.app.Fragment() {

    internal lateinit var context : Context
    lateinit var am: AlarmManager
    lateinit var tp: TimePicker
    lateinit var update_text: TextView
    lateinit var con: Context
    lateinit var btnSet: Button
    lateinit var btnStop: Button

    var hour: Int = 0
    var min: Int = 0

    lateinit var pI: PendingIntent

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    class Receiver : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.d("AlarmClockFragment", "Receiver : " + Date().toString() )
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_alarm_clock, container, false)
        context = this.getContext()


        am = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        tp = rootView!!.findViewById(R.id.timePicker) as TimePicker
        update_text = rootView!!.findViewById(R.id.txtView)
        btnSet = rootView!!.findViewById(R.id.btnSet)
        btnStop = rootView!!.findViewById(R.id.btnStop)

        var calendar: Calendar = Calendar.getInstance()
        var myIntent: Intent = Intent(context, AlarmReceiver::class.java)

        btnSet.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    calendar.set(Calendar.HOUR_OF_DAY, tp.hour)
                    calendar.set(Calendar.MINUTE, tp.minute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    hour = tp.hour
                    min = tp.minute
                } else {
                    calendar.set(Calendar.HOUR_OF_DAY, tp.currentHour)
                    calendar.set(Calendar.MINUTE, tp.currentMinute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    hour = tp.currentHour
                    min = tp.currentMinute
                }

                var hr_str: String = hour.toString()
                var min_str: String = min.toString()

                if (hour > 12) {
                    hr_str = (hour - 12).toString()
                }
                if (min < 10) {
                    min_str = "0$min"
                }
                set_alarm_text("Alarm set to: $hr_str: $min_str")
                myIntent.putExtra("extra", "on")
                pI = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pI)

            }
        })
        btnStop.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                set_alarm_text("Alarm off")
                pI = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                am.cancel(pI)
                myIntent.putExtra("extra", "off")
                context.sendBroadcast(myIntent)

            }
        })
        return rootView
    }

    private fun set_alarm_text(s: String) {
        update_text.setText(s)
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            Toast.makeText(context, "Alarm Attached", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AlarmClockFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): AlarmClockFragment {
            val fragment = AlarmClockFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
