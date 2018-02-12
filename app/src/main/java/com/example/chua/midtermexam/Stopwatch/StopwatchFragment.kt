package com.example.chua.midtermexam.Stopwatch

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

import com.example.chua.midtermexam.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [StopwatchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [StopwatchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StopwatchFragment : android.support.v4.app.Fragment() {

    var handler: Handler? = null
    var hour: TextView? = null
    var minute: TextView? = null
    var seconds: TextView? = null
    var milli_seconds: TextView? = null

    internal var MillisecondTime: Long = 0
    internal var StartTime: Long = 0
    internal var TimeBuff: Long = 0
    internal var UpdateTime = 0L


    internal var Seconds: Int = 0
    internal var Minutes: Int = 0
    internal var MilliSeconds: Int = 0

    internal var flag:Boolean=false

    var startButton: ImageButton? = null
    var stopButton: ImageButton? = null
    var pauseButton: ImageButton? = null

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_stopwatch, container, false)
        startButton = rootView!!.findViewById(R.id.btnStart)
        stopButton = rootView!!.findViewById(R.id.btnStop)
        hour = rootView!!.findViewById(R.id.hour)
        minute = rootView!!.findViewById(R.id.minute)
        seconds = rootView!!.findViewById(R.id.seconds)
        milli_seconds = rootView!!.findViewById(R.id.milli_second)


        startButton?.setOnClickListener {
            if (flag) {
                handler?.removeCallbacks(runnable)
                startButton?.setImageResource(R.drawable.ic_start)
                flag = false
            }else{
                startButton?.setImageResource(R.drawable.ic_pause)
                StartTime = SystemClock.uptimeMillis()
                handler?.postDelayed(runnable, 0)
                flag = true
            }
        }
        stopButton?.setOnClickListener{
            if (flag)
                handler?.removeCallbacks(runnable)
            hour?.text = "00"
            minute?.text = "00"
            seconds?.text = "00"
            milli_seconds?.text = "00"
            startButton?.setImageResource(R.drawable.ic_start)
        }
        handler = Handler()
        return rootView
    }


    var runnable: Runnable = object : Runnable {

        override fun run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime

            UpdateTime = TimeBuff + MillisecondTime

            Seconds = (UpdateTime / 1000).toInt()

            Minutes = Seconds / 60

            Seconds = Seconds % 60

            MilliSeconds = (UpdateTime % 1000).toInt()


            if (Minutes.toString().length < 2) {
                minute?.text = "0" + Minutes.toString()
            } else {
                minute?.text = Minutes.toString()
            }
            if (Seconds.toString().length < 2) {
                seconds?.text = "0" + Seconds.toString()
            } else {
                seconds?.text = Seconds.toString()
            }

            milli_seconds?.text = MilliSeconds.toString()

            handler?.postDelayed(this, 0)
        }
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
            Toast.makeText(context, "Stopwatch Attached", Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment StopwatchFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): StopwatchFragment {
            val fragment = StopwatchFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
