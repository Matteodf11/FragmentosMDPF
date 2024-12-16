package com.example.fragmentosmdpf

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    private val viewModel: ContactoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }


    private fun add(fragment: Fragment, fm: FragmentManager, ft: FragmentTransaction) {
        for (it in fm.fragments) {
            ft.hide(it)
        }

        ft.add(R.id.fragmentContainerView, fragment)
        this.viewModel.setSelected(Contacto(1, "", "", "", ""))
        ft.addToBackStack(null)
        ft.commit()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.agendamenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var fm: FragmentManager = supportFragmentManager
        var ft: FragmentTransaction = fm.beginTransaction()
        return when (item.itemId) {
            R.id.listado -> {
                add(ListadoContactosFragment(), fm, ft)
                true
            }

            R.id.nuevo -> {
                add(NuevoContactoFragment(false), fm, ft)
                true
            }

            android.R.id.home -> {
                supportFragmentManager.popBackStack()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}
