package kosuke.jp.livewords.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by kosuke on 2/8/18.
 */
class WordsDBOpenHelper(context: Context)
    : ManagedSQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.run {
            createTable(
                    tableName   = TABLE_NAME,
                    ifNotExists = true,
                    columns     = *arrayOf(
                            COL_WORD to TEXT,
                            COL_MEANING to TEXT,
                            COL_TYPE to TEXT)) }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        //空実装
    }
}