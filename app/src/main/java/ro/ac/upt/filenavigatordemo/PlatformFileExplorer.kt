package ro.ac.upt.filenavigatordemo

import java.io.File


class PlatformFileExplorer {

    fun listFiles(fileEntry: FileEntry): List<FileEntry> {
        TODO("Return the files contained by the current directory (both plain files and directories)")

        return emptyList()
    }

    fun hasParent(fileEntry: FileEntry): Boolean {
        TODO("Check if current file entry has a parent directory")


        return false
    }

    fun getParent(fileEntry: FileEntry): FileEntry {
        TODO("Return the parent of current file entry")


        return null!!
    }

}