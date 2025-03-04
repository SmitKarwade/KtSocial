package com.example.ktinsta.itemfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.example.instagram.R

class SearchFragment : Fragment() {
    private var toolbar: Toolbar? = null
    private var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        toolbar = requireActivity().findViewById(R.id.toolbar)
        toolbar?.setTitle("")
        requireActivity().addMenuProvider(menuProvider)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().removeMenuProvider(menuProvider)
    }

    private val menuProvider: MenuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.search_menu, menu)

            val item = menu.findItem(R.id.search)
            searchView = item.actionView as SearchView?

            searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    Toast.makeText(activity, "" + query.toString(), Toast.LENGTH_SHORT).show()
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return false
        }
    }
}