package com.example.kotlinfirstexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = MainFragment()
        mainFragment.listener = object : NekojListener {
            override fun nekojaMetoda(text: String) {

            }
        }

        mainFragment.listenerFunc = {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, mainFragment)
            .commit()
    }
}

class ListenerImpl : NekojListener {
    override fun nekojaMetoda(text: String) {

    }

}
