package com.example.ktinsta.itemfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.example.instagram.R

class ProfileFragment : Fragment() {
    private var materialToolbar: Toolbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        materialToolbar = requireActivity().findViewById(R.id.toolbar)
        materialToolbar?.setTitle("Username")
        materialToolbar?.setTitleTextAppearance(activity, R.style.textAppearanceToolbar)
        Toast.makeText(activity, "pro onCreateView()", Toast.LENGTH_SHORT).show()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner)
        Toast.makeText(activity, "pro onViewCreated()", Toast.LENGTH_SHORT).show()
    }


    override fun onPause() {
        super.onPause()
        materialToolbar!!.removeMenuProvider(menuProvider)
        Toast.makeText(context, "pro onPause()", Toast.LENGTH_SHORT).show()
    }

    private val menuProvider: MenuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            materialToolbar!!.menu.clear()
            menuInflater.inflate(R.menu.profile_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            val profileID = menuItem.itemId
            if (profileID == R.id.setting) {
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, SettingItemsFragment())
                    .addToBackStack(null)
                    .commit()

            } else if (profileID == R.id.create_post) {
                Toast.makeText(context, "Create post", Toast.LENGTH_SHORT).show()
            } else if (profileID == R.id.threads) {
                Toast.makeText(context, "Threads", Toast.LENGTH_SHORT).show()
            }
            return true
        }
    }
}