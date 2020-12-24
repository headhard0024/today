package no3ratii.mohammad.dev.app.notebook.view.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item.view.*
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.base.helper.CirculeRevealHelper
import no3ratii.mohammad.dev.app.notebook.base.helper.PopupDialog
import no3ratii.mohammad.dev.app.notebook.model.Note
import no3ratii.mohammad.dev.app.notebook.view.activity.ActivityMain
import no3ratii.mohammad.dev.app.notebook.model.intrface.IPopupClick
import no3ratii.mohammad.dev.app.notebook.view.activity.ActivityUpdate
import no3ratii.mohammad.dev.app.notebook.view.activity.ActivityUpdateNote
import no3ratii.mohammad.dev.app.notebook.viewModel.NoteViewModel


class NoteAdapter(val activity: ActivityMain, val lifecycleOwner: ViewModelStoreOwner) :
    RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {

    //initialize
    private lateinit var noteViewModel: NoteViewModel
    private var noteList = emptyList<Note>()
    fun getNoteList(noteList: List<Note>) {
        this.noteList = noteList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        noteViewModel = ViewModelProvider(lifecycleOwner).get(NoteViewModel::class.java)
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tempPosition = position + 1
        val item = noteList[position]

        //set layout logic for show and replase value
        LayoutLogic(holder, item, tempPosition)

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
        }
    }

    private fun LayoutLogic(holder: MyViewHolder, item: Note, tempPosition: Int) {

        holder.itemView.txtTitle.text = item.title
        holder.itemView.txtDesc.text = item.desc


//        holder.itemView.imgBackRed.setOnClickListener {
//            holder.itemView.layRoot.setBackgroundColor(holder.itemView.resources.getColor(R.color.red))
//        }

        holder.itemView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CirculeRevealHelper(it, startcolor = R.color.whiteF1, defaultColor = R.color.white)
                    .init()
            }
            val intent = Intent(it.context, ActivityUpdateNote::class.java)
            intent.putExtra("id", item.id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            it.context.startActivity(intent)
            activity.finish()
        }

        holder.itemView.layRoot.setOnLongClickListener { view ->
            val manager: FragmentManager =
                (view.context as AppCompatActivity).supportFragmentManager
            PopupDialog.newInstance(
                "حذف",
                "آیا از حذف " + item.title + " اطمینان دارید ؟",
                "بله",
                "بیخیال"
            )
                .show(manager, "deleteDialog")
            PopupDialog.setClicked(object : IPopupClick {
                override fun onYesClick() {
                    noteViewModel.deleteNote(item)
                    notifyDataSetChanged()
                }
                override fun onNoClick() {
                }
            })
            true
        }
    }
}