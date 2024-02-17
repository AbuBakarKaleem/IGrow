package com.app.igrow.data.local.abstraction

import com.app.igrow.data.local.models.entities.DistributorsEntityName

interface DistributorsRepo {

    suspend fun getAllDistributors(): ArrayList<DistributorsEntityName>
    suspend fun insertDistributors(dataList: List<DistributorsEntityName>)
    suspend fun getDistributorsCount(): Int
    suspend fun getDistributorsColumnData(filtersMap: HashMap<String, String>, sheetName: String, columnName: String): List<String>
    suspend fun getDistributorByName(name:String,columnName: String): DistributorsEntityName
}