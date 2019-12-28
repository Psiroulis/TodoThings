package com.redpepper.todothings.ui.home;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.redpepper.todothings.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button goToTestActivityBtn = root.findViewById(R.id.goToShoppingCategoriesBtn);

        goToTestActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                NavDirections action = HomeFragmentDirections.goToCategories();
//
//                Navigation.findNavController(view).navigate(action);

//                NavDirections action = HomeFragmentDirections.goToTools();
//
//                Navigation.findNavController(view).navigate(action);

                NavDirections action = HomeFragmentDirections.goToShoppingCategory();
                Navigation.findNavController(view).navigate(action);
            }
        });


        return root;
    }


}