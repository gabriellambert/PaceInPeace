package dominando.android.paceinpeacemvp.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import dominando.android.paceinpeacemvp.R
import dominando.android.paceinpeacemvp.model.Running

class RunningAdapter(context: Context, runnings: List<Running>) :
    ArrayAdapter<Running>(context, 0, runnings) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val running = getItem(position)
        val viewHolder = if (convertView == null) {
            val view =
                LayoutInflater.from(parent?.context).inflate(R.layout.item_running, parent, false)
            val holder = ViewHolder(view)
            view.tag = holder
            holder
        } else {
            convertView.tag as ViewHolder
        }
        viewHolder.txtDate.text = running?.date
        viewHolder.txtDuration.text = running?.duration.toString()
        viewHolder.txtDistance.text = running?.distance.toString()

        return viewHolder.view
    }

    class ViewHolder(val view: View) {
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val txtDuration: TextView = view.findViewById(R.id.txtDuration)
        val txtDistance: TextView = view.findViewById(R.id.txtDistance)
    }

}