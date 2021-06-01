package com.dang.marty.weather.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dang.marty.weather.R
import com.dang.marty.weather.databinding.ListItemSettingsBinding
import com.dang.marty.weather.presentation.model.BaseSettingsModel
import com.dang.marty.weather.presentation.model.SettingsModel

class SettingsAdapter(var data: List<SettingsModel>, val callbacks: SettingsAdapterCallbacks):
    RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsAdapter.SettingsViewHolder {
        return when (BaseSettingsModel.Type.fromOrdinal(viewType)) {
            BaseSettingsModel.Type.REGULAR -> SettingsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_settings, parent, false))
        }
    }

    override fun onBindViewHolder(holder: SettingsAdapter.SettingsViewHolder, position: Int) {
        val rowItem = data[position]
        holder.updateData(rowItem)
        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onClick(v: View?) {
        val position = v?.tag as Int?
        position?.let {
            val model = data[it]
            if(model is BaseSettingsModel) {
                model.clickListener.invoke(model.title)
            }
        }
    }

    open class BaseSettingsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        open fun updateData(data: SettingsModel) {}
    }

    inner class SettingsViewHolder(view: View): BaseSettingsViewHolder(view) {
        private val binding = ListItemSettingsBinding.bind(view)

        init {
            view.setOnClickListener(this@SettingsAdapter)
        }

        override fun updateData(data: SettingsModel) {
            val item = data as SettingsModel
            binding.listItemSettingsCardTitle.text = item.title
        }

    }
}

interface SettingsAdapterCallbacks {

}