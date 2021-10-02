package com.example.appgcc.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.appgcc.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterDialogFragment extends DialogFragment {

    private EditText etFirstName, etLastName, etPhone, etEmail, etPassword;
    private TextInputLayout tilFirstName, tilLastName, tilPhone, tilEmail, tilPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_register, container, false);

        etFirstName = (EditText) view.findViewById(R.id.etFirstName);
        etLastName = (EditText) view.findViewById(R.id.etLastName);
        etPhone = (EditText) view.findViewById(R.id.etPhone);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        tilFirstName = (TextInputLayout) view.findViewById(R.id.tilFirstName);
        tilLastName = (TextInputLayout) view.findViewById(R.id.tilLastName);
        tilPhone = (TextInputLayout) view.findViewById(R.id.tilPhone);
        tilEmail = (TextInputLayout) view.findViewById(R.id.tilEmail);
        tilPassword = (TextInputLayout) view.findViewById(R.id.tilPassword);


        return view;
    }

    private void ValidateForm() {
        if (!validateEditText(etFirstName, tilFirstName, R.string.error_first_name)) {
            return;
        }
//        if (!validateLastName()) {
//            return;
//       }
        if (!validateEditText(etLastName, tilLastName, R.string.error_last_name)) {
            return;
        }

        if (!validateEditText(etPhone, tilPhone, R.string.error_phone)) {
            return;
        }

        if (!validateEditText(etEmail, tilEmail, R.string.error_email)) {
            return;
        }

        if (!validateEditText(etPassword, tilPassword, R.string.error_password)) {
            return;
        }

        Toast.makeText(getContext(), "Registrado con exito", Toast.LENGTH_LONG).show();
    }

    private Boolean validateFirstName() {
        if (etFirstName.getText().toString().trim().isEmpty()) {
            tilFirstName.setError(getString(R.string.error_first_name));
            return false;
        } else {
            tilFirstName.setErrorEnabled(false);
        }

        return true;
    }

    private Boolean validateEditText(EditText editText, TextInputLayout textInputLayout, int errorString) {
        if (editText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError(getString(errorString));
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }
}
