package com.example.aiartcreator.view

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.aiartcreator.databinding.FragmentDownloadBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.aiartcreator.viewmodel.AppViewModel
import com.example.aiartcreator.Result
import com.example.aiartcreator.daoModel.RoomEntity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

class DownloadFragment : Fragment() {
    private lateinit var binding: FragmentDownloadBinding
    private val apiViewModel by viewModel<AppViewModel>()
    private var text = ""
    private var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDownloadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        if (text.isNotEmpty() || category.isNotEmpty()) {
            setupCollector()
        }
    }

    private fun setupCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                apiViewModel.getTextToImage(text, category)
                apiViewModel.text2imageState.collectLatest {
                    when (it) {
                        is Result.Idle -> {
                            // show loading indicator if needed
                        }
                        is Result.Success -> {
                            it.data?.let { it1 ->
                                uiOperations(it1)
                                val description = binding.exampleTV.text.toString()
                                val bitmap = it.data
                                val fileName = text
                                val stream = ByteArrayOutputStream()
                                it.data.compress(Bitmap.CompressFormat.PNG, 90, stream)

                                saveImageToDao(stream, description, bitmap, fileName)
                            }
                        }
                        is Result.Error -> {
                            // show error message if needed
                        }
                    }
                }
            }
        }
    }

    private fun setupUi() {
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }
            exampleTV.visibility = View.GONE
            downloadIV.visibility = View.GONE
            cardView2.visibility = View.GONE
            arguments?.let {
                text = DownloadFragmentArgs.fromBundle(requireArguments()).text
                category = DownloadFragmentArgs.fromBundle(requireArguments()).category

            }
        }
    }

    private fun uiOperations(bitmap: Bitmap) {
        binding.apply {
            downloadIV.setImageBitmap(bitmap)
            downloadIV.visibility = View.VISIBLE
            exampleTV.visibility = View.VISIBLE
            cardView2.visibility = View.VISIBLE
            exampleTV.text = "${text}, ${category}"
        }
    }

    private suspend fun saveImageToDao(
        stream: ByteArrayOutputStream,
        description: String,
        bitmap: Bitmap,
        fileName: String
    ) {
        //handle room insert
        val image = RoomEntity(
            image = stream.toByteArray(),
            description = description
        )

        apiViewModel.viewModelScope.launch {
            apiViewModel.insertImage(image)
        }
        //handle save image to gallery
        saveImageToGallery(bitmap, fileName)
    }

    private fun showAlertDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Artwork Saved Succesfully!")
        builder.setMessage("The AI-generated artwork you created has been successfully saved to photos.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun saveImageToGallery(bitmap: Bitmap, fileName: String) {
        binding.downloadBtn.setOnClickListener {
            val resolver = requireActivity().contentResolver
            val imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val newImage = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }
            val imageUri = resolver.insert(imageCollection, newImage)

            if (imageUri != null) {
                resolver.openOutputStream(imageUri).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                }
            }
            //handle alertdialog
            showAlertDialog()
        }
    }
}
