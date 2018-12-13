package ro.ac.upt.filenavigatordemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_file_entry.view.*

class FilesRecyclerViewAdapter(private val onClick : (FileEntry) -> Unit)
    : RecyclerView.Adapter<FilesRecyclerViewAdapter.FileViewHolder>() {

    private val fileEntries : MutableList<FileEntry> = mutableListOf()

    fun refreshFiles(files : List<FileEntry>) {
        fileEntries.clear()
        fileEntries.addAll(files)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file_entry, parent, false)

        return FileViewHolder(view)
    }

    override fun getItemCount(): Int = fileEntries.size


    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(fileEntries[position])
    }

    inner class FileViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener { onClick.invoke(fileEntries[adapterPosition]) }
        }

        fun bind(fileEntry: FileEntry) {
            val fileTypeImageRes = if(fileEntry.isDir) R.drawable.ic_folder else R.drawable.ic_more_horiz
            itemView.imv_file_type.setImageResource(fileTypeImageRes)
            itemView.txv_file_path.text = fileEntry.path
        }
    }

}