package kosuke.jp.livewords.db

/**
 * Created by kosuke on 2/8/18.
 */

const val DB_VERSION  = 1
const val DB_NAME     = "WordsDB"
const val TABLE_NAME  = "Words"
const val COL_ID      = "_id"
const val COL_WORD    = "word"
const val COL_MEANING = "meaning"
const val COL_TYPE    = "type"

val CREATE_TABLE =
        """
            CREATE TABLE $TABLE_NAME(
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_WORD TEXT NOT NULL,
            $COL_MEANING TEXT,
            $COL_TYPE TEXT);
        """.trimIndent()

val DROP_TABLE =
        "DROP TABLE IF EXISTS $TABLE_NAME"
