package ro.ac.upt.filenavigatordemo

import java.io.File


class PlatformFileExplorer {

    fun listFiles(fileEntry: FileEntry): List<FileEntry> {
        //        TODO("Return the files contained by the current directory (both plain files and directories)")

        val f = File(fileEntry.path)
        if (f.exists()) {

            val files = f.listFiles()
            if(files != null) {
                return files
                    .map {
                        FileEntry(it.name, it.isDirectory)
                    }
            }
        }

        return listOf()
    }

    fun hasParent(fileEntry: FileEntry): Boolean {
//        TODO("Check if current file entry has a parent directory")
        val f = File(fileEntry.path)
        if(f.exists()) {
            val files = f.parentFile.listFiles()
            return files != null
        }

        return false
    }

    fun getParent(fileEntry: FileEntry): FileEntry {
//        TODO("Return the parent of current file entry")
        val f = File(fileEntry.path)

        return FileEntry(f.parentFile.path, f.parentFile.isDirectory)
    }

}