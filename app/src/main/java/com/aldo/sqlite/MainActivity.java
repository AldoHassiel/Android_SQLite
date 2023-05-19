package com.aldo.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editID, editNombre, editDireccion;
    RadioButton rbF, rbM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editID = (EditText) findViewById(R.id.editID);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDireccion = (EditText) findViewById(R.id.editDireccion);
        rbF = (RadioButton) findViewById(R.id.rbF);
        rbM = (RadioButton) findViewById(R.id.rbM);

        editID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    Administrador admin = new Administrador
                            (getApplicationContext(), "Administracion", null, 1 );
                    SQLiteDatabase BD = admin.getWritableDatabase();
                    Integer ID = Integer.parseInt(editID.getText().toString());
                    Cursor fila = BD.rawQuery(
                            "SELECT NOMBRE, DIRECCION, GENERO FROM PERSONAS WHERE " +
                                    "ID=" + ID, null);
                    if(fila.moveToFirst()){
                        editID.setEnabled(false);
                        editNombre.setText(fila.getString(0));
                        editDireccion.setText(fila.getString(1));
                        if(fila.getString(2).equals("F")){
                            rbF.setChecked(true);
                        }else{
                            rbM.setChecked(true);
                        }
                        BD.close();
                    }
                }
            }
        });
    }

    public void agregar(View v) {
        Administrador admin = new Administrador(getApplicationContext(),
                "Administracion", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Integer ID = Integer.parseInt(editID.getText().toString());
        String nombre = editNombre.getText().toString();
        String direccion = editDireccion.getText().toString();
        String genero = rbF.isChecked() ? "F" : "M";

        ContentValues registro = new ContentValues();
        registro.put("ID" , ID);
        registro.put("NOMBRE" , nombre);
        registro.put("DIRECCION", direccion);
        registro.put("GENERO" , genero);

        if (BD.insert("PERSONAS", null, registro) != -1){
            editID.setText("");
            editNombre.setText("");
            editDireccion.setText("");
            rbF.setChecked(false);
            rbM.setChecked(false);
            Toast.makeText(this, "éxito", Toast.LENGTH_SHORT).show();
            BD.close();
        }else{
            Toast.makeText(this, "No éxito", Toast.LENGTH_SHORT).show();
        }

    }
    public void modificar(View v){
        Administrador admin = new Administrador(getApplicationContext(),
                "Administracion", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Integer ID = Integer.parseInt(editID.getText().toString());
        String nombre = editNombre.getText().toString();
        String direccion = editDireccion.getText().toString();
        String genero = rbF.isChecked()? "F": "M";

        ContentValues registro = new ContentValues();

        //Actualizar regristro
        registro.put("NOMBRE", nombre);
        registro.put("DIRECCION", direccion);
        registro.put("GENERO", genero);

        int cant = BD.update("PERSONAS", registro, "ID=" + ID, null);
        BD.close();
        if(cant == 1){
            editNombre.setText(""); editDireccion.setText("");rbF.setChecked(true);
            editID.setText("");editID.setEnabled(true);
            Toast.makeText(this, "Datos de la persona modificado con éxito", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "No se logró modificar los datos", Toast.LENGTH_SHORT).show();
        }
    }
    public void eliminar(View v){
        Administrador admin = new Administrador(getApplicationContext(),
                "Administracion", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();
        Integer ID = Integer.parseInt(editID.getText().toString());
        int cant = BD.delete("PERSONAS", "ID=" + ID, null);
        BD.close();
        if (cant == 1){
            Toast.makeText(this, "Registro de persona eliminado", Toast.LENGTH_SHORT).show();
            editNombre.setText(""); editDireccion.setText(""); rbF.setChecked(true);
            editID.setText("");
            editID.setEnabled(true);
        }else {
            Toast.makeText(this, "Ocurrio un error al intentar eliminar"+ "el registro",
                    Toast.LENGTH_SHORT).show();
        }

    }
}