package org.yellowtree.moviebrowse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.yellowtree.moviebrowse.R
import org.yellowtree.moviebrowse.databinding.FavoritesFragmentBinding
import org.yellowtree.moviebrowse.databinding.SearchFragmentBinding

class FavoritesFragment : Fragment() {

    private lateinit var favRV : RecyclerView
    private lateinit var binding : FavoritesFragmentBinding
    private val viewModel : SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.favorites_fragment, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        favRV = binding.favRv
        initFavListView()
    }

    private fun initFavListView() {

        favRV.layoutManager = LinearLayoutManager(favRV.context, LinearLayoutManager.VERTICAL, false)
        val favResultListAdapter = SearchResultListAdapter(this, viewModel,true, object : ItemClickListener{
            override fun onMovieItemClick(thumbnail: View, id: String) {

            }

        }) {fav, insert ->
            if (insert) {
                viewModel.insertFav(fav)
            } else {
                viewModel.deleteFav(fav)
            }


        }
        favRV.adapter = favResultListAdapter
        viewModel.favorites.observe(viewLifecycleOwner, { favs ->
            favResultListAdapter.submitList(favs)
        })


    }
}