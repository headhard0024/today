package no3ratii.mohammad.dev.app.notebook.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.base.helper.ReplaceFragment
import no3ratii.mohammad.dev.app.notebook.view.activity.ActivityMain
import no3ratii.mohammad.dev.app.notebook.view.adapter.ListAdapter
import no3ratii.mohammad.dev.app.notebook.model.intrface.IRecyclerPosition
import no3ratii.mohammad.dev.app.notebook.viewModel.ListViewModel




class FragmentList : Fragment() {

    companion object {
        private lateinit var contactBackPressListener: IRecyclerPosition
        fun setContactOnBackPress(contactBackPressListener: IRecyclerPosition) {
            this.contactBackPressListener = contactBackPressListener
        }
    }

    private lateinit var userViewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        userViewModel = ViewModelProvider(this).get(ListViewModel::class.java)


        val adapter = ListAdapter(activity = ActivityMain(), lifecycleOwner = this)
        val recycler = view.recRoot
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        userViewModel.readAllUser.observe(viewLifecycleOwner, Observer { userList ->
            adapter.getUserList(userList)
        })

        view.fab.setOnClickListener {
            ReplaceFragment(FragmentAdd(), childFragmentManager, R.id.layRootList)
                .addToBackStack()
                .init()
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