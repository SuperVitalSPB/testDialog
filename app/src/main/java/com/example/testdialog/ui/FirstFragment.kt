package com.example.testdialog.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testdialog.R
import com.example.testdialog.data.MessagesInteractor
import com.example.testdialog.databinding.FragmentFirstBinding
import com.example.testdialog.ui.adapter.MessagesAdapter

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!
    lateinit var viewmodel: MainViewModel
    var adapter: MessagesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        viewmodel = MainViewModel(MessagesInteractor())
        viewmodel.observeData()

        binding.recyclerview.adapter = adapter
        viewmodel.messages.observe(viewLifecycleOwner) {
            it?.let {
                if (adapter == null) {
                    adapter = MessagesAdapter(it)
                    binding.recyclerview.adapter = adapter
                }
                adapter?.refreshData(it)
                binding.recyclerview.layoutManager?.scrollToPosition(it.size-1)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}