package com.example.fragmentosmdpf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels


class NuevoContactoFragment(editar: Boolean) : Fragment() {
      private val viewModel: ContactoViewModel by activityViewModels()
    private var _editar = editar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_nuevo_contacto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (_editar){
            viewModel.item_selected.observe(this.viewLifecycleOwner) {

                view.findViewById<EditText>(R.id.editTextNombre).setText(it.nombre)
                view.findViewById<EditText>(R.id.editTextApellidos).setText(it.apellidos)
                view.findViewById<EditText>(R.id.editTextTelefono).setText(it.telefono)
                view.findViewById<EditText>(R.id.editTextEmail).setText(it.email)
            }
        }

        val btnalta = view.findViewById<Button>(R.id.buttonEnviar)

        btnalta.setOnClickListener {
            val nombre = view.findViewById<EditText>(R.id.editTextNombre)
            val apellidos = view.findViewById<EditText>(R.id.editTextApellidos)
            val telefono = view.findViewById<EditText>(R.id.editTextTelefono)
            val email = view.findViewById<EditText>(R.id.editTextEmail)
            val contacto: Contacto
            if (nombre.text.isNotEmpty() && apellidos.text.isNotEmpty() && telefono.text.isNotEmpty() && email.text.isNotEmpty()){
                if (_editar) {
                    contacto = Contacto(
                        id = viewModel.item_selected.value!!.id,
                        nombre = nombre.text.toString(),
                        apellidos = apellidos.text.toString(),
                        telefono = telefono.text.toString(),
                        email = email.text.toString()
                    )
                    viewModel.addItem(contacto)
                }else{
                    contacto = Contacto(
                        nombre = nombre.text.toString(),
                        apellidos = apellidos.text.toString(),
                        telefono = telefono.text.toString(),
                        email = email.text.toString()
                    )
                    viewModel.addItem(contacto)
                }
            }
            requireActivity().supportFragmentManager.popBackStack()
            viewModel.unselect()
        }
    }


}