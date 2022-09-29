package com.app.igrow.helpers

import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.model.sheets.Products
import com.app.igrow.utils.Constants
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream

class FileConverter {
    //ArrayLists
    private var diagnosticDataList = ArrayList<Diagnostic>()
    private var distributorsDataList = ArrayList<Distributors>()
    private var dealerDataList = ArrayList<Dealers>()
    private var productsDataList = ArrayList<Products>()

    //HashMaps
    private var diagnosticDataMap = HashMap<String, Diagnostic>()
    private var distributorsDataMap = HashMap<String, Distributors>()
    private var dealerDataMap = HashMap<String, Dealers>()
    private var productsDataMap = HashMap<String, Products>()

    fun fileToWorkBook(inputStream: InputStream): Workbook {
        return WorkbookFactory.create(inputStream)
    }

    fun createSheetsDataLists(workbook: Workbook): Boolean {
        if (workbook.numberOfSheets > 0) {
            workbook.forEach {
                when (it.sheetName) {
                    Constants.SHEET_DIAGNOSTIC -> {
                        createDiagnosticSheetList(it.rowIterator())
                    }
                    Constants.SHEET_DISTRIBUTORS -> {
                        createDistributorsSheetList(it.rowIterator())
                    }
                    Constants.SHEET_DEALERS -> {
                        createDealerSheetList(it.rowIterator())
                    }
                    Constants.SHEET_PRODUCTS -> {
                        createProductsSheetList(it.rowIterator())
                    }
                }
            }

        }

        return isDataListsCreated()
    }

    private fun isDataListsCreated(): Boolean {
        var isMapCreated = false

        if (diagnosticDataList.size > 0 &&
            distributorsDataList.size > 0 &&
            dealerDataList.size > 0 &&
            productsDataList.size > 0
        ) {
            diagnosticDataMap = diagnosticListToMap(diagnosticDataList)
            distributorsDataMap = distributorListToMap(distributorsDataList)
            dealerDataMap = dealersListToMap(dealerDataList)
            productsDataMap = productsListToMap(productsDataList)

            if (diagnosticDataMap.isEmpty().not() &&
                distributorsDataMap.isEmpty().not() &&
                dealerDataMap.isEmpty().not() && productsDataMap.isEmpty().not()
            ) {
                isMapCreated = true
            }

        }
        return isMapCreated
    }

