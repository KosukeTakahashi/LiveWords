package kosuke.jp.livewords.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import kosuke.jp.livewords.R

/**
 * Created by kosuke on 2/5/18.
 */
class SettingsFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences
                .registerOnSharedPreferenceChangeListener(this@SettingsFragment)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences
                .registerOnSharedPreferenceChangeListener(this@SettingsFragment)
    }

    override fun onSharedPreferenceChanged(sp: SharedPreferences?, key: String?) {
        // サービス再起動
        // TODO 18/02/16 : Implement 'Restart Service'
        // ↓ 動かない
//        stopService<LiveWords>()
//        startService<LiveWords>()
    }
}