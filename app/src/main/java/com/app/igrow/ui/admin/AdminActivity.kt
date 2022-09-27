package com.app.igrow.ui.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.app.igrow.databinding.ActivityAdminBinding
import com.app.igrow.utils.Constants.SHEET_DEALERS
import com.app.igrow.utils.Constants.SHEET_DIAGNOSTIC
import com.app.igrow.utils.Constants.SHEET_DISTRIBUTORS
import com.app.igrow.utils.Constants.SHEET_PRODUCTS
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream


class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.uploadFile.setOnClickListener {
            showFileChooser()
        }
    }

    private fun showFileChooser() {
        var fileChooserIntent = Intent(Intent.ACTION_GET_CONTENT)
        fileChooserIntent.addCategory(Intent.CATEGORY_OPENABLE)
        fileChooserIntent.type = "*/*"
        val mimeTypes = arrayOf(
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",  // .xls & .xlsx
        )
        fileChooserIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        fileChooserIntent = Intent.createChooser(fileChooserIntent, "Pick a file")
        chooseFile.launch(fileChooserIntent)
    }

    private val chooseFile = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            val selectedFileUri: Intent? = it.data
            val uri: Uri? = selectedFileUri?.data
            if (uri != null) {
                readFile(uri)
            }
        }
    }

    private fun readFile(uri: Uri) {
        try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val workbook = WorkbookFactory.create(inputStream)
            if (workbook.numberOfSheets > 0) {
                var sheetIterator = workbook.sheetIterator()
                while (sheetIterator.hasNext()) {
                    val sheet = sheetIterator.next()
                    when (sheet.sheetName) {
                        SHEET_DIAGNOSTIC -> {
                            createDiagnosticSheetList(sheet.rowIterator())
                            break
                        }
                        SHEET_DISTRIBUTORS -> {
                            break
                        }
                        SHEET_PRODUCTS -> {
                            break
                        }
                        SHEET_DEALERS -> {
                            break
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun createDiagnosticSheetList(rowIterator: Iterator<Row>){
        try {
            while (rowIterator.hasNext()) {
                val row = rowIterator.next()
                if (row.rowNum != 0) {
                    val cellIterator: Iterator<Cell> = row.cellIterator()
                    while (cellIterator.hasNext()) {
                        val cell = cellIterator.next()
                        val cellInfo =
                            "Cell Comment ${cell.cellComment} -> cell column Index ${cell.columnIndex} -> Cell Sheet Name ${cell.sheet.sheetName} -> Cell to String $cell   ->,"
                        println("Cell Info ->>>>>>$cellInfo")
                    }
                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}