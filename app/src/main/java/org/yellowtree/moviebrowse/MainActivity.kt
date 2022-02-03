package org.yellowtree.moviebrowse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import org.yellowtree.moviebrowse.ui.MovieContainerFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//        supportFragmentManager.beginTransaction()
//            .add(R.id.fragment_container, MovieContainerFragment(), MovieContainerFragment::class.simpleName)
//            .commit()

    }
}