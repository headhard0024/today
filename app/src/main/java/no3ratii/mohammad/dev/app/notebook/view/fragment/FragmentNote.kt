package no3ratii.mohammad.dev.app.notebook.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_list.view.fab
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.base.G
import no3ratii.mohammad.dev.app.notebook.base.helper.ReplaceFragment
import no3ratii.mohammad.dev.app.notebook.model.intrface.IRecyclerPosition
import no3ratii.mohammad.dev.app.notebook.view.activity.ActivityMain
import no3ratii.mohammad.dev.app.notebook.view.adapter.ListAdapter
import no3ratii.mohammad.dev.app.notebook.view.adapter.NoteAdapter
import no3ratii.mohammad.dev.app.notebook.viewModel.ListViewModel
import no3ratii.mohammad.dev.app.notebook.viewModel.NoteViewModel

class FragmentNote : Fragment() {
    private lateinit var noteViewModel: NoteViewModel

    companion object {
        private lateinit var contactBackPressListener: IRecyclerPosition
        fun setContactOnBackPress(contactBackPressListener: IRecyclerPosition) {
            this.contactBackPressListener = contactBackPressListener
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_note, container, false)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val adapter = NoteAdapter(activity = ActivityMain(), lifecycleOwner = this)
        val recycler = view.recRoot
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(context, 2,RecyclerView.VERTICAL , false)

        noteViewModel.readAllNote.observe(viewLifecycleOwner, Observer {
            adapter.getNoteList(it)
        })

        view.fab.setOnClickListener {
            ReplaceFragment(FragmentAddNote(),childFragmentManager , R.id.noteRoot).init()
        }

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                contactBackPressListener.onBackPress(recyclerView,dx,dy , fab)
            }
        })
        return view
    }
}