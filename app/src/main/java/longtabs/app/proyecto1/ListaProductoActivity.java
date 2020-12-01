package longtabs.app.proyecto1;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import longtabs.app.proyecto1.modelo.ComprasDatabaseHelper;
import longtabs.app.proyecto1.modelo.Producto;

public class ListaProductoActivity extends ListActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        cargarLista();

    }
    public void cargarLista()
    {
        //Leer la lista de productos desde la base de datos
        ComprasDatabaseHelper helper=new ComprasDatabaseHelper(this);
        lista=getListView();
        List<Producto> productoList= helper.listaProdutos();
        ArrayAdapter<Producto> listaAdapter=
                new ArrayAdapter<Producto>(this,
                        android.R.layout.simple_list_item_1,
                        productoList);
        lista.setAdapter(listaAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //Obtener el item de la lista que se seleccionó
        Object o=lista.getItemAtPosition(position);
        String linea=o.toString();
        //Obtener el nombre del producto
        String[] separar=linea.split(":");
        //Llamar a DetallesActivity
        Intent intent=new Intent(ListaProductoActivity.this,DetallesActivity.class);
        intent.putExtra("nombreProducto",separar[0]);
        startActivityForResult(intent,1);

    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode == RESULT_OK)
            {
                cargarLista();
            }
        }
    }
}