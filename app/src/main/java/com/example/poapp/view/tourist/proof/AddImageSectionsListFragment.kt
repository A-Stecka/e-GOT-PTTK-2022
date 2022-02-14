package com.example.poapp.view.tourist.proof

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.viewModel.RouteViewModel
import java.io.InputStream

class AddImageSectionsListFragment(private val new: Boolean = false) : Fragment() {

    private val mViewModel: RouteViewModel by activityViewModels()
    private val PICK_IMAGE = 1
    val selected = mutableListOf<Long>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_image_sections_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.route_section_pick_list).adapter =
            RouteSectionsPickAdapter(activity as Context, mViewModel.getRouteSectionsWithoutProof(), mViewModel, object : OnRouteSectionSelectedListener {
                override fun check(routeSectionId: Long) {
                    selected.add(routeSectionId)
                }

                override fun uncheck(routeSectionId: Long) {
                    selected.remove(routeSectionId)
                }
            })

        view.findViewById<Button>(R.id.pick_picture_button).setOnClickListener {
            if (selected.isEmpty()) {
                dialogPickSection()
                return@setOnClickListener
            }
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"
            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/*"
            val chooserIntent = Intent.createChooser(getIntent, getString(R.string.pick_proof_image))
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            startActivityForResult(chooserIntent, PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(requireContext(), getString(R.string.image_not_selected), Toast.LENGTH_SHORT).show()
                return
            }
            val inputStream: InputStream? = requireContext().contentResolver.openInputStream(data.data!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            if (inputStream != null) {
                mViewModel.saveImageProof(selected, bitmap)
            }
            activity?.supportFragmentManager?.popBackStack("EditProofs", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    ProofListFragment(new)
                )
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    private fun dialogPickSection() {
        val alertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setNeutralButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
                setTitle(R.string.alert)
                setMessage(R.string.no_passes_chosen_message)
            }
            builder.create()
        }
        alertDialog.show()
    }
}