import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto1.proyecto1_federicomoreira.ClickListener
import com.proyecto1.proyecto1_federicomoreira.LongClickListener
import com.proyecto1.proyecto1_federicomoreira.Platillo
import com.proyecto1.proyecto1_federicomoreira.R

// Especificarle que AdaptadorCustom que es de tipo Adaptador
// Como argumento le paso el arrayList, el ClickListener, el longClickListenr
// Estiende de RecyclerView.Adapter y le indico que es el Adaptador es de tipo Custom
class AdaptadorCustom(items:ArrayList<Platillo>, var listener: ClickListener, var longClickListener: LongClickListener): RecyclerView.Adapter<AdaptadorCustom.ViewHolder>(){

    // Referencias de nuestra lista de datos //
    var items: ArrayList<Platillo>? = null
    // multiSeleccion del Callback //
    var multiSeleccion = false
    // Referencia a nuestros items seleccionados //
    var itemsSeleccionados:ArrayList<Int>? = null
    // Inicializar nuestro viewHolder como nulo //
    var viewHolder:ViewHolder? = null

    init{
        this.items = items
        itemsSeleccionados = ArrayList()
    }

    // Implementacion del miembro onCreateViewHolder perteneciente al viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorCustom.ViewHolder {
        val vista = LayoutInflater.from(parent?.context).inflate(R.layout.template_platillo,parent,false)
        viewHolder = ViewHolder(vista, listener, longClickListener)
        return viewHolder!!
    }

    // Implementacion del miembro getItemCount perteneciente al viewHolder
    override fun getItemCount(): Int {
        return items?.count()!!
    }

    // Implementacion del miembro onBindViewHolder perteneciente al viewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.foto?.setImageResource(item?.foto!!)
        holder.nombre?.text = item?.nombre
        holder.precio?.text = "$" + item?.precio.toString()
        holder.rating?.rating = item?.rating!!

        // If-Else para los elementos seleccionados de la lista //
        if(itemsSeleccionados?.contains(position)!!){
            holder.vista.setBackgroundColor(Color.LTGRAY)
        }else{
            holder.vista.setBackgroundColor(Color.WHITE)
        }
    }

    // iniciarActionMode Callback //
    fun iniciarActionMode(){
        multiSeleccion = true
    }

    // iniciarActionMode Callback //
    fun destruirActionMode(){
        multiSeleccion = false
        itemsSeleccionados?.clear()
        notifyDataSetChanged()
    }

    // iniciarActionMode Callback //
    fun terminarActionMode(){
        // Eliminar elementos seleccionados
        for(item in itemsSeleccionados!!){
            itemsSeleccionados?.remove(item)
        }
        multiSeleccion = false
        notifyDataSetChanged()
    }

    // Metodo para seleccionarItem //
    fun seleccionarItem(index:Int) {
        if (multiSeleccion) {
            if (itemsSeleccionados?.contains(index)!!) {
                itemsSeleccionados?.remove(index)
            }else{
                itemsSeleccionados?.add(index)
            }
            notifyDataSetChanged()
        }
    }

    // Mostrar en el toolbar la cantidad de elementos que ha seleccionado el usuario de la lista //
    fun obtenerNumeroElementosSeleccionados():Int{
        return itemsSeleccionados?.count()!!
    }

    // Eliminar elementos seleccionados por el usuario de la lista //
    fun eliminarSeleccionados(){
        if(itemsSeleccionados?.count()!! > 0){
            var itemsEliminados = ArrayList<Platillo>()

            for(index in itemsSeleccionados!!){
                itemsEliminados.add(items?.get(index)!!)
            }

            items?.removeAll(itemsEliminados)
            itemsSeleccionados?.clear()
        }
    }

    // Creacion de la clase ViewHolder donde le coloco por parametro la view, clickListenery longClickListener
    // Y le especifico que estienda un RecyclerView, ViewHolder, ViewOnClickListener y ViewOnLongClickListener
    class ViewHolder(vista: View, listener: ClickListener, longClickListener: LongClickListener): RecyclerView.ViewHolder(vista), View.OnClickListener, View.OnLongClickListener{
        var vista = vista
        var foto: ImageView? = null
        var nombre: TextView? = null
        var precio: TextView? = null
        var rating: RatingBar? = null
        var listener:ClickListener? = null
        var longListener:LongClickListener? = null

        init {
            foto = vista.findViewById(R.id.ivFoto)
            nombre = vista.findViewById(R.id.tvNombre)
            precio = vista.findViewById(R.id.tvPrecio)
            rating = vista.findViewById(R.id.tvRating)
            this.listener = listener
            this.longListener = longClickListener
            vista.setOnClickListener(this)
            vista.setOnLongClickListener(this)
        }

        // Metodo para el onClick //
        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }

        // Metodo para el onLongClick //
        override fun onLongClick(v: View?): Boolean {
            this.longListener?.longClick(v!!, adapterPosition)
            return true
        }
    }
}