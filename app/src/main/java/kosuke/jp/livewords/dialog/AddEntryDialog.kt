package kosuke.jp.livewords.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import kosuke.jp.livewords.R

/**
 * Created by kosuke on 2/5/18.
 */

interface AddEntryDialogListener {
    fun onDialogPositiveClick(word: String, meaning: String, type: String)
    fun onDialogNegativeClick(dialog: AddEntryDialog)
}

class AddEntryDialog : DialogFragment() {
    lateinit var mListener: AddEntryDialogListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mListener = activity as AddEntryDialogListener
        }
        catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement AddEntryDialogListener.")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_entry, null)
        builder.setView(view)
               .setPositiveButton(R.string.label_add, { _, _ ->
                   val word = view.findViewById<TextView>(R.id.dae_edit_word).text.toString()
                   val meaning = view.findViewById<TextView>(R.id.dae_edit_meaning).text.toString()
                   val type = view.findViewById<TextView>(R.id.dae_edit_type).text.toString()
                   mListener.onDialogPositiveClick(word, meaning, type)
               })
               .setNegativeButton(R.string.label_cancel, { _, _ ->
                   mListener.onDialogNegativeClick(this@AddEntryDialog)
               })
        return builder.create()
    }
}