    /*fun readFile(inputStream: InputStream, viewModel: AdminActivityViewModel) {
        try {
            val workbook = WorkbookFactory.create(inputStream)
            if (workbook.numberOfSheets > 0) {
                var sheetIterator = workbook.sheetIterator()
                while (sheetIterator.hasNext()) {
                    val sheet = sheetIterator.next()
                    when (sheet.sheetName) {
                        Constants.SHEET_DIAGNOSTIC -> {
                            createDiagnosticSheetList(sheet.rowIterator())
                            break
                        }
                        Constants.SHEET_DISTRIBUTORS -> {
                            createDistributorsSheetList(sheet.rowIterator())
                            break
                        }
                        Constants.SHEET_DEALERS -> {
                            createDealerSheetList(sheet.rowIterator())
                            break
                        }
                        Constants.SHEET_PRODUCTS -> {
                            break
                        }
                    }
                }
            }
            if (diagnosticDataList.size > 0) {
                var dataMap = diagnosticListToMap(diagnosticDataList)
                viewModel.addDiagnosticData(dataMap)
            }
            if (distributorsDataList.size > 0) {
                var dataMap = distributorListToMap(distributorsDataList)
                viewModel.addDistributorsData(dataMap)
            }
            if (dealerDataList.size > 0) {
                var dataMap = dealersListToMap(dealerDataList)
                viewModel.addDealersData(dataMap)
            }
            if (productsDataList.size > 0) {
                var dataMap = productsListToMap(productsDataList)
                viewModel.addProductsData(dataMap)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/

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
                }

            }
            println("DistributionList ==> $distributorsDataList")

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
                    if (dealers != null) {
                        dealerDataList.add(dealers)
                    }
                }

            }
            println("dealerList ==> $dealerDataList")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createProductsSheetList(rowIterator: Iterator<Row>) {
        try {
            productsDataList.clear()
            while (rowIterator.hasNext()) {
                val row = rowIterator.next()
                if (row.rowNum != 0) {
                    var products = Products()
                    row.forEach {
                        productsColumnsValueMapper(
                            products,
                            it.columnIndex,
                            it.toString()
                        )
                    }
                    if (products != null) {
                        productsDataList.add(products)
                    }
                }

            }
            println("products List ==> $productsDataList")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    //Sheet Columns Mappers
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

    private fun dealersColumnsValueMapper(dealers: Dealers, cellIndex: Int, cellValue: String) {
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

    private fun productsColumnsValueMapper(products: Products, cellIndex: Int, cellValue: String) {
        try {
            when (cellIndex) {
                0 -> {
                    products.id = cellValue
                    return
                }
                1 -> {
                    products.crop = cellValue
                    return
                }
                2 -> {
                    products.crop_fr = cellValue
                    return
                }
                3 -> {
                    products.type_of_enemy = cellValue
                    return
                }
                4 -> {
                    products.type_of_enemy_fr = cellValue
                    return
                }
                5 -> {
                    products.product_category = cellValue
                    return
                }
                6 -> {
                    products.product_category_fr = cellValue
                    return
                }
                7 -> {
                    products.product_name = cellValue
                    return
                }
                8 -> {
                    products.product_name_fr = cellValue
                    return
                }
                9 -> {
                    products.registration_number = cellValue
                    return
                }
                10 -> {
                    products.registration_number_fr = cellValue
                    return
                }
                11 -> {
                    products.active_ingredient = cellValue
                    return
                }
                12 -> {
                    products.active_ingredient_fr = cellValue
                    return
                }
                13 -> {
                    products.composition = cellValue
                    return
                }
                14 -> {
                    products.composition_fr = cellValue
                    return
                }
                15 -> {
                    products.formulation = cellValue
                    return
                }
                16 -> {
                    products.formulation_fr = cellValue
                    return
                }
                17 -> {
                    products.toxicological_class = cellValue
                    return
                }
                18 -> {
                    products.toxicological_class_fr = cellValue
                    return
                }
                19 -> {
                    products.mode_of_action = cellValue
                    return
                }
                20 -> {
                    products.mode_of_action_fr = cellValue
                    return
                }
                21 -> {
                    products.enemy = cellValue
                    return
                }
                22 -> {
                    products.enemy_fr = cellValue
                    return
                }
                23 -> {
                    products.packaging_available = cellValue
                    return
                }
                24 -> {
                    products.packaging_available_fr = cellValue
                    return
                }
                25 -> {
                    products.application_rate = cellValue
                    return
                }
                26 -> {
                    products.application_rate_fr = cellValue
                    return
                }
                27 -> {
                    products.treatment_time = cellValue
                    return
                }
                28 -> {
                    products.treatment_time_fr = cellValue
                    return
                }
                29 -> {
                    products.frequency_of_application = cellValue
                    return
                }
                30 -> {
                    products.frequency_of_application_fr = cellValue
                    return
                }
                31 -> {
                    products.method_of_application = cellValue
                    return
                }
                32 -> {
                    products.method_of_application_fr = cellValue
                    return
                }
                33 -> {
                    products.restrictions_of_use = cellValue
                    return
                }
                34 -> {
                    products.restrictions_of_use_fr = cellValue
                    return
                }
                35 -> {
                    products.re_entry_period = cellValue
                    return
                }
                36 -> {
                    products.re_entry_period_fr = cellValue
                    return
                }
                37 -> {
                    products.time_before_harvest = cellValue
                    return
                }
                38 -> {
                    products.time_before_harvest_fr = cellValue
                    return
                }
                39 -> {
                    products.distributor = cellValue
                    return
                }
                40 -> {
                    products.distributor_fr = cellValue
                    return
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    //Hashmap Getters
    fun getDistributorMap(): HashMap<String, Distributors> {
        return distributorsDataMap
    }

    fun getDealersMap(): HashMap<String, Dealers> {
        return dealerDataMap
    }

    fun getProductsMap(): HashMap<String, Products> {
        return productsDataMap
    }

    fun getDiagnosticMap(): HashMap<String, Diagnostic> {
        return diagnosticDataMap
    }

    //ListToMapMappers
    private fun diagnosticListToMap(diagnosticList: ArrayList<Diagnostic>): HashMap<String, Diagnostic> {
        val map = HashMap<String, Diagnostic>()
        try {
            diagnosticList.forEach {
                map[it.id] = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }

    private fun distributorListToMap(dataList: ArrayList<Distributors>): HashMap<String, Distributors> {
        val map = HashMap<String, Distributors>()
        try {
            dataList.forEach {
                map[it.id] = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }

    private fun dealersListToMap(dataList: ArrayList<Dealers>): HashMap<String, Dealers> {
        val map = HashMap<String, Dealers>()
        try {
            dataList.forEach {
                map[it.id] = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }

    private fun productsListToMap(dataList: ArrayList<Products>): HashMap<String, Products> {
        val map = HashMap<String, Products>()
        try {
            dataList.forEach {
                map[it.id] = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }


}