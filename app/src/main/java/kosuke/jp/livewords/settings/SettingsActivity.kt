package kosuke.jp.livewords.settings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kosuke.jp.livewords.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        fragmentManager
                .beginTransaction()
                .replace(R.id.settings_container, SettingsFragment())
                .commit()
    }
}
