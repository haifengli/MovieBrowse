package org.yellowtree.moviebrowse.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.yellowtree.moviebrowse.R
import org.yellowtree.moviebrowse.databinding.DetailFragmentBinding
import org.yellowtree.moviebrowse.util.Status

class DetailFragment : Fragment() {

    companion object {
        const val MOVIE_ID = "movie_id"
        const val ZOOM_IN = "zoom_in"
    }
    private lateinit var binding: DetailFragmentBinding

    private lateinit var enlargedIV : ImageView

    private lateinit var viewModel : DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition()
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        enlargedIV = binding.picImg
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareTransition()
        binding.lifecycleOwner = viewLifecycleOwner

        arguments?.getString(MOVIE_ID)?.let {
            viewModel.fetchMovie(it)
        }

        viewModel.movieLiveData.observe(viewLifecycleOwner, { resource ->
            if (resource.status == Status.SUCCESS) {
                val movie = resource.data
                binding.movie = movie
                movie?.let {
                Glide.with(enlargedIV.context).load(movie.posterUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            ViewCompat.setTransitionName(enlargedIV, ZOOM_IN)
                            startPostponedEnterTransition()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            ViewCompat.setTransitionName(enlargedIV, ZOOM_IN)
                            startPostponedEnterTransition()
                            return false
                        }

                    }).into(enlargedIV)

                }
            }


        })


    }

    private fun prepareTransition() {
        val transition = TransitionInflater.from(binding.root.context).inflateTransition(R.transition.enter_transition)
        sharedElementEnterTransition = transition
    }








}