package no3ratii.mohammad.dev.app.notebook.view.adapter

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_first_item.view.*
import kotlinx.android.synthetic.main.user_item.view.imgCheck
import kotlinx.android.synthetic.main.user_item.view.layRoot
import kotlinx.android.synthetic.main.user_item.view.layTime
import kotlinx.android.synthetic.main.user_item.view.txtDesc
import kotlinx.android.synthetic.main.user_item.view.txtEndTime
import kotlinx.android.synthetic.main.user_item.view.txtId
import kotlinx.android.synthetic.main.user_item.view.txtStartTime
import kotlinx.android.synthetic.main.user_item.view.txtTitle
import no3ratii.mohammad.dev.app.notebook.viewModel.ListViewModel
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.base.helper.CirculeRevealHelper
import no3ratii.mohammad.dev.app.notebook.base.helper.PopupDialog
import no3ratii.mohammad.dev.app.notebook.model.User
import no3ratii.mohammad.dev.app.notebook.view.activity.ActivityUpdate
import no3ratii.mohammad.dev.app.notebook.view.activity.ActivityMain
import no3ratii.mohammad.dev.app.notebook.model.intrface.IPopupClick


class ListAdapter(val activity: ActivityMain, val lifecycleOwner: ViewModelStoreOwner) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //initialize
    private lateinit var userViewModel: ListViewModel
    private var userList = emptyList<User>()

    fun getUserList(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //set viewModel
        userViewModel = ViewModelProvider(lifecycleOwner).get(ListViewModel::class.java)

        // items layout
        val myHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        //first item
        val firstItem =
            LayoutInflater.from(parent.context).inflate(R.layout.user_first_item, parent, false)
        //end item layout
        val endtItem =
            LayoutInflater.from(parent.context).inflate(R.layout.user_end_item, parent, false)

        // set view type for call in bind
        return when (viewType) {
            // FirstItemViewHolder view holder for first item
            0 -> FirstItemViewHolder(firstItem)
            // EndItemViewHolder view holder for end item
            1 -> EndItemViewHolder(endtItem)
            // MyViewHolder view holder items
            else -> MyViewHolder(myHolder)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //logic for set binding valuse by use item view type
        when (holder.itemViewType) {
            0 -> {
                val firstItem = holder as FirstItemViewHolder
                firstItemLogic(firstItem)
            }
            1 -> {
                val endItem = holder as EndItemViewHolder
                endItemLogic(endItem)
            }
            else -> {
                val myViewHolder = holder as MyViewHolder
                //position -1 for dont use end item
                val item = userList[position - 1]
                //set layout logic for show and replase value
                laoyutLogic(myViewHolder, item, position)

            }
        }
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    class FirstItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    class EndItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun getItemCount(): Int {
        // plus two for use first and end item in recycler
        return userList.size + 2
    }

    //set to items value viewType for use in CreateviewHlder and use other viewHolders
    override fun getItemViewType(position: Int): Int {
        return when {
            position < 1 -> {
                0
            }
            position == userList.size + 1 -> {
                1
            }
            else -> {
                2
            }
        }
    }

    private fun endItemLogic(endItem: EndItemViewHolder) {
        if (userList.size >= 8){
            endItem.itemView.layRoot.visibility = View.VISIBLE
        }else{
            endItem.itemView.layRoot.visibility = View.GONE
        }
    }

    private fun firstItemLogic(firstItem: FirstItemViewHolder) {

    }

    private fun laoyutLogic(holder: RecyclerView.ViewHolder, item: User, tempPosition: Int) {
        holder.itemView.txtId.text = "" + tempPosition
        holder.itemView.txtTitle.text = item.title
        holder.itemView.txtDesc.text = item.desc
        holder.itemView.txtStartTime.text = item.startTiem
        holder.itemView.txtEndTime.text = item.endTime

        if (item.startTiem.contains("0:0") && item.endTime.contains("0:0")) {
            holder.itemView.layTime.visibility = View.INVISIBLE
        } else {
            holder.itemView.layTime.visibility = View.VISIBLE
        }

        if (item.checked) {
            setOnImg(holder)
        } else {
            setOffImg(holder)
        }

        holder.itemView.layRoot.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CirculeRevealHelper(it, startcolor = R.color.whiteF1, defaultColor = R.color.white)
                    .init()
            }
            val intent = Intent(it.context, ActivityUpdate::class.java)
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
                "اطمینان دارید ؟" + item.title + "آیا از حذف ",
                "بله",
                "بیخیال"
            )
                .show(manager, "deleteDialog")
            PopupDialog.setClicked(object : IPopupClick {
                override fun onYesClick() {
                    userViewModel.deleteUser(item)
                    notifyDataSetChanged()
                }

                override fun onNoClick() {
                }
            })
            true
        }

        holder.itemView.imgCheck.setOnClickListener {
            if (item.checked) {
                upDateUser(item.id, item.title, item.desc, item.startTiem, item.endTime, false)
                setOffImg(holder)
            } else {
                upDateUser(item.id, item.title, item.desc, item.startTiem, item.endTime, true)
                setOnImg(holder)
            }
        }
    }

    private fun setOffImg(holder: RecyclerView.ViewHolder) {
        holder.itemView.imgCheck.setImageResource(R.drawable.ic_check_off)
    }

    private fun setOnImg(holder: RecyclerView.ViewHolder) {
        holder.itemView.imgCheck.setImageResource(R.drawable.ic_check_on)
    }

    private fun upDateUser(
        userId: Int,
        name: String,
        family: String,
        startTime: String,
        endTime: String,
        checked: Boolean
    ) {
        val user = User(userId, name, family, startTime, endTime, checked)
        userViewModel.updateUser(user)
    }
}