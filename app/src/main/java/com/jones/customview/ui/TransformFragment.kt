package com.jones.customview.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jones.customview.R
import com.jones.customview.databinding.FragmentHomeBinding
import com.jones.customview.databinding.FragmentTransformBinding
import kotlin.math.PI


class TransformFragment : Fragment() {

    private lateinit var binding: FragmentTransformBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransformBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMove.setOnClickListener {
//            binding.transformView.move(50, 50)
            binding.transformView.rotate((2* PI).toFloat(), 5000)
        }

        binding.btnAdd.setOnClickListener {
            binding.transformView.createRandomShape()
        }
    }
}
