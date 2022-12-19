package com.example.testdialog.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testdialog.App
import com.example.testdialog.R
import com.example.testdialog.databinding.FragmentMessagesBinding
import com.example.testdialog.ui.adapter.MessagesAdapter


class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null

    private val binding get() = _binding!!
    private val viewModel: MessagesViewModel by viewModels {
        MessagesViewModel.provideFactory((requireActivity().application as App)
            .appComponent.messagesInteractor, requireActivity())
    }
    var adapter: MessagesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun viewModelListeners() {
        viewModel.messages.observe(viewLifecycleOwner) {
            it?.let {
                if (adapter == null) {
                    adapter = MessagesAdapter(it)
                    binding.recyclerview.adapter = adapter
                }
                adapter?.refreshData(it)
                binding.recyclerview.layoutManager?.scrollToPosition(it.size-1)
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showAlert(getString(it))
        }

    }
    fun initView() {
        showInterval()
        with(binding) {
            recyclerview.adapter = adapter
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                @SuppressLint("SetTextI18n")
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    viewModel.period = binding.seekBar.progress.toLong()
                    showInterval()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
            buttonInsert.setOnClickListener {
                viewModel.insertMessage(position.text.toString())
            }
            buttonTask.setOnClickListener { findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment) }

        }
    }

    fun showAlert(message: String){
        with(AlertDialog.Builder(requireContext())) {
            setTitle("Error")
            setMessage(message)
            setPositiveButton("OK", { dialog, which -> dialog.dismiss() })
            show()
        }
    }


    @SuppressLint("SetTextI18n")
    fun showInterval() {
        binding.interval.text = "${getString(R.string.interval)} ${viewModel.period} мсек"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeData()
        viewModelListeners()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}