package com.app.igrow.ui.admin

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.app.igrow.R
import com.app.igrow.base.BaseActivity
import com.app.igrow.databinding.ActivityAdminBinding
import com.app.igrow.helpers.FileConverter
import com.app.igrow.ui.admin.dealers.DealersActivity
import com.app.igrow.ui.admin.diagnostic.DiagnosticActivity
import com.app.igrow.ui.admin.distributors.DistributorsActivity
import com.app.igrow.ui.admin.product.ProductsActivity
import com.app.igrow.utils.Constants
import com.app.igrow.utils.Utils.getFileMimeType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.action_type_dialog.view.*
import org.apache.poi.ss.usermodel.Workbook
import java.io.InputStream


@AndroidEntryPoint
class AdminActivity : BaseActivity() {

    private lateinit var binding: ActivityAdminBinding
    private val viewModel: AdminActivityViewModel by viewModels()
    private val fileConverter = FileConverter()
    private lateinit var workbook: Workbook

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        activateListener()
        startObserving()
    }

    private fun initView() {
        binding.uploadFile.setOnClickListener {
            uploadFile()
        }
    }

    private fun activateListener() {
        binding.edit.setOnClickListener {
            showActionTypeDialog(AdminActionType.EDIT.toString())
        }
        binding.delete.setOnClickListener {
            showActionTypeDialog(AdminActionType.DELETE.toString())
        }
    }

    private fun startObserving() {
        viewModel.uiStateLiveData.observe(this) { state ->
            when (state) {
                is LoadingState -> {
                    // pb_saveClimb.visibility = View.VISIBLE
                }
                is UnloadingState -> {
                    // pb_saveClimb.visibility = View.GONE
                }
            }
        }
        viewModel.addDiagnosticLiveData.observe(this) {
            showMessage(it.toString())
        }
        viewModel.addDistributorsLiveData.observe(this) {
            showMessage(it.toString())
        }
        viewModel.addDealerLiveData.observe(this) {
            showMessage(it.toString())
        }
        viewModel.addProductsLiveData.observe(this) {
            showMessage(it.toString())
        }
    }

    private fun uploadFile() {

        var uploadFileIntent = Intent(Intent.ACTION_GET_CONTENT)
        uploadFileIntent.addCategory(Intent.CATEGORY_OPENABLE)
        uploadFileIntent.type = "*/*"
        val mimeTypes = getFileMimeType()
        uploadFileIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        uploadFileIntent =
            Intent.createChooser(uploadFileIntent, getString(R.id.upload_file))
        uploadFileLauncher.launch(uploadFileIntent)

    }

    private val uploadFileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            val selectedFileUri: Intent? = it.data
            val uri: Uri? = selectedFileUri?.data
            if (uri != null) {
                fileToWorkBook(uri = uri)
            }
        }
    }

    private fun fileToWorkBook(uri: Uri) {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        if (inputStream != null) {
            workbook = fileConverter.fileToWorkBook(inputStream)
            uploadFileData()
        }
    }

    private fun uploadFileData() {
        val isListsCreated = fileConverter.createSheetsDataLists(workbook)
        if (isListsCreated) {
            println(" All Lists are Created===>>")
            viewModel.addDiagnosticData(fileConverter.getDiagnosticMap())
            viewModel.addDistributorsData(fileConverter.getDistributorMap())
            viewModel.addDealersData(fileConverter.getDealersMap())
            viewModel.addProductsData(fileConverter.getProductsMap())
        }
    }

    private fun showActionTypeDialog(actionType: String) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)

        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.action_type_dialog, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setPositiveButton(getString(R.string.ok), null)
        dialogBuilder.setNegativeButton(getString(R.string.cancel), null)
        val alertDialog: AlertDialog = dialogBuilder.create()

        alertDialog.setOnShowListener {
            val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                if (!dialogView.action_type.selectedItem.equals("Select")) {
                    when (dialogView.action_type.selectedItem.toString()) {
                        Constants.SHEET_DIAGNOSTIC -> {
                            val intent = Intent(this, DiagnosticActivity::class.java)
                            if (actionType == AdminActionType.EDIT.toString()) {
                                intent.putExtra(
                                    AdminActionType.TYPE.toString(),
                                    AdminActionType.EDIT.toString()
                                )
                            } else {
                                intent.putExtra(
                                    AdminActionType.TYPE.toString(),
                                    AdminActionType.DELETE.toString()
                                )
                            }
                            startActivity(intent)
                        }
                        Constants.SHEET_DEALERS -> {
                            val intent = Intent(this, DealersActivity::class.java)
                            if (actionType == AdminActionType.EDIT.toString()) {
                                intent.putExtra(
                                    AdminActionType.TYPE.toString(),
                                    AdminActionType.EDIT.toString()
                                )
                            } else {
                                intent.putExtra(
                                    AdminActionType.TYPE.toString(),
                                    AdminActionType.DELETE.toString()
                                )
                            }
                            startActivity(intent)
                        }
                        Constants.SHEET_PRODUCTS -> {
                            val intent = Intent(this, ProductsActivity::class.java)
                            if (actionType == AdminActionType.EDIT.toString()) {
                                intent.putExtra(
                                    AdminActionType.TYPE.toString(),
                                    AdminActionType.EDIT.toString()
                                )
                            } else {
                                intent.putExtra(
                                    AdminActionType.TYPE.toString(),
                                    AdminActionType.DELETE.toString()
                                )
                            }
                            startActivity(intent)
                        }
                        Constants.SHEET_DISTRIBUTORS -> {
                            val intent = Intent(this, DistributorsActivity::class.java)
                            if (actionType == AdminActionType.EDIT.toString()) {
                                intent.putExtra(
                                    AdminActionType.TYPE.toString(),
                                    AdminActionType.EDIT.toString()
                                )
                            } else {
                                intent.putExtra(
                                    AdminActionType.TYPE.toString(),
                                    AdminActionType.DELETE.toString()
                                )
                            }
                            startActivity(intent)
                        }
                    }
                    alertDialog.dismiss()
                } else {
                    //TODO()
                    showMessage("Please select an option")
                }
            }
            val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setOnClickListener {
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    enum class AdminActionType {
        EDIT, DELETE, TYPE
    }
}