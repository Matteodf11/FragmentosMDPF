package com.example.fragmentosmdpf

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListadoContactosFragment : Fragment() {


    private lateinit var adaptadorContacto: AdaptadorContacto
    var contactos = arrayListOf<Contacto>()
    private lateinit var recyclerView: RecyclerView

    private val viewModel: ContactoViewModel by activityViewModels()


    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        this.viewModel.items.observe(this.viewLifecycleOwner, Observer {
            (recyclerView.adapter as AdaptadorContacto).setPersonas(it)

            (recyclerView.adapter as AdaptadorContacto).notifyDataSetChanged()
        })
        val view = inflater.inflate(R.layout.fragment_listado_contactos, container, false)
        configMenu(view)
        recyclerView = view.findViewById(R.id.listado)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adaptadorContacto = AdaptadorContacto(contactos)
        recyclerView.adapter = adaptadorContacto
        viewModel.items.observe(viewLifecycleOwner) { nuevasPersonas ->
            contactos.addAll(nuevasPersonas)
            adaptadorContacto.notifyDataSetChanged()
        }
        adaptadorContacto.setOnClick {
            this.unselect()
            this.viewModel.setSelected(it)
        }
        adaptadorContacto.setOnDoubleClick {
            this.viewModel.setSelected(it)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, NuevoContactoFragment(true))
                .addToBackStack(null).commit()
        }

        return view
    }

    private fun unselect() {
        this.viewModel.unselect()
        for (child in recyclerView.children) {
            child.setBackgroundColor(0xFFFFFFFF.toInt())
        }
    }

    @SuppressLint("CommitTransaction")
    private fun configMenu(view: View) {
        view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar2)
            .setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.borrar -> {
                        Log.d("eliminar", "configMenu: ")
                        viewModel.getSelected()?.let { it1 -> viewModel.removeItem(it1) }
                        true
                    }

                    R.id.modificar -> {
                        if (viewModel.getSelected() != null) {
                            if (viewModel.getSelected()!!.nombre != "") {
                                viewModel.getSelected()
                                    ?.let { it1 -> this.viewModel.setSelected(it1) }
                                requireActivity().supportFragmentManager.beginTransaction().replace(
                                    R.id.fragmentContainerView, NuevoContactoFragment(true)
                                ).addToBackStack(null).commit()
                            }
                        }
                        true
                    }

                    R.id.nuevo -> {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, NuevoContactoFragment(false))
                            .addToBackStack(null).commit()
                        true
                    }

                    else -> {
                        Log.d("error", "Error ")
                        true
                    }

                }
            }
    }
}