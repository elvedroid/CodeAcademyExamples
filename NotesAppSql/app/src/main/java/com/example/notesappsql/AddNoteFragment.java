package com.example.notesappsql;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.notesappsql.model.Note;

import java.util.Calendar;

public class AddNoteFragment extends DialogFragment {

    public interface AddNoteListener {
        void onNoteAdded(long id);
    }

    public AddNoteListener addNoteListener;

    public AddNoteFragment(AddNoteListener addNoteListener) {
        this.addNoteListener = addNoteListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_add_note, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.add_new_note_title)
                .setView(rootView)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = rootView.findViewById(R.id.etNewNote);
                        String noteDescription = editText.getText().toString();
                        Note noteToAdd = new Note(noteDescription);

                        DatabaseHelper dbHelper = AppSingleton.getInstance(getContext()).getDbHelper();

                        long id = dbHelper.insertNote(noteToAdd);

                        if (addNoteListener != null) {
                            addNoteListener.onNoteAdded(id);
                        }

                    }
                });
        return builder.create();
    }
}
