package longtabs.app.proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import longtabs.app.proyecto1.modelo.ComprasDatabaseHelper;
import longtabs.app.proyecto1.modelo.Producto;

public class DetallesActivity extends AppCompatActivity {
    private Producto producto;
    private Intent intent;
    private ComprasDatabaseHelper helper=new ComprasDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        intent=getIntent();
        String nombreProducto=(String)intent.getExtras().get("nombreProducto");

        //Traemos el producto desde la base de datos
        producto=helper.getProducto(nombreProducto);
       // int id=(Integer)intent.getExtras().get("idProducto");
       // producto= ListaDeCompras.getInstancia().getProducto(id);

        TextView txtNombre=(TextView)findViewById(R.id.txtNombre);
        txtNombre.setText(producto.getNombre());

        TextView txtUnidad=(TextView)findViewById(R.id.txtUnidad);
        txtUnidad.setText(producto.getCantidad()+" "+producto.getUnidad());

        TextView txtEstado=(TextView)findViewById(R.id.txtEstado);
        Button cambiar=(Button)findViewById(R.id.estado);
        if(producto.isEstado()){
            txtEstado.setText("Pendiente");
            cambiar.setText("Marcar como comprado");
        }
        else{
            txtEstado.setText("Comprado");
            cambiar.setText("Marcar como pendiente");
        }


    }
    public void cambiarEstado(View view)
    {
        producto.setEstado(!producto.isEstado());
        //Atualizar en la base de datos
        String msg=helper.cambiarEstado(producto);
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK,intent);
        finish();
    }

}