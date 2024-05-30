package igor.second.riddle.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data_store")

class DataStoreManager(val context: Context){

    suspend fun saveSettings(settingData: SettingData){
        context.dataStore.edit { pref ->
            pref [intPreferencesKey("game_level")] = settingData.level
            pref [intPreferencesKey("game_score")] = settingData.score
            pref [intPreferencesKey("game_progress")] = settingData.progress
            pref [booleanPreferencesKey("game_sound")] = settingData.checkedState1
            pref [booleanPreferencesKey("game_music")] = settingData.checkedState2
        }
    }
    fun getSettings() = context.dataStore.data.map { pref ->
        return@map SettingData(
            pref[intPreferencesKey("game_level")] ?: 1,
            pref[intPreferencesKey("game_score")] ?: 0,
            pref[intPreferencesKey("game_progress")] ?: 0,
            pref[booleanPreferencesKey("game_sound")] ?: true,
            pref[booleanPreferencesKey("game_music")] ?: true
        )
    }
}

data class SettingData(
    val level: Int,
    val score: Int,
    val progress: Int,
    val checkedState1: Boolean,
    val checkedState2: Boolean
)