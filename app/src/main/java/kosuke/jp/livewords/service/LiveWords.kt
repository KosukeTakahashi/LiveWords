package kosuke.jp.livewords.service

import android.graphics.Color
import android.graphics.Paint
import android.preference.PreferenceManager
import android.service.wallpaper.WallpaperService
import android.view.MotionEvent
import android.view.SurfaceHolder
import kosuke.jp.livewords.R
import kosuke.jp.livewords.data.WordEntry
import kosuke.jp.livewords.db.TABLE_NAME
import kosuke.jp.livewords.db.WordsDBOpenHelper
import kosuke.jp.livewords.db.WordsParser
import kosuke.jp.livewords.settings.DEFAULT_BG_COLOR
import kosuke.jp.livewords.settings.DEFAULT_TAP_TO_RELOAD
import kosuke.jp.livewords.settings.DEFAULT_TX_COLOR
import kosuke.jp.livewords.settings.DEFAULT_TX_SIZE
import org.jetbrains.anko.db.select
import java.util.*

/**
 * Created by kosuke on 2/5/18.
 */
class LiveWords : WallpaperService() {
    private val random = Random()

    override fun onCreateEngine(): Engine =
        LiveWordsEngine()

    inner class LiveWordsEngine : Engine() {
        // サービス開始時に引数として渡すべき？
        // TODO 18/02/16 : Consider it

        private lateinit var helper: WordsDBOpenHelper
        private lateinit var dataList: List<WordEntry>
        private var bgColor = DEFAULT_BG_COLOR
        private var txColor = DEFAULT_TX_COLOR
        private var txSize  = DEFAULT_TX_SIZE
        private var tapToReload = DEFAULT_TAP_TO_RELOAD

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            setTouchEventsEnabled(true)

            helper = WordsDBOpenHelper(this@LiveWords)
            dataList = helper.readableDatabase.select(TABLE_NAME).parseList(WordsParser())

            val pref = PreferenceManager.getDefaultSharedPreferences(this@LiveWords)
            bgColor = pref.getString(getString(R.string.key_bg_color), DEFAULT_BG_COLOR)
            txColor = pref.getString(getString(R.string.key_tx_color), DEFAULT_TX_COLOR)
            txSize = pref.getString(getString(R.string.key_tx_size), DEFAULT_TX_SIZE.toString()).toFloat()
            tapToReload = pref.getBoolean(getString(R.string.key_tap_to_reload), DEFAULT_TAP_TO_RELOAD)
        }

        override fun onTouchEvent(event: MotionEvent) {
            if (tapToReload) {
                if (event.action == MotionEvent.ACTION_UP) {
                    val x = random.nextInt(dataList.size)
                    val w = dataList[x].word
                    val m = dataList[x].meaning
                    val t = dataList[x].type
                    val text = "$w = ${if (t.isNotEmpty()) "[$t]" else ""} $m"
                    drawText(text, event.x, event.y)
                }
            }
            super.onTouchEvent(event)
        }

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
            drawText("FIRST!", 10.0F, 10.0F)
        }

        private fun drawText(text: String, x: Float, y: Float) {
            // TODO 18/02/16 : Implement 'Background Image'

            val canvas = surfaceHolder.lockCanvas()
            val paint = Paint()

            canvas.drawColor(Color.parseColor(bgColor))
            paint.textSize = txSize
            paint.color = Color.parseColor(txColor)
            canvas.drawText(text, x, y, paint)
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }
}