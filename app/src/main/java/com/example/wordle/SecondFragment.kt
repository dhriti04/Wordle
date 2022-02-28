package com.example.wordle

import android.os.Bundle
import android.text.InputFilter
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.wordle.databinding.FragmentSecondBinding
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.fragment_second.word_1
import kotlinx.android.synthetic.main.word_layout.view.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        word_1.textView1.requestFocus()
        setFocusOnWord(word_1, word_2)
        setFocusOnWord(word_2, word_3)
        setFocusOnWord(word_3, word_4)
        setFocusOnWord(word_4, word_5)
        setFocusOnWord(word_5, word_5)

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showInvalidWordAlert() {
        Toast.makeText(context, "Invalid word!", Toast.LENGTH_SHORT).show()
    }

    private fun EditText.checkIfEnterOrBackPressed(prevEditText: EditText, word: View, nextWord: View){
        this.setOnKeyListener { _, keyCode, event ->
            when {
                //Check if it is the Enter-Key,      Check if the Enter Key was pressed down
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {

                    if(word.textView1.length() == word.textView2.length() && word.textView3.length() == word.textView4.length() && word.textView5.length() == 1){
                        //perform word check
                        checkWord(word, nextWord)
                    } else {
                        showInvalidWordAlert()
                    }
                    //return true
                    return@setOnKeyListener true
                }
                ((keyCode == KeyEvent.KEYCODE_BACK) && (event.action == KeyEvent.ACTION_DOWN)) -> {

                    if(this.length() == 0){
                        //perform check
                        prevEditText.requestFocus()
                        prevEditText.setText("")
                    }
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
        }
    }

    private fun EditText.checkIfLetterEntered(prevEditText: EditText, nextEditText: EditText){
        this.filters = this.filters + InputFilter.AllCaps()
        this.doOnTextChanged { text, _, _, _ ->
            if (text?.length == 0){
                prevEditText.requestFocus()
            }
            if (text?.length == 1){
                nextEditText.requestFocus()
            }
        }
    }

    private fun checkWord(word: View, nextWord: View){
        var total = 0
        if(word.textView1.checkLetter(0)) total++
        if(word.textView2.checkLetter(1)) total++
        if(word.textView3.checkLetter(2)) total++
        if(word.textView4.checkLetter(3)) total++
        if(word.textView5.checkLetter(4)) total++

        word.textView1.setUneditable()
        word.textView2.setUneditable()
        word.textView3.setUneditable()
        word.textView4.setUneditable()
        word.textView5.setUneditable()

        if (total == 5){
            Toast.makeText(context,"Great Job wooohoo", Toast.LENGTH_LONG).show()
            val popup = LayoutInflater.from(context).inflate(R.layout.popup, null)
        } else {
            nextWord.textView1.requestFocus()
        }

    }

    private fun EditText.checkLetter(index: Int): Boolean {
        val word = "HELLO"
        if(this.text in word && this.text[0] == word[index]){
            this.setBackgroundResource(R.drawable.border_green)
            return true
        } else if(this.text in word) {
            this.setBackgroundResource(R.drawable.border_yellow)
            return false
        } else {
            this.setBackgroundResource(R.drawable.border_grey)
        }
        return false
    }

    private fun EditText.setUneditable(){
        this.isFocusable = false
        this.isClickable = false
    }

    private fun setFocusOnWord(word: View, nextWord: View){
        word.textView1.checkIfLetterEntered(word.textView1, word.textView2)
        word.textView1.checkIfEnterOrBackPressed(word.textView1, word, nextWord)

        word.textView2.checkIfLetterEntered(word.textView1, word.textView3)
        word.textView2.checkIfEnterOrBackPressed(word.textView1, word, nextWord)

        word.textView3.checkIfLetterEntered(word.textView2, word.textView4)
        word.textView3.checkIfEnterOrBackPressed(word.textView2, word, nextWord)

        word.textView4.checkIfLetterEntered(word.textView3, word.textView5)
        word.textView4.checkIfEnterOrBackPressed(word.textView3, word, nextWord)

        word.textView5.checkIfLetterEntered(word.textView4, word.textView5)
        word. textView5.checkIfEnterOrBackPressed(word.textView4, word, nextWord)
    }

}