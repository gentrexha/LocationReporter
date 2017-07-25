package com.grasslever.gentrexha.locationreporter.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grasslever.gentrexha.locationreporter.R;

/**
 * Created by GRexha on 25-Jul-17.
 */

public class InformationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information,container,false);
    }
}
