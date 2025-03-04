package com.example.ktinsta.itemfragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.example.ktinsta.login.LoginActivity
import com.example.instagram.R
import com.google.firebase.auth.FirebaseAuth

class SettingItemsFragment : Fragment() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private var toolbar: Toolbar? = null
    private var activity: AppCompatActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_setting_items, container, false)

        activity = requireActivity() as AppCompatActivity
        toolbar = requireActivity().findViewById(R.id.toolbar)
        toolbar?.title = "Settings and privacy"
        toolbar?.setTitleTextAppearance(requireContext(), R.style.textAppearanceToolbar)

        view.findViewById<TextView>(R.id.signOut).setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        activity?.setSupportActionBar(toolbar)
        activity?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        activity?.addMenuProvider(menuProvider, viewLifecycleOwner)
    }

    override fun onPause() {
        super.onPause()
        activity?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
        activity?.removeMenuProvider(menuProvider)
    }

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return if (menuItem.itemId == android.R.id.home) {
                activity?.supportFragmentManager?.popBackStack()
                true
            } else false
        }
    }
}
