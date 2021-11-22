package com.sarco.mymovies.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sarco.mymovies.databinding.ViewMovieItemBinding
import com.sarco.mymovies.model.Movie


class MoviesAdapter(var movies: List<Movie>,
                    private val movieClickListener: (Movie) -> Unit) :
                    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  ViewMovieItemBinding.inflate(LayoutInflater.from(parent.context),
                                    parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { movieClickListener(movie)}
    }

    override fun getItemCount(): Int = movies.size


    class ViewHolder(private val binding: ViewMovieItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie){
                binding.tvTitle.text = movie.title

//            Glide.with(binding.root.context)
//                .load("https://image.tmdb.org/t/p/w185/${movie.poster_path}")
//                .into(binding.ivCover)

            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w185/${movie.poster_path}")
                .into(binding.cover)
        }
    }
}