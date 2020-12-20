package no3ratii.mohammad.dev.app.notebook.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add_note.view.*
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.base.helper.ReplaceFragment
import no3ratii.mohammad.dev.app.notebook.model.Note
import no3ratii.mohammad.dev.app.notebook.viewModel.NoteViewModel

class FragmentAddNote : Fragment() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_add_note, container, false)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        initialize(view)
        return view

    }

    private fun initialize(view: View) {
        view.btnOk.setOnClickListener {
            insertNoteToDataBase(edtTitle.text.toString(),edtDesc.text.toString())
        }
    }

    private fun insertNoteToDataBase(title: String, desc: String) {
        if (checkForEmpty(title, desc)) {
            val note = Note(0,title, desc)
            noteViewModel.addNote(note)
            ReplaceFragment(FragmentNote(),childFragmentManager , R.id.rootAddNote).init()
        } else {
            Toast.makeText(context, "لطفا متن را وارد کنید", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkForEmpty(name: String, family: String): Boolean {
        return name.isNotEmpty() || family.isNotEmpty()
    }
}