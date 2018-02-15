package kosuke.jp.livewords.db

import kosuke.jp.livewords.data.WordEntry
import org.jetbrains.anko.db.MapRowParser

/**
 * Created by kosuke on 2/8/18.
 */
class WordsParser : MapRowParser<WordEntry> {
    override fun parseRow(columns: Map<String, Any?>): WordEntry =
            WordEntry(
                    columns[COL_WORD]    as String,
                    columns[COL_MEANING] as String,
                    columns[COL_TYPE]    as String)
}
