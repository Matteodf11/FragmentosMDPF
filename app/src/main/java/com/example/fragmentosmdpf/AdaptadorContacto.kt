package com.example.fragmentosmdpf

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.util.Consumer
import androidx.recyclerview.widget.RecyclerView


class AdaptadorContacto(private var contactos: ArrayList<Contacto>) : RecyclerView.Adapter<AdaptadorContacto.ViewHolder>() {

    private var onClick: Consumer<Contacto>? = null
    private var ondoubleclick: Consumer<Contacto>? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagen: ImageView = view.findViewById(R.id.imagen)
        val nombre: TextView = view.findViewById(R.id.nombre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_adaptador, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.contactos.size
    }

    public fun setOnClick(onlick: Consumer<Contacto>) {
        this.onClick = onlick
    }

    public fun setOnDoubleClick(ondoubleclick: Consumer<Contacto>) {
        this.ondoubleclick = ondoubleclick
    }

    public fun setPersonas(p:ArrayList<Contacto>){
        contactos = p
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.imagen.setImageResource(R.drawable.baseline_person_24)
            holder.nombre.text = this.contactos.get(position).nombre
        holder.itemView.setOnClickListener {
            if (this.onClick != null) {
                this.onClick!!.accept(this.contactos.get(position))
                val border = GradientDrawable()
                border.setColor(0xFFFFFFFF.toInt())
                border.setStroke(4,0xFFFF0000.toInt())
                border.cornerRadius = 16f
                holder.itemView.background = border
            }
        }
        holder.itemView.setOnLongClickListener {
            if (this.ondoubleclick != null){
                this.ondoubleclick!!.accept(this.contactos.get(position))
            }
            true
        }
    }
}
