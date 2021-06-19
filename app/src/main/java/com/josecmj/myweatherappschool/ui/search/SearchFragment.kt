package com.josecmj.myweatherappschool.ui.search

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.josecmj.myweatherappschool.databinding.FragmentSearchBinding
import com.josecmj.myweatherappschool.model.Place
import com.josecmj.myweatherappschool.model.RetrofitInitializer
import com.josecmj.myweatherappschool.model.QueryResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        searchViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val itemAdapter = ListAdapter ( { item -> adapterOnClick(item) })

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = itemAdapter

        binding.txtSearch.setEndIconOnClickListener {
            if(context != null) {
                if (!isInternetAvailable(requireContext())){
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                }else{
                    loadData(itemAdapter)
                }

            }
        }

        return root
    }

    private fun adapterOnClick(item: Place) {

    }

    fun loadData(itemAdapter: ListAdapter) {
        val call = RetrofitInitializer().weatherService().queryWeather("recife","5fde54966e3e1c8a80e436245bdf9672", "metric")

        call.enqueue(object : Callback<QueryResult> {
            override fun onResponse(call: Call<QueryResult>, response: Response<QueryResult>) {
                response?.body()?.let {
                    val notes: QueryResult = it
                    itemAdapter.submitList(notes.list)
                    Log.d("ZECA", notes.toString())
                }
            }

            override fun onFailure(call: Call<QueryResult>, t: Throwable) {
                Log.d("ZECA","error")
            }
        })
    }

    fun isInternetAvailable(context: Context): Boolean {
        var result = false

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        cm.getNetworkCapabilities(cm.activeNetwork)?.run {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }

        return result
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

class WeatherViewModel : LiveData<List<Place>> (emptyList()) {
    fun loadData() {
        val call = RetrofitInitializer().weatherService().queryWeather("recife","5fde54966e3e1c8a80e436245bdf9672", "metric")

        call.enqueue(object : Callback<QueryResult> {
            override fun onResponse(call: Call<QueryResult>, response: Response<QueryResult>) {
                response?.body()?.let {
                    val notes: QueryResult = it
                    value = notes.list
                    Log.d("ZECA", notes.toString())
                }
            }

            override fun onFailure(call: Call<QueryResult>, t: Throwable) {
                Log.d("ZECA","error")
            }
        })
    }
}
