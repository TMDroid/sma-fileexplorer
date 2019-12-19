package ro.ac.upt.filenavigatordemo

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_file_explorer.*
import java.io.File


class FileExplorerActivity : AppCompatActivity() {

    private lateinit var fileExplorerViewModel: FileExplorerViewModel
    private var adapter = FilesRecyclerViewAdapter {

        val oldPath = fileExplorerViewModel.currentPath.value!!.path

        val path = "${oldPath}/${it.path}"
        val f = File(path)
        val ff = FileEntry(path, f.isDirectory)

        fileExplorerViewModel.openFile(ff)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_explorer)

        fileExplorerViewModel = ViewModelProviders.of(this).get(FileExplorerViewModel::class.java)

        requestStoragePermission()
    }

    private fun init() {
        initWidgets()

        initViewModelObservers()

        initFromRootDirectory()
    }

    private fun initViewModelObservers() {
//        TODO("Observe currentPath's live data from view model and update txv_current_dir on changes")
        fileExplorerViewModel.currentPath.observe(this, Observer {
            txv_current_dir.text = it.path

            fab_back.isEnabled = fileExplorerViewModel.platformFileExplorer.hasParent(it)
        })


//        TODO("Observe listedFiles's live data from view model and update the recycler view adapter on changes")
        fileExplorerViewModel.listedFiles.observe(this, Observer {
            adapter.refreshFiles(it)
        })

    }

    private fun initWidgets() {
//        TODO("Initialize the layout manager and adapter of the recycler view widget")
        rcv_list_dir.apply {
            adapter = this@FileExplorerActivity.adapter
            layoutManager = LinearLayoutManager(this@FileExplorerActivity)
        }

//        TODO("Initialize a click listener for back button such that once clicked, it would navigate one level up in current directory hierarchy")

        fab_back.setOnClickListener {
            val current = fileExplorerViewModel.currentPath.value!!

            if (fileExplorerViewModel.platformFileExplorer.hasParent(current)) {
                fileExplorerViewModel.onBack()
            }
        }
    }

    private fun initFromRootDirectory() {
        val root = Environment.getExternalStorageDirectory()
        fileExplorerViewModel.openFile(FileEntry(root.absolutePath, true))
    }

    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                init()

            } else {
                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), 1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                init()
            }
        }
    }

}
