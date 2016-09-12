package id.sch.smktelkom_mlg.tugas01.xirpl1009.zbio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    EditText user, email, password, jAnak;
    RadioGroup rgJK, rgStatus;
    CheckBox cbInternet, cbRelasi, cbIklan;
    Spinner asalProv, asalKot;
    Button submit, clear;
    TextView tvTK, tvHasil;
    RadioButton rbBM;
    String[][] arKota = {
            {"Jakarta Barat", "Jakarta Pusat", "Jakarta Selatan", "Jakarta Timur", "Jakarta Utara"},
            {"Bandung", "Cirebon", "Bekasi"},
            {"Semarang", "Magelang", "Surakarta"},
            {"Surabaya", "Malang", "Blitar"},
            {"Denpasar"}
    };
    ArrayList<String> listKota = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.editTextNama);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        jAnak = (EditText) findViewById(R.id.editTextJAnak);
        rgJK = (RadioGroup) findViewById(R.id.radioGroupJK);
        rgStatus = (RadioGroup) findViewById(R.id.radioGroupS);
        cbInternet = (CheckBox) findViewById(R.id.checkBoxInternet);
        cbRelasi = (CheckBox) findViewById(R.id.checkBoxRelasi);
        cbIklan = (CheckBox) findViewById(R.id.checkBoxIklan);
        asalProv = (Spinner) findViewById(R.id.spinnerProv);
        asalKot = (Spinner) findViewById(R.id.spinnerKot);
        submit = (Button) findViewById(R.id.buttonSubmit);
        clear = (Button) findViewById(R.id.buttonClear);
        tvTK = (TextView) findViewById(R.id.textViewTK);
        tvHasil = (TextView) findViewById(R.id.textViewHasil);
        rbBM = (RadioButton) findViewById(R.id.radioButtonBM);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listKota);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        asalKot.setAdapter(adapter);

        asalProv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listKota.clear();
                listKota.addAll(Arrays.asList(arKota[position]));
                adapter.notifyDataSetChanged();
                asalKot.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rgStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonBM) {
                    findViewById(R.id.editTextJAnak).setVisibility(View.GONE);
                }
                else {
                    findViewById(R.id.editTextJAnak).setVisibility(View.VISIBLE);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSubmit();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClear();
            }
        });
    }

    private void doClear() {
        user.setText("");
        email.setText("");
        password.setText("");
        jAnak.setText("");
        tvHasil.setText("");
        tvTK.setVisibility(View.GONE);
    }

    private void doSubmit() {
        boolean valid = true;
        String hasil = "";
        String jk = null;
        String staatus = null;
        String username = user.getText().toString();
        String surel = email.getText().toString();
        String pass = password.getText().toString();
        String anak = jAnak.getText().toString();
        String kota = asalKot.getSelectedItem().toString();
        String prov = asalProv.getSelectedItem().toString();

        if (cbRelasi.isChecked()) hasil += "Internet\n";
        if (cbIklan.isChecked()) hasil += "Iklan\n";
        if (cbRelasi.isChecked()) hasil += "Relasi";
        if (rgJK.getCheckedRadioButtonId() != -1) {
            RadioButton rbJK = (RadioButton) findViewById(rgJK.getCheckedRadioButtonId());
            jk = rbJK.getText().toString();
        }
        if (rgStatus.getCheckedRadioButtonId() != -1) {
            RadioButton rbS = (RadioButton) findViewById(rgStatus.getCheckedRadioButtonId());
            staatus = rbS.getText().toString();
        }
        if (jk == null) {
            tvHasil.setText("Belum mengisi jenis kelamin");
            valid = false;
        }
        if (staatus == null) {
            tvHasil.setText("Belum mengisi status");
            valid = false;
        }
        if (username.isEmpty()) {
            user.setError("Username belum diisi");
            valid = false;
        }
        if (surel.isEmpty()) {
            email.setError("Email belum diisi");
            valid = false;
        }
        if (pass.isEmpty()) {
            password.setError("Password belum diisi");
            valid = false;
        }
        if (username.length() <= 3) {
            user.setError("Nama minimal 3 karakter");
        }
        if (pass.length() <= 6) {
            password.setError("Password minimal 6 karakter");
        }
        if (surel.length() <= 4) {
            email.setError("Email minimal 4 karakter");
        }
        if (valid) {
            tvTK.setVisibility(View.VISIBLE);
            tvHasil.setText("Nama anda " + username + " berhasil mendaftar dengan informasi sebagai berikut\n\n" + "Email         : " + surel + "\nPassword : " + pass + "\n\nJenis Kelamin : " + jk + "\nStatus               : " + staatus + "\nJumlah Anak   : " + anak + "\n\nAsal Provinsi : " + prov + "\nAsal Kota       : " + kota + "\n\nAnda mengetahui program ini dari\n" + hasil);
        }
    }
}