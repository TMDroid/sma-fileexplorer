package ro.ac.upt.filenavigatordemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FileExplorerViewModel : ViewModel() {

    private val platformFileExplorer = PlatformFileExplorer()

    val currentPath = MutableLiveData<FileEntry>()
    val listedFiles = MutableLiveData<List<FileEntry>>()

    fun openFile(fileEntry: FileEntry) {
        if (fileEntry.isDir) {
            currentPath.value = fileEntry
            listedFiles.value = platformFileExplorer.listFiles(fileEntry)
        }
    }

    fun onBack() {
        val path = currentPath.value

        if (path != null && path.isDir && platformFileExplorer.hasParent(path)){
            val parentDir = platformFileExplorer.getParent(path)
            currentPath.value = parentDir
            listedFiles.value = platformFileExplorer.listFiles(parentDir)
        }
    }


}