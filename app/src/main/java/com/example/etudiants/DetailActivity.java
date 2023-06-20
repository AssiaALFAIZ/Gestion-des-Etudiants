package com.example.etudiants;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    EditText nom,classe;
    Button mod,sup;
    int position ;

    Params p = new Params();
    HashMap<String,String> m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nom = findViewById(R.id.nom);
        classe = findViewById(R.id.classe);
        mod = findViewById(R.id.modifier);
        sup = findViewById(R.id.supprimer);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("position");

        }
        HashMap<String,String> m = p.values.get(position);
        nom.setText(m.get("nom"));
        classe.setText(m.get("classe"));

        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newNom = nom.getText().toString();
                String newClasse = classe.getText().toString();

                // Vérifier si les nouvelles informations correspondent à un autre étudiant existant
                boolean studentExists = false;
                for (HashMap<String, String> existingStudent : p.values) {
                    if (existingStudent != m && existingStudent.get("nom").equals(newNom) && existingStudent.get("classe").equals(newClasse)) {
                        studentExists = true;
                        break;
                    }
                }

                if (studentExists) {
                    // Un autre étudiant avec les mêmes informations existe déjà, afficher une alerte
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                    builder.setTitle("Étudiant déjà existant");
                    builder.setMessage("Un autre étudiant avec les mêmes informations existe déjà.");
                    builder.setPositiveButton("OK", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    // Mettre à jour les informations de l'étudiant
                    m.put("nom", newNom);
                    m.put("classe", newClasse);
                    Intent i = new Intent(getApplicationContext(), StudentsActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.values.remove(position);
                Intent i = new Intent(getApplicationContext(),StudentsActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}