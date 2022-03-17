package com.example.wordle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordle.databinding.FragmentGameCustomBinding
import kotlinx.android.synthetic.main.fragment_game_custom.*

class CustomGameFragment : Fragment() {

    private var _binding: FragmentGameCustomBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGameCustomBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_CustomGameFragment_to_InitialFragment)
        }

        val inputConnection = word.onCreateInputConnection(EditorInfo())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val keyPadListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.deleteKey -> inputConnection?.delete()
            else -> {
                val value = (view as? TextView)?.text
                inputConnection?.commitText(value, 1)
            }
        }
    }

    private fun InputConnection.delete() {
        if (getSelectedText(0).isNullOrBlank()) {
            deleteSurroundingText(1, 0)
        } else {
            commitText("", 1)
        }
    }
}