package com.example.starwars.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwars.MyApplication
import com.example.starwars.adapter.StarCharactersAdapter
import com.example.starwars.database.DBHelper
import com.example.starwars.databinding.FragmentStarCharactersBinding
import com.example.starwars.model.Results
import com.example.starwars.model.StarWarsResponse
import com.example.starwars.viewModel.StarWarsViewModel
import com.example.starwars.viewModel.StarWarsViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class StarCharactersFragment : Fragment(), StarCharactersAdapter.LastPositionScrollView {

    @Inject
    lateinit var viewModel: StarWarsViewModel

    @Inject
    lateinit var starWarsViewModelFactory: StarWarsViewModelFactory
    private var _binding: FragmentStarCharactersBinding? = null
    private val binding get() = _binding!!
    private var starCharactersAdapter: StarCharactersAdapter? = null
    private var starWarsResponse: StarWarsResponse? = null
    private var isScrolling = false
    private var checkApiResponse = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStarCharactersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        val characterDao = DBHelper.getInstance(requireContext()).characterDao()
        val db = DBHelper.getInstance(requireContext())
        starCharactersAdapter = StarCharactersAdapter( this)
        binding.gridView.adapter = starCharactersAdapter
        if (::viewModel.isInitialized) {
            viewModel =
                ViewModelProvider(this, starWarsViewModelFactory)[StarWarsViewModel::class.java]
        }


        binding.progressBar.visibility = View.VISIBLE
        // Trigger API call
        db.clearAllTables()
        viewModel.getPeoples(null)



        // Observe the ViewModel
        viewModel.peoples.observe(requireActivity()) { peoples ->
            // Update UI with characters
            Log.d("TAG", "onCreate: check the list of people $peoples")
            checkApiResponse = true
            starWarsResponse = peoples
            CoroutineScope(Dispatchers.IO).launch {
                val characterList = characterDao.fetchStarWars()
                var characterListData : ArrayList<Results> = arrayListOf()
                if (characterList != null) {
                  characterListData = characterList.results
                }
                db.clearAllTables()
                for(people in peoples.results) {
                    characterListData.add(people)
                }
                val newStarWars = StarWarsResponse(next = peoples.next, previous = peoples.previous,  results = characterListData)
                characterDao.insert(newStarWars)
                val charList: ArrayList<ArrayList<Results>> = ArrayList()
                CoroutineScope(Dispatchers.IO).launch {
                    val fetchCharacterList = characterDao.fetchStarWars()
                    CoroutineScope(Dispatchers.Main).launch {
                        starCharactersAdapter?.apply {
                            Log.d("TAG", "categories: categories size  ${charList.size}")
                            fetchCharacterList?.results?.let { setData(it) }
                            notifyDataSetChanged()
                        }
                    }
                }
            }
            binding.progressBar.visibility = View.GONE
        }

        binding.gridView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                isScrolling = scrollState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE
            }

            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (checkApiResponse && isScrolling && (firstVisibleItem + visibleItemCount == totalItemCount)) {
                    // This means you've reached the end of the GridView
                    // Implement your load more items logic here
                    val next = starWarsResponse?.next
                    if (next?.isNotEmpty() == true) {
                        binding.progressBar.visibility = View.VISIBLE
                        checkApiResponse = false
                        viewModel.getPeoples(getPageNumberFromUrl(next))
                        isScrolling = false
                    }
                }
            }
        })

        starCharactersAdapter?.onLastItemVisibleListener = {
            // Trigger your API call here

        }
    }

    private fun getPageNumberFromUrl(url: String): Int {
        val regex = Regex("page=(\\d+)")
        val matchResult = regex.find(url)
        return matchResult?.groupValues?.get(1)?.toIntOrNull() ?: 1
    }

    override fun lastScrollView() {
        Log.d("TAG", "onViewCreated:  check the clicked")
        binding.progressBar.visibility = View.VISIBLE

    }


}