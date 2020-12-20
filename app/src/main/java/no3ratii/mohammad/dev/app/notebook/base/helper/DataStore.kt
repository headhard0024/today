package no3ratii.mohammad.dev.app.notebook.base.helper

import androidx.datastore.createDataStore
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import no3ratii.mohammad.dev.app.notebook.base.G

object DataStore {

    val dataStore = G.context.createDataStore("sharid_prifranses")

    fun setValue(value: String, key: String) {
        GlobalScope.launch {
            dataStore.edit {
                it[preferencesKey<String>(key)] = value
            }
        }
    }


    fun getValue(key: String): Flow<String?> {
        val data = dataStore.data.map { pre ->
            pre[preferencesKey<String>(key)] ?: ""
        }
        return data
    }
}