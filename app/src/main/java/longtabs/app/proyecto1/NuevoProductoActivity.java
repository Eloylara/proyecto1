package longtabs.app.proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import longtabs.app.proyecto1.modelo.ComprasDatabaseHelper;
import longtabs.app.proyecto1.modelo.Producto;

public class NuevoProductoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);
    }
    public void ingresarProducto(View view)
    {
        ComprasDatabaseHelper helper=new ComprasDatabaseHelper(this);
        //ListaDeCompras listaDeCompras=ListaDeCompras.getInstancia();
        String nombre=((TextView) findViewById(R.id.ingresarNombre)).getText().toString();
        String cantidadStr=((TextView) findViewById(R.id.ingresarCantidad)).getText().toString();
        String extra=((Spinner)findViewById(R.id.ingresarExtra)).getSelectedItem().toString();
        String extraNuevo=((TextView) findViewById(R.id.otroExtra)).getText().toString();
        int cantidad=0;
        try {
            cantidad=Integer.parseInt(cantidadStr);
        }catch (NumberFormatException ex)
        {
            Toast.makeText(this, "Debes ingresar un numero en la cantidad", Toast.LENGTH_SHORT).show();

        }
        if (cantidad>0)
        {
            if (extra.equals("Otro")){
                extra=extraNuevo;
            }
            Producto producto=new Producto(nombre,cantidad,extra);
            helper.ingresarProducto(producto);
            //listaDeCompras.agregarProducto(producto);
            finish();
        }
        else{
            Toast.makeText(this,"Debes ingresar una cantidad mayor a cero",Toast.LENGTH_SHORT).show();

        }
    }
}