package com.app.igrow.ui.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.databinding.ActivityAdminBinding
import com.app.igrow.utils.Constants.SHEET_DEALERS
import com.app.igrow.utils.Constants.SHEET_DIAGNOSTIC
import com.app.igrow.utils.Constants.SHEET_DISTRIBUTORS
import com.app.igrow.utils.Constants.SHEET_PRODUCTS
import dagger.hilt.android.AndroidEntryPoint
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private var diagnosticDataList = ArrayList<Diagnostic>()
    private var distributorsDataList = ArrayList<Distributors>()
    private var dealerDataList = ArrayList<Dealers>()
    private val viewModel: AdminActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        startObserving()
    }

    private fun initView() {
        binding.uploadFile.setOnClickListener {
            showFileChooser()
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
                            createDistributorsSheetList(sheet.rowIterator())
                            break
                        }
                        SHEET_DEALERS -> {
                            createDealerSheetList(sheet.rowIterator())
                            break
                        }
                        SHEET_PRODUCTS -> {
                            break
                        }
                    }
                }
            }
            if (diagnosticDataList.size > 0) {
                viewModel.addDiagnosticData(diagnosticDataList)
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
                    var diagnosticData = Diagnostic()
                    row.forEach {
                        diagnosticsColumnsValueMapper(
                            diagnosticData,
                            it.columnIndex,
                            it.toString()
                        )
                    }
                    diagnosticDataList.add(diagnosticData)
                    /* val cellIterator: Iterator<Cell> = row.cellIterator()
                    var diagnosticData: Diagnostic? = null
                    while (cellIterator.hasNext()) {
                        diagnosticData = Diagnostic()
                        val cell = cellIterator.next()
                        diagnosticsColumnsValueMapper(
                            diagnosticData,
                            cell.columnIndex,
                            cell.toString()
                        )*/
                    /* val cellInfo =
                             "Cell Comment ${cell.cellComment} -> cell column Index ${cell.columnIndex} -> Cell Sheet Name ${cell.sheet.sheetName} -> Cell to String $cell   ->,"
                         println("Cell Info ->>>>>>$cellInfo")*/
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("Diagnostic List =>${diagnosticDataList}")
    }

    private fun createDistributorsSheetList(rowIterator: Iterator<Row>) {
        try {
            distributorsDataList.clear()
            while (rowIterator.hasNext()) {
                val row = rowIterator.next()
                if (row.rowNum != 0) {
                    val distributors = Distributors()
                    row.forEach {
                        distributorsColumnsValueMapper(
                            distributors,
                            it.columnIndex,
                            it.toString()
                        )

                    }
                    if (distributors != null) {
                        distributorsDataList.add(distributors)
                    }
                    /* while (cellIterator.hasNext()) {
                         distributors = Distributors()
                         val cell = cellIterator.next()
                         distributorsColumnsValueMapper(
                             distributors,
                             cell.columnIndex,
                             cell.toString()
                         )
                         *//* val cellInfo =
                             "Cell Comment ${cell.cellComment} -> cell column Index ${cell.columnIndex} -> Cell Sheet Name ${cell.sheet.sheetName} -> Cell to String $cell   ->,"
                         println("Cell Info ->>>>>>$cellInfo")*//*
                    }*/
                }

            }
            println("distributionList ==> $distributorsDataList")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createDealerSheetList(rowIterator: Iterator<Row>) {
        try {
            dealerDataList.clear()
            while (rowIterator.hasNext()) {
                val row = rowIterator.next()
                if (row.rowNum != 0) {
                    var dealers = Dealers()
                    row.forEach {
                        dealersColumnsValueMapper(
                            dealers,
                            it.columnIndex,
                            it.toString()
                        )
                    }
                    /* while (cellIterator.hasNext()) {
                         dealers = Dealers()
                         val cell = cellIterator.next()
                         dealersColumnsValueMapper(
                             dealers,
                             cell.columnIndex,
                             cell.toString()
                         )
                         *//* val cellInfo =
                             "Cell Comment ${cell.cellComment} -> cell column Index ${cell.columnIndex} -> Cell Sheet Name ${cell.sheet.sheetName} -> Cell to String $cell   ->,"
                         println("Cell Info ->>>>>>$cellInfo")*//*
                    }*/
                    if (dealers != null) {
                        dealerDataList.add(dealers)
                    }
                }

            }
            println("distributionList ==> $dealerDataList")

        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    private fun distributorsColumnsValueMapper(
        distributors: Distributors,
        cellIndex: Int,
        cellValue: String
    ) {
        try {
            when (cellIndex) {
                0 -> {
                    distributors.id = cellValue
                    return
                }
                1 -> {
                    distributors.distributor_name = cellValue
                    return
                }
                2 -> {
                    distributors.distributor_name_fr = cellValue
                    return
                }
                3 -> {
                    distributors.address = cellValue
                    return
                }
                4 -> {
                    distributors.address_fr = cellValue
                    return
                }
                5 -> {
                    distributors.city_town = cellValue
                    return
                }
                6 -> {
                    distributors.city_town_fr = cellValue
                    return
                }
                7 -> {
                    distributors.region = cellValue
                    return
                }
                8 -> {
                    distributors.region_fr = cellValue
                    return
                }
                9 -> {
                    distributors.telephone = cellValue
                    return
                }
                10 -> {
                    distributors.telephone_2 = cellValue
                    return
                }
                11 -> {
                    distributors.email = cellValue
                    return
                }
                12 -> {
                    distributors.email_fr = cellValue
                    return
                }
                13 -> {
                    distributors.website = cellValue
                    return
                }
                14 -> {
                    distributors.facebook = cellValue
                    return
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dealersColumnsValueMapper(
        dealers: Dealers,
        cellIndex: Int,
        cellValue: String
    ) {
        try {
            when (cellIndex) {
                0 -> {
                    dealers.id = cellValue
                    return
                }
                1 -> {
                    dealers.dealer_name = cellValue
                    return
                }
                2 -> {
                    dealers.dealer_name_fr = cellValue
                    return
                }
                3 -> {
                    dealers.address = cellValue
                    return
                }
                4 -> {
                    dealers.address_fr = cellValue
                    return
                }
                5 -> {
                    dealers.city_town = cellValue
                    return
                }
                6 -> {
                    dealers.city_town_fr = cellValue
                    return
                }
                7 -> {
                    dealers.region = cellValue
                    return
                }
                8 -> {
                    dealers.region_fr = cellValue
                    return
                }
                9 -> {
                    dealers.telephone = cellValue
                    return
                }
                10 -> {
                    dealers.telephone_fr = cellValue
                    return
                }
                11 -> {
                    dealers.mobile = cellValue
                    return
                }
                12 -> {
                    dealers.mobile_fr = cellValue
                    return
                }
                13 -> {
                    dealers.distributors = cellValue
                    return
                }
                14 -> {
                    dealers.distributors_fr = cellValue
                    return
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}