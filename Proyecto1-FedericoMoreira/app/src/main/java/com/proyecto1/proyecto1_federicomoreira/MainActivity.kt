package com.proyecto1.proyecto1_federicomoreira

import AdaptadorCustom
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    // Configurar el RecyclerView, es decir, activity_main.xml desde MainActivity.kt
    var lista: RecyclerView? = null
    // Variable adaptador perteneciente a la capa Controlador //
    var adaptador:AdaptadorCustom? = null
    // Variable layoutManager perteneciente administrar al recyclerView presente en la capa de Vista //
    var layoutManager: RecyclerView.LayoutManager? = null

    var isActionMode = false
    var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Definir la variable platillos parteneciente al ArrayList Platillo, y mapearla a la clase Platillo //
        val platillos = ArrayList<Platillo>()
        // Rellenar los valores que contendra el ArrayList Platillo //
        platillos.add(Platillo("Alitas de Pollo Teryaki", 400.0, 3.5F, R.drawable.platillo01))
        platillos.add(Platillo("Ensalada César", 300.0, 5F, R.drawable.platillo02))
        platillos.add(Platillo("Biscochos Salados", 250.0, 2F, R.drawable.platillo03))
        platillos.add(Platillo("Papas Horneadas con Tocino", 150.0, 4.5F, R.drawable.platillo04))
        platillos.add(Platillo("Coctél de Frutas", 200.0, 5F, R.drawable.platillo05))
        platillos.add(Platillo("Papas a a Francesa", 150.0, 5F, R.drawable.platillo06))
        platillos.add(Platillo("Pollo al Carbón y Especias", 450.0, 1F, R.drawable.platillo07))
        platillos.add(Platillo("Pasta Italiana y Albóndigas", 350.0, 3.5F, R.drawable.platillo08))
        platillos.add(Platillo("Bolitas de Jamón Serrano", 140.0, 2F, R.drawable.platillo09))
        platillos.add(Platillo("Ciles Rellenos de Atún", 200.0, 4F, R.drawable.platillo10))

        // Mecanizmo para optimizar nuestro RecyclerView //
        lista = findViewById(R.id.lista)
        lista?.setHasFixedSize(true)

        // Declaramos el layoutManager //
        layoutManager = LinearLayoutManager(this)
        lista?.layoutManager = layoutManager

        // Callback del ActionMode //
        val callback = object: ActionMode.Callback{
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {

                when(item?.itemId){
                    R.id.iEliminar ->{
                        adaptador?.eliminarSeleccionados()
                    } else->{
                    return true
                }
                }

                adaptador?.terminarActionMode()
                mode?.finish()
                isActionMode = false
                return true
            }

            // Metodo para crear el ActionMode //
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                // Inicializar action Mode
                adaptador?.iniciarActionMode()
                actionMode = mode
                // inflar menu
                menuInflater.inflate(R.menu.menu_contextual, menu!!)
                return true
            }

            // Metodo para declarar el onPreparaActionMode que se mostrara en el toolbar //
            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                // mode?.title = "0 seleccionados"
                return false
            }

            // Metodo para destruir el ActionMode que se mostró el el toolbar, una vez que se activo la funcion onPrepareActionMode //
            override fun onDestroyActionMode(mode: ActionMode?) {
                // destruimos el modo //
                adaptador?.destruirActionMode()
                isActionMode = false
            }

        }

        // Definir el metodo para ClickListener //
        adaptador = AdaptadorCustom(platillos, object:ClickListener{
            override fun onClick(vista: View, index: Int) {
                Toast.makeText(applicationContext, platillos.get(index).nombre, Toast.LENGTH_SHORT).show()
            }
        }, object: LongClickListener{
            override fun longClick(vista: View, index: Int) {
                //Log.d("LONG", "Prueba")
                if(!isActionMode){
                    startSupportActionMode(callback)
                    isActionMode= true
                    adaptador?.seleccionarItem(index)
                }else {
                    // hacer selecciones o deselecciones
                    adaptador?.seleccionarItem(index)
                }
                actionMode?.title = adaptador?.obtenerNumeroElementosSeleccionados().toString() + " elemento/s seleccionado/s"
            }

        })
        lista?.adapter = adaptador

        // Definir el swipeToRefresh //
        val swipeToRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeToRefresh.setOnRefreshListener {
            for(i in 1..500000000){
            }
            swipeToRefresh.isRefreshing = false
            // Agregar un elemento a la lista si el usuario realiza un refresh //
            platillos.add(Platillo("Lasania Premium Tzuzul", 800.0, 5F, R.drawable.platillo11_lasania_premium_tzuzul))
            adaptador?.notifyDataSetChanged()
        }
    }
}