package com.example.fragmentosmdpf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactoViewModel : ViewModel() {
    private val _contactos = MutableLiveData<ArrayList<Contacto>>(
        arrayListOf(
            Contacto(1, "Matteo", "Di Paolo Formiconi", "691929679", "matteodf2008@gmail.com"),
            Contacto(2, "Pablo", "Gomez Salinas", "602463926", "pabgomsal@gmail.com"),
            Contacto(3, "Fedor", "Litvinov Kuznetsov", "643840163", "fedlitkuz@gmail.com")
        )
    )

    val items: LiveData<ArrayList<Contacto>> get() = _contactos

    private val _item_selected: MutableLiveData<Contacto> =
        MutableLiveData<Contacto>(Contacto(nombre = "", apellidos = "", telefono = "", email = ""))
    val item_selected: LiveData<Contacto> = _item_selected
    public fun setSelected(index: Int) {
        _item_selected.value = _contactos.value?.get(index)
    }

    fun setSelected(contacto: Contacto) {
        this._item_selected.value = contacto
    }

    public fun getSelected(): Contacto? {
        return item_selected.value

    }

    public fun unselect() {
        this._item_selected.value=Contacto(nombre = "", apellidos = "", telefono = "", email = "")
    }

    public fun addItem(contacto: Contacto) {

        var tempo = this._contactos.value?: ArrayList<Contacto>()
        if (contacto.id==-1){
            var max = tempo.stream().mapToInt{it.id}.max().orElse(0)
            contacto.id = max+1
            tempo.add(contacto)
        }else{
            tempo.removeIf { it.id == contacto.id }
            tempo.add(contacto)
        }
        tempo.sortBy { it.nombre }
        this._contactos.value=tempo
    }

    public fun removeItem(contacto: Contacto) {
val currentList = _contactos.value?: ArrayList<Contacto>()
        currentList.remove(contacto)
        _contactos.value = currentList
    }

    public fun getItem(index: Int): Contacto? {
        return this.items.value?.get(index)


    }
}