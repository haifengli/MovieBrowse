package org.yellowtree.moviebrowse.ui

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.yellowtree.moviebrowse.R
import org.yellowtree.moviebrowse.databinding.SearchFragmentBinding
import org.yellowtree.moviebrowse.repository.SearchAPIServiceRepo
import org.yellowtree.moviebrowse.util.Status

class SearchFragment : Fragment() {

    private lateinit var resultRV : RecyclerView
    private lateinit var binding : SearchFragmentBinding
    private val viewModel : SearchViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false, null)
        //viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lifecycleOwner = viewLifecycleOwner

        resultRV = binding.resultRv
        initResultListView()


        initSearchInput()

    }

    private fun initResultListView() {
        resultRV.layoutManager = LinearLayoutManager(resultRV.context, LinearLayoutManager.VERTICAL, false)
        val searchResultAdapter = SearchResultListAdapter(this, viewModel,false, object : ItemClickListener{
            override fun onMovieItemClick(thumbnail: View, id: String) {
                ViewCompat.setTransitionName(thumbnail, DetailFragment.ZOOM_IN)
                val sharedElement = FragmentNavigatorExtras(thumbnail to DetailFragment.ZOOM_IN)
                parentFragment?.findNavController()?.navigate(R.id.action_search_to_detail, bundleOf(DetailFragment.MOVIE_ID to id), null, sharedElement)
            }

        }) { fav, insert ->
            if (insert) {
                viewModel.insertFav(fav)
            } else {
                viewModel.deleteFav(fav)
            }
        }

        resultRV.adapter = searchResultAdapter
        viewModel.results.observe(viewLifecycleOwner, { resource ->
            searchResultAdapter.submitList(resource.data)
            if (resource.status == Status.ERROR) {
                displayErrorToast()
            }

        })

        viewModel.favIds.observe(viewLifecycleOwner, { favIds ->
            searchResultAdapter.setFavs(favIds)

        })
    }


    private fun displayErrorToast() {
        val text = resources.getText(R.string.network_error)
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(context, text, duration)
        toast.show()
    }

    private fun initSearchInput() {
        binding.searchTxt.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun doSearch(view : View) {
        val query = binding.searchTxt.text.toString()
        dismissKeyboard(view.windowToken)
        viewModel.setQuery(query)
    }


    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }





}