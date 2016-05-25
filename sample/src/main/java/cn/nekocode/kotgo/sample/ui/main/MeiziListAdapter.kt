package cn.nekocode.kotgo.sample.ui.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import cn.nekocode.kotgo.lib.request.Request

import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.data.dto.Meizi
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onLongClick

/**
 * Created by nekocode on 2015/7/22.
 */
class MeiziListAdapter(private val list: List<Meizi>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    object Type {
        const val TYPE_ITEM: Int = 0
    }

    var onMeiziItemClickListener: ((Meizi) -> Unit)? = null
    var onMeiziItemLongClickListener: ((Meizi) -> Boolean)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            Type.TYPE_ITEM -> {
                val v = parent!!.context.layoutInflater.inflate(R.layout.item_meizi, parent, false)
                return ItemViewHolder(v)
            }

        }

        throw UnsupportedOperationException()
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int) = when(position) {
        else -> Type.TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ItemViewHolder -> {
                holder.setData(list[position])
            }
        }
    }

    private inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView by bindView(R.id.imageView)
        val textView: TextView by bindView(R.id.textView)
        val textView2: TextView by bindView(R.id.textView2)

        fun setData(meizi : Meizi) {
            itemView?.apply {
                onClick { onMeiziItemClickListener?.invoke(meizi) }
                onLongClick { onMeiziItemLongClickListener?.invoke(meizi) ?: false }
            }

            Request.image(imageView, meizi.url)
            textView.text = meizi.id
            textView2.text = "${meizi.who} - ${meizi.type}"
        }
    }
}


