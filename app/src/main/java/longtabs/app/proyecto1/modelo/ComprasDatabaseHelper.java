package longtabs.app.proyecto1.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ComprasDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="productos.db";
    private static final int DB_VERSION=1;

    public ComprasDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlTxt="CREATE TABLE PRODUCTOS("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"NOMBRE TEXT, CANTIDAD INTEGER, "
                +"UNIDAD TEXT, ESTADO INTEGER);";
        sqLiteDatabase.execSQL(sqlTxt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void ingresarProducto(Producto producto)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues valores=new ContentValues();
        valores.put("NOMBRE",producto.getNombre());
        valores.put("CANTIDAD",producto.getCantidad());
        valores.put("UNIDAD",producto.getUnidad());
        if (producto.isEstado())
        {
            valores.put("ESTADO",1);
        }
        else{
            valores.put("ESTADO",0);
        }
        db.insert("PRODUCTOS",null,valores);
    }
    public List<Producto> listaProdutos()
    {
        SQLiteDatabase db=getReadableDatabase();
        List<Producto> productos=new ArrayList<>();
        Cursor cursor=db.query("PRODUCTOS",
                new String[]{"NOMBRE","CANTIDAD","UNIDAD","ESTADO"},
                null,null,null,null,null);
        cursor.moveToFirst();
        int estadoInt;
        boolean estado=false;
        do{
            estadoInt=cursor.getInt(3);
            if(estadoInt==1) estado=true;
            else estado=false;

            productos.add(new Producto(cursor.getString(0),
                    cursor.getInt(1),
                    cursor.getString(2),estado));
        }while (cursor.moveToNext());
        cursor.close();
        db.close();
        return productos;
    }
    public Producto getProducto(String nombre)
    {
        Producto p;
        SQLiteDatabase db=getReadableDatabase();
        String sqlTxt="SELECT NOMBRE, CANTIDAD, UNIDAD, ESTADO "
                +"FROM PRODUCTOS WHERE NOMBRE=? ";
        String[] argumentos=new String[]{nombre};
        try{
            Cursor cursor=db.rawQuery(sqlTxt,argumentos);
            cursor.moveToFirst();
            boolean estado=false;
            if (cursor.getInt(3)==1) estado=true;
            p=new Producto(cursor.getString(0),
                    cursor.getInt(1),
                    cursor.getString(2), estado);
        }catch (SQLException ex)
        {
            p=null;
        }
        return p;
    }
    public String cambiarEstado(Producto p)
    {
        int estadoInt;
        if(p.isEstado()) estadoInt=1;
        else estadoInt=0;
        String sqlTxt="UPDATE PRODUCTOS SET ESTADO=? "
                +"WHERE NOMBRE=? ";
        Object[] argumentos=new Object[]{estadoInt,p.getNombre()};
        try{
            getWritableDatabase().execSQL(sqlTxt,argumentos);
            return "Se cambió el estado correcatamente";
        }catch (SQLException ex)
        {
            return "No se pudo cambiar el estado del producto";
        }
    }
    public String eliminarComprados()
    {
        String sqlTxt="DELETE FROM PRODUCTOS WHERE ESTADO=0";
        try{
            getWritableDatabase().execSQL(sqlTxt);
            return "Se han eliminado los productos comprados";
        }catch (SQLException ex)
        {
            return "No se puede eliminar los productos";
        }
    }
}
