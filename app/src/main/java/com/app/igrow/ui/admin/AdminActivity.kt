package com.app.igrow.ui.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.app.igrow.data.model.sheets.Diagnostic
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
    private var diagnosticDataList = ArrayList<Diagnostic>()
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

    private fun createDiagnosticSheetList(rowIterator: Iterator<Row>) {
        try {
            diagnosticDataList.clear()
            while (rowIterator.hasNext()) {
                val row = rowIterator.next()
                if (row.rowNum != 0) {
                    val cellIterator: Iterator<Cell> = row.cellIterator()
                    var diagnosticData: Diagnostic? = null
                    while (cellIterator.hasNext()) {
                        diagnosticData = Diagnostic()
                        val cell = cellIterator.next()
                        diagnosticsColumnsValueMapper(
                            diagnosticData,
                            cell.columnIndex,
                            cell.toString()
                        )
                        /* val cellInfo =
                             "Cell Comment ${cell.cellComment} -> cell column Index ${cell.columnIndex} -> Cell Sheet Name ${cell.sheet.sheetName} -> Cell to String $cell   ->,"
                         println("Cell Info ->>>>>>$cellInfo")*/
                    }
                    if (diagnosticData != null) {
                        diagnosticDataList.add(diagnosticData)
                    }
                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("Diagnostic List =>${diagnosticDataList}")
    }

    private fun diagnosticsColumnsValueMapper(
        diagnosticData: Diagnostic,
        cellIndex: Int,
        cellValue: String
    ) {
        try {
            when (cellIndex) {
                0 -> {
                    diagnosticData.id = cellValue
                    return
                }
                1 -> {
                    diagnosticData.crop = cellValue
                    return
                }
                2 -> {
                    diagnosticData.crop_fr = cellValue
                    return
                }
                3 -> {
                    diagnosticData.type_of_enemy = cellValue
                    return
                }
                4 -> {
                    diagnosticData.type_of_enemy_fr = cellValue
                    return
                }
                5 -> {
                    diagnosticData.plant_health_problem = cellValue
                    return
                }
                6 -> {
                    diagnosticData.plant_health_problem_fr = cellValue
                    return
                }
                7 -> {
                    diagnosticData.causal_agent = cellValue
                    return
                }
                8 -> {
                    diagnosticData.causal_agent_fr = cellValue
                    return
                }
                9 -> {
                    diagnosticData.part_affected = cellValue
                    return
                }
                10 -> {
                    diagnosticData.part_affected_fr = cellValue
                    return
                }
                11 -> {
                    diagnosticData.symptoms_impact = cellValue
                    return
                }
                12 -> {
                    diagnosticData.symptoms_impact_fr = cellValue
                    return
                }
                13 -> {
                    diagnosticData.control = cellValue
                    return
                }
                14 -> {
                    diagnosticData.control_fr = cellValue
                    return
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}