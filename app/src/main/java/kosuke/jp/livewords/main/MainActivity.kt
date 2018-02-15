package kosuke.jp.livewords.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kosuke.jp.livewords.R
import kosuke.jp.livewords.adapter.EntriesRecyclerAdapter
import kosuke.jp.livewords.data.WordEntry
import kosuke.jp.livewords.db.*
import kosuke.jp.livewords.dialog.AddEntryDialog
import kosuke.jp.livewords.dialog.AddEntryDialogListener
import kosuke.jp.livewords.settings.SettingsActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class MainActivity : AppCompatActivity(), AddEntryDialogListener {
    private val dbHelper = WordsDBOpenHelper(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.action_settings -> {
                    val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onDialogPositiveClick(word: String, meaning: String, type: String) {
        val adapter = entries_recycler.adapter as EntriesRecyclerAdapter
        val helper = WordsDBOpenHelper(this@MainActivity)
        val wordEntry = WordEntry(word, meaning, type)
        helper.use {
            insert(TABLE_NAME, COL_WORD to word, COL_MEANING to meaning, COL_TYPE to type)
        }
        adapter.addWord(wordEntry)
    }

    override fun onDialogNegativeClick(dialog: AddEntryDialog) {
        dialog.dialog.cancel()
    }

    private fun init() {
        fab.setOnClickListener {
            val fragment = AddEntryDialog()
            fragment.show(fragmentManager, null)
        }

        val dataList = dbHelper.readableDatabase.select(TABLE_NAME).parseList(WordsParser()).toMutableList()
        val adapter = EntriesRecyclerAdapter(
                data = dataList,
                onItemClickListener = { pos ->
                    Toast.makeText(this, "Item $pos Clicked!", Toast.LENGTH_LONG).show()
                })
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) =
                    false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val adp = entries_recycler.adapter as EntriesRecyclerAdapter
                dbHelper.use {
                    val item = adp.getItem(pos)
                    delete(
                            TABLE_NAME,
                            "$COL_WORD = {word} AND $COL_MEANING = {meaning} AND $COL_TYPE = {type}",
                            "word" to item.word, "meaning" to item.meaning, "type" to item.type
                    )
                }
                adp.removeWord(pos)
            }
        }

        entries_recycler.layoutManager = LinearLayoutManager(this@MainActivity)
        entries_recycler.addItemDecoration(ItemDecoration(this@MainActivity))
        entries_recycler.adapter = adapter
        ItemTouchHelper(callback).attachToRecyclerView(entries_recycler)
    }
}
