package no3ratii.mohammad.dev.app.notebook.view.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_update.btnUpdate
import kotlinx.android.synthetic.main.activity_update.edtDesc
import kotlinx.android.synthetic.main.activity_update.edtTitle
import kotlinx.android.synthetic.main.activity_update.imgDelete
import kotlinx.android.synthetic.main.activity_update.txtToolbarTitle
import kotlinx.android.synthetic.main.activity_update_note.*
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.base.helper.CirculeRevealHelper
import no3ratii.mohammad.dev.app.notebook.model.Note
import no3ratii.mohammad.dev.app.notebook.viewModel.NoteViewModel

class ActivityUpdateNote : AppCompatActivity() {

    var note: Note? = null
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        //
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        val noteId = intent.getIntExtra("id", 1)
        note = noteViewModel.singleNote(noteId)

        initialize()
    }

    private fun initialize() {
        //init layout logic by note resive from activity
        initLayoutLogic(note!!)

        //set on click values and call listeners
        initOnClickListener()

    }

    private fun initOnClickListener() {
        imgDelete.setOnClickListener {
            noteViewModel.deleteNote(note!!)
            finish()
        }


        imgBack.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CirculeRevealHelper(it, startcolor = R.color.whiteCc, defaultColor = R.color.whiteLite)
                    .init()
            }
            finish()
        }

        btnUpdate.setOnClickListener {
            val note = Note(note!!.id ,edtTitle.text.toString() , edtDesc.text.toString() )
            noteViewModel.updateNote(note)
            finish()
        }
    }

    private fun initLayoutLogic(note: Note) {
        edtTitle.setText(note.title)
        edtDesc.setText(note.desc)
        txtToolbarTitle.text = note.title

    }
}