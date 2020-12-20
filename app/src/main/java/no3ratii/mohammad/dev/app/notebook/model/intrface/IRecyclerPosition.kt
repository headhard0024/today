package no3ratii.mohammad.dev.app.notebook.model.intrface

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

interface IRecyclerPosition {
    fun onBackPress(recyclerView: RecyclerView, dx:Int, dy:Int , fab: FloatingActionButton)
}