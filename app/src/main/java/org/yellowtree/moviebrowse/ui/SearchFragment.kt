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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.yellowtree.moviebrowse.R
import org.yellowtree.moviebrowse.databinding.SearchFragmentBinding
import org.yellowtree.moviebrowse.repository.SearchAPIServiceRepo

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
        val searchResultAdapter = SearchResultListAdapter(false, object : ItemClickListener{
            override fun onMovieItemClick(id: String) {
//                parentFragment.findNavController().navigate()
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

        })

        viewModel.favIds.observe(viewLifecycleOwner, { favIds ->
            searchResultAdapter.setFavs(favIds)

        })
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