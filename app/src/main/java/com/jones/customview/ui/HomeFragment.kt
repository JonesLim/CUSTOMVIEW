package com.jones.customview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jones.customview.R
import com.jones.customview.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.color1.setOnClickListener {
            binding.pathDrawing.setColor(resources.getColor(R.color.color1, null))
        }

        binding.color2.setOnClickListener {
            binding.pathDrawing.setColor(resources.getColor(R.color.color2, null))
        }

        binding.color3.setOnClickListener {
            binding.pathDrawing.setColor(resources.getColor(R.color.color3, null))
        }


        // Set click listeners for buttons
        binding.btnUndo.setOnClickListener {
            binding.pathDrawing.undo()
        }

        binding.btnReset.setOnClickListener {
            binding.pathDrawing.reset()
        }
    }
}
