package org.yellowtree.moviebrowse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import org.yellowtree.moviebrowse.R
import org.yellowtree.moviebrowse.databinding.ContainerFragmentBinding

class MovieContainerFragment : Fragment() {

    companion object {
        const val SEARCH_POSITION = 0
        const val FAVORITES_POSITION = 1

    }

    lateinit var binding : ContainerFragmentBinding

    private lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.container_fragment,  container, false)

        // ***** Sets up the viewpager for Search and Favorites ****
        viewPager = binding.movieViewpager
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 2

            override fun createFragment(position: Int): Fragment {
                if (position == SEARCH_POSITION) {
                    return SearchFragment()
                }
                return FavoritesFragment()
            }
        }


        val tabLayout = binding.movieTabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == SEARCH_POSITION) {
                tab.text = getString(R.string.search_movie)
            } else {
                tab.text = getString(R.string.favorites_movie)
            }
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}