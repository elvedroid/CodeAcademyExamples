package com.example.kotlinfirstexample


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    lateinit var listener: NekojListener
    lateinit var listenerFunc : (String) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnOpen.setOnClickListener {
            val text = edit_text.text.toString()
            listenerFunc(text)
//            val bundle = Bundle()
//            bundle.putString("text", text)
//            val secondFragment = SecondFragment()
//            secondFragment.arguments = bundle
//
//            fragmentManager?.beginTransaction()
//                ?.replace(R.id.main_container, secondFragment)
//                ?.commit()
        }
    }


}
