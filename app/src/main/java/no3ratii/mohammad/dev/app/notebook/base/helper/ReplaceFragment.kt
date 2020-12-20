package no3ratii.mohammad.dev.app.notebook.base.helper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/*replace fragment by bilder design patern*/
data class ReplaceFragment(
    //base fragment for replace
    val fragment: Fragment,
    //manager for bigin transaction
    var supportFragmentManager: FragmentManager,
    /**
     *xlm layout id for replace
     *note: cant use linierlayout becose this cant support replace
     */
    val layRoot: Int
) {
    private val newFragment: Fragment = fragment
    private val transaction =
        supportFragmentManager.beginTransaction()

    //start replace
    fun init(): ReplaceFragment {
        transaction.replace(layRoot, newFragment)
        transaction.commit()
        return this
    }

    /**
     *delete backstack values
     *backstack save fragments for return
     */
    fun clearBackStack(): ReplaceFragment {
        val count: Int = supportFragmentManager.backStackEntryCount
        for (i in 0 until count) {
            supportFragmentManager.popBackStackImmediate();
        }
        return this
    }

    /**
     * add fragment to back stack and use a name for callback
     */
    fun addToBackStack(): ReplaceFragment {
        transaction.addToBackStack("1")
        return this
    }
}