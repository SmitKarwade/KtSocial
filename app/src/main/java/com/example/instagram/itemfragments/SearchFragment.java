package com.example.instagram.itemfragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.instagram.R;



public class SearchFragment extends Fragment {
    private Toolbar toolbar;
    private SearchView searchView;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("");
        getActivity().addMenuProvider(menuProvider);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().removeMenuProvider(menuProvider);
    }

    private MenuProvider menuProvider = new MenuProvider() {
        @Override
        public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
            menuInflater.inflate(R.menu.search_menu, menu);

            MenuItem item = menu.findItem(R.id.search);
            searchView = (SearchView) item.getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(getActivity(), "" + query.toString(), Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

        }

        @Override
        public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
            return false;
        }

    };
}