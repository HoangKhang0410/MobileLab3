package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lab3.Contact;
import com.example.lab3.DatabaseHandler;
import com.example.lab3.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static WeakReference<MainActivity> weakActivity;
    // etc..
    public static MainActivity getmInstanceActivity() {
        return weakActivity.get();
    }

    ListView lvPerson;
    FloatingActionButton btnAdd;
    ArrayAdapter<Contact> adapter = null;

    DatabaseHandler db;
    List<Contact> contacts = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerAdapter studentAdapter;
    StudentDbHandler studentDB;
    List<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weakActivity = new WeakReference<>(MainActivity.this);
        setContentView(R.layout.bai1);
        bai1();
//        setContentView(R.layout.activity_main);
//        bai2();
    }

    private void bai1(){
        db = new DatabaseHandler(this);


        // Thêm liên hệ
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Khang", "0123456789"));
        db.addContact(new Contact("Khangg", "01234567899"));
        db.addContact(new Contact("Khanggg", "012345678999"));
        db.addContact(new Contact("Khangggg", "012345678999"));

        // Đọc các liên hệ
        Log.d("Reading: ", "Reading all contacts..");

        contacts = db.getAllContacts();

        lvPerson = (ListView) findViewById(R.id.lv_person);
        btnAdd = findViewById(R.id.btn_add);
        adapter = new ArrayAdapter<Contact>
                (this, android.R.layout.simple_list_item_1, contacts);
        lvPerson.setAdapter(adapter);
        for (Contact cn : contacts) {

            String log = "Id: " + cn.getId() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Ghi các liên hệ đến log
            Log.d("Name: ", log);

        }

        lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openEditDialog(contacts.get(i), i);

            }
        });

        lvPerson.setOnItemLongClickListener(new AdapterView
                .OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                db.deleteContact(contacts.get(arg2));
                contacts.remove(arg2);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });
    }

    private void bai2(){
        studentDB = new StudentDbHandler(this);


        // Thêm liên hệ
        Log.d("Insert: ", "Inserting ..");
        studentDB.addStudent(new Student("Khang", "CNTT2017"));
        studentDB.addStudent(new Student("Khangg", "CNTT2018"));
        studentDB.addStudent(new Student("Khanggg", "CNTT2019"));
        studentDB.addStudent(new Student("Khangggg", "CNTT2020"));

        // Đọc các liên hệ
        Log.d("Reading: ", "Reading all contacts..");

        students = studentDB.getAllStudents();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btn_add);
//        Button updateBtn = (Button) findViewById(R.id.updateBtn);
//        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
        studentAdapter = new RecyclerAdapter(this, students);
        recyclerView.setAdapter(studentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentAddDialog();
            }
        });
    }

    private void openEditDialog(Contact contact , int position) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        EditText etName = dialog.findViewById(R.id.et_name);
        EditText etPhone = dialog.findViewById(R.id.et_phone);
        Button btnCancel = dialog.findViewById(R.id.bt_cancel);
        Button btnConfirm = dialog.findViewById(R.id.bt_confirm);

        etName.setText(contact.getName());
        etPhone.setText(contact.getPhoneNumber());

        btnCancel.setOnClickListener(view -> dialog.dismiss());

        btnConfirm.setOnClickListener(view -> {
            if (etName.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty()){
                Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
            } else {
                //tvHello.setText("Hi " + etUsername.getText().toString());
                Contact contact1 = new Contact(position+1, etName.getText().toString(), etPhone.getText().toString());
                db.updateContact(contact1);
                contacts.set(position, contact1);
                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }

        });

        dialog.show();
    }

    public void openEditStudentDialog(Student student, int position){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.recycler_dialog);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        EditText etName = dialog.findViewById(R.id.et_name);
        EditText etClass = dialog.findViewById(R.id.et_className);
        Button btnCancel = dialog.findViewById(R.id.bt_cancel);
        Button btnConfirm = dialog.findViewById(R.id.bt_confirm);

        etName.setText(student.getName());
        etClass.setText(student.getClassName());

        btnCancel.setOnClickListener(view -> dialog.dismiss());

        btnConfirm.setOnClickListener(view -> {
            if (etName.getText().toString().isEmpty() || etClass.getText().toString().isEmpty()){
                Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
            } else {
                //tvHello.setText("Hi " + etUsername.getText().toString());
                Student student1 = new Student(position+1, etName.getText().toString(), etClass.getText().toString());
                studentDB.updateStudent(student1);
                students.set(position, student1);
                studentAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }

        });

        dialog.show();
    }



    private void openAddDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        EditText etName = dialog.findViewById(R.id.et_name);
        EditText etPhone = dialog.findViewById(R.id.et_phone);
        Button btnCancel = dialog.findViewById(R.id.bt_cancel);
        Button btnConfirm = dialog.findViewById(R.id.bt_confirm);


        btnCancel.setOnClickListener(view -> dialog.dismiss());

        btnConfirm.setOnClickListener(view -> {
            if (etName.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty()){
                Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
            } else {
                //tvHello.setText("Hi " + etUsername.getText().toString());
                Contact contact1 = new Contact(etName.getText().toString(), etPhone.getText().toString());
                db.addContact(contact1);
                contacts.add(contact1);
                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }

        });

        dialog.show();
    }

    private void openStudentAddDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.recycler_dialog);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        EditText etName = dialog.findViewById(R.id.et_name);
        EditText etClass = dialog.findViewById(R.id.et_className);
        Button btnCancel = dialog.findViewById(R.id.bt_cancel);
        Button btnConfirm = dialog.findViewById(R.id.bt_confirm);


        btnCancel.setOnClickListener(view -> dialog.dismiss());

        btnConfirm.setOnClickListener(view -> {
            if (etName.getText().toString().isEmpty() || etClass.getText().toString().isEmpty()){
                Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
            } else {
                //tvHello.setText("Hi " + etUsername.getText().toString());
                Student student1 = new Student(etName.getText().toString(), etClass.getText().toString());
                studentDB.addStudent(student1);
                students.add(student1);
                studentAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }

        });

        dialog.show();
    }
}