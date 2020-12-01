package longtabs.app.proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import longtabs.app.proyecto1.modelo.ComprasDatabaseHelper;
import longtabs.app.proyecto1.modelo.Producto;

public class MainActivity extends AppCompatActivity {
    // private ListaDeCompras lista=ListaDeCompras.getInstancia();
    private ComprasDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //icono de la app
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }

    public void verLista(View view)
    {
        helper=new ComprasDatabaseHelper(this);
        try {
           ArrayList<Producto> productos=(ArrayList<Producto>) helper.listaProdutos();
           Intent intent=new Intent(this,ListaProductoActivity.class);
           startActivity(intent);

        }catch (Exception ex)
        {
            Toast.makeText(this, "La lista esta vacía", Toast.LENGTH_SHORT).show();
        }
        //ArrayList<Producto> productos=lista.getListaDeCompras();
        //if(productos.size()>0) {
            //Intent intent = new Intent(this, ListaProductoActivity.class);
           // startActivity(intent);
        //}
        //else {
            //Toast.makeText(this, "La lista de compras esta vacía",Toast.LENGTH_SHORT).show();
       // }
    }
    public void ingresarNuevo(View view)
    {
        Intent intent=new Intent(this,NuevoProductoActivity.class);
        startActivity(intent);
    }
    public void  eliminarComprados(View view)
    {
        //lista.eliminarComprados();
        helper=new ComprasDatabaseHelper(this);
        String msg=helper.eliminarComprados();
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}