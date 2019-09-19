package algo.com.mmchords

import algo.com.mmchords.Objects.Song
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataList = mutableListOf<Song>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_song_item, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SongViewHolder)
            holder.show(dataList.get(position))
    }

    fun set(songs: MutableList<Song>) {
        dataList.clear()
        dataList.addAll(songs)
        notifyDataSetChanged()
    }

    fun addAll(songs: MutableList<Song>) {
        dataList.addAll(songs)
        notifyDataSetChanged()
    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        val tv_singer = itemView.findViewById<TextView>(R.id.tv_singer)

        fun show(song: Song) {
            tv_title.text = song.title
            tv_singer.text = "တေးဆို - ${song.singer}"
        }

    }

}