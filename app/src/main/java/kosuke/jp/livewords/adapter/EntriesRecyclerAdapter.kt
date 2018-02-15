package kosuke.jp.livewords.adapter

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kosuke.jp.livewords.R
import kosuke.jp.livewords.data.WordEntry

/**
 * Created by kosuke on 2/5/18.
 */
class EntriesRecyclerAdapter(
        private val data: MutableList<WordEntry>,
        private val onItemClickListener: (Int) -> Unit
        ) : RecyclerView.Adapter<EntriesRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val word = v.findViewById<TextView>(R.id.entry_word)
        val meaning = v.findViewById<TextView>(R.id.entry_meaning)
        val type = v.findViewById<TextView>(R.id.entry_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.word_entry_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.word.text = data[position].word
        holder.meaning.text = data[position].meaning
        holder.type.text = data[position].type.first().toString()
//        holder.type.background = genBackground(data[position].color)
        holder.itemView.setOnClickListener { onItemClickListener(position) }
    }

    override fun getItemCount(): Int =
            data.size

    private fun genBackground(color: Int): Drawable {
        val typeBg = GradientDrawable()
        typeBg.setStroke(5, color)
        typeBg.setColor(color)
        typeBg.cornerRadius = 10.0F
        typeBg.setSize(32, 32)
        return typeBg
    }

    fun addWord(word: WordEntry) {
        this.data += word
        notifyDataSetChanged()
    }

    fun removeWord(position: Int) {
        this.data.removeAt(position)
        this.notifyItemRemoved(position)
        this.notifyItemRangeChanged(position, itemCount)
        this.notifyDataSetChanged()
    }

    fun getItem(position: Int) =
            this.data[position]
}
