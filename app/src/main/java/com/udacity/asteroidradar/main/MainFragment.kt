package com.udacity.asteroidradar.main

import ClickHandler
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapter.AsteroidAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainBinding: FragmentMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val asteroidAdapter by lazy {
        AsteroidAdapter(ClickHandler {
            viewModel.displayDetails(it)
            val item = viewModel.selectedAsteroid
            findNavController().navigate(MainFragmentDirections.actionShowDetail(
                item.value!!
            ))
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

    viewModel.pictureOfDay.observe(viewLifecycleOwner , androidx.lifecycle.Observer {
        with(binding) {

            val context = root.context
            val picasso = Picasso.with(context)
                .load(it.url,)
                .error(R.mipmap.ic_launcher)
                .into(binding.activityMainImageOfTheDay,)
        }
    })

            binding.asteroidRecycler.adapter = asteroidAdapter
            setHasOptionsMenu(true)

            return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.week_asteroid_menuId -> viewModel.getWeekAsteroids()
            R.id.today_asteroid_menuId -> viewModel.getTodayAsteroids()
            R.id.all_asteroids_menuId -> viewModel.getAllAsteroids()
        }
        return true
    }

}
