package com.app.igrow.utils

import com.app.igrow.data.local.models.entities.DealersEntityName
import com.app.igrow.data.local.models.entities.DiagnosticEntityName
import com.app.igrow.data.local.models.entities.DistributorsEntityName
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.model.sheets.Distributors

// Diagnostic Mappers
fun ArrayList<HashMap<String, String>>.toDiagnosticEntityModel(): List<DiagnosticEntityName> {
    val diagnosticEntityList: MutableList<DiagnosticEntityName> = ArrayList()
    this.forEach { item ->
        val diagnostic = Utils.parseHashMapToObject(item, Diagnostic::class.java) as Diagnostic
        DiagnosticEntityName(
            id = diagnostic.id,
            crop = diagnostic.crop,
            crop_fr = diagnostic.crop_fr,
            type_of_enemy = diagnostic.type_of_enemy,
            type_of_enemy_fr = diagnostic.type_of_enemy_fr,
            plant_health_problem = diagnostic.plant_health_problem,
            plant_health_problem_fr = diagnostic.plant_health_problem_fr,
            causal_agent = diagnostic.causal_agent,
            causal_agent_fr = diagnostic.causal_agent_fr,
            part_affected = diagnostic.part_affected,
            part_affected_fr= diagnostic.part_affected_fr,
            symptoms_impact = diagnostic.symptoms_impact,
            symptoms_impact_fr = diagnostic.symptoms_impact_fr,
            control = diagnostic.control,
            control_fr = diagnostic.control_fr
        ).run {
            diagnosticEntityList.add(this)
        }
    }
    return diagnosticEntityList
}

fun ArrayList<DiagnosticEntityName>.toDiagnosticUIModel(): ArrayList<HashMap<String, String>> {

    val diagnosticUIModelList: ArrayList<HashMap<String, String>> = ArrayList()

    this.forEach { item ->

        val entry : HashMap<String, String> = HashMap<String, String> ()

        entry[Constants.COL_ID] = item.id
        entry[Constants.COL_CROP] = item.crop
        entry[Constants.COL_CROP_FR] = item.crop_fr
        entry[Constants.COL_TYPE_OF_ENEMY] = item.type_of_enemy
        entry[Constants.COL_TYPE_OF_ENEMY_FR] = item.type_of_enemy_fr
        entry[Constants.COL_PLANT_HEALTH_PROBLEM] = item.plant_health_problem
        entry[Constants.COL_PLANT_HEALTH_PROBLEM_FR] = item.plant_health_problem_fr
        entry[Constants.COL_CAUSAL_AGENT] = item.causal_agent
        entry[Constants.COL_CAUSAL_AGENT_FR] = item.causal_agent_fr
        entry[Constants.COL_PART_AFFECTED] = item.part_affected
        entry[Constants.COL_PART_AFFECTED_FR] = item.part_affected_fr
        entry[Constants.COL_SYMPTOMS_IMPACT] = item.symptoms_impact
        entry[Constants.COL_SYMPTOMS_IMPACT_FR] = item.symptoms_impact_fr
        entry[Constants.COL_CONTROL] = item.control
        entry[Constants.COL_CONTROL_FR] = item.control_fr

        diagnosticUIModelList.add(entry)
    }

    return diagnosticUIModelList
}


// Dealer Mappers
fun ArrayList<HashMap<String, String>>.toDealerEntityModel(): List<DealersEntityName> {
    val dealersEntityList: MutableList<DealersEntityName> = ArrayList()

    this.forEach { item ->
        val dealer  = Utils.parseHashMapToObject(item, Dealers::class.java) as Dealers
        DealersEntityName(
            id = dealer.id,
            dealer_name =  dealer.dealer_name,
            dealer_name_fr = dealer.dealer_name_fr,
            address = dealer.address,
            address_fr = dealer.address_fr,
            city_town = dealer.city_town,
            city_town_fr = dealer.city_town_fr,
            region = dealer.region,
            region_fr = dealer.region_fr,
            telephone = dealer.telephone,
            telephone_fr = dealer.telephone_fr,
            mobile = dealer.mobile,
            mobile_fr = dealer.mobile_fr,
            distributors = dealer.distributors,
            distributors_fr = dealer.distributors_fr
        ).run {
            dealersEntityList.add(this)
        }
    }

    return dealersEntityList
}

fun ArrayList<DealersEntityName>.toDealerUIModel(): ArrayList<HashMap<String, String>> {

    val dealerUIModelList: ArrayList<HashMap<String, String>> = ArrayList()

    this.forEach { item ->
        val entry : HashMap<String, String> = HashMap<String, String> ()

        entry[Constants.COL_ID] = item.id
        entry[Constants.COL_DEALER_NAME] = item.dealer_name
        entry[Constants.COL_DEALER_NAME_FR] = item.dealer_name_fr
        entry[Constants.COL_ADDRESS] = item.address
        entry[Constants.COL_ADDRESS_FR] = item.address_fr
        entry[Constants.COL_CITY_TOWN] = item.city_town
        entry[Constants.COL_CITY_TOWN_FR] = item.city_town_fr
        entry[Constants.COL_REGION] = item.region
        entry[Constants.COL_REGION_FR] = item.region_fr
        entry[Constants.COL_TELEPHONE] = item.telephone
        entry[Constants.COL_TELEPHONE_FR] = item.telephone_fr
        entry[Constants.COL_MOBILE] = item.mobile
        entry[Constants.COL_MOBILE_FR] = item.mobile_fr
        entry[Constants.COL_DISTRIBUTORS] = item.distributors
        entry[Constants.COL_DISTRIBUTORS_FR] = item.distributors_fr

        dealerUIModelList.add(entry)
    }
    return dealerUIModelList
}


// Distributor Mappers
fun ArrayList<HashMap<String, String>>.toDistributorEntityModel(): List<DistributorsEntityName> {
    val distributorEntityList: MutableList<DistributorsEntityName> = ArrayList()

    this.forEach { item ->
        val distributor  = Utils.parseHashMapToObject(item, Distributors::class.java) as Distributors
        DistributorsEntityName(
            id = distributor.id,
            distributor_name = distributor.distributor_name,
            distributor_name_fr = distributor.distributor_name_fr,
            address = distributor.address,
            address_fr = distributor.address_fr,
            city_town = distributor.city_town,
            city_town_fr = distributor.city_town_fr,
            region = distributor.region,
            region_fr = distributor.region_fr,
            telephone = distributor.telephone,
            telephone_2 = distributor.telephone_2,
            email = distributor.email,
            email_fr = distributor.email_fr,
            website = distributor.website,
            facebook = distributor.facebook
        ).run {
            distributorEntityList.add(this)
        }
    }

    return distributorEntityList
}

fun ArrayList<DistributorsEntityName>.toDistributorUIModel(): ArrayList<HashMap<String, String>> {

    val distributorUIModelList: ArrayList<HashMap<String, String>> = ArrayList()

    this.forEach { item ->
        val entry : HashMap<String, String> = HashMap<String, String> ()

        entry[Constants.COL_ID] = item.id
        entry[Constants.COL_DISTRIBUTORS_NAME] = item.distributor_name
        entry[Constants.COL_DISTRIBUTORS_NAME_FR] = item.distributor_name_fr
        entry[Constants.COL_ADDRESS] = item.address
        entry[Constants.COL_ADDRESS_FR] = item.address_fr
        entry[Constants.COL_CITY_TOWN] = item.city_town
        entry[Constants.COL_CITY_TOWN_FR] = item.city_town_fr
        entry[Constants.COL_REGION] = item.region
        entry[Constants.COL_REGION_FR] = item.region_fr
        entry[Constants.COL_TELEPHONE] = item.telephone
        entry[Constants.COL_TELEPHONE_TWO] = item.telephone_2
        entry[Constants.COL_EMAIL] = item.email
        entry[Constants.COL_EMAIL_FR] = item.email_fr
        entry[Constants.COL_WEBSITE] = item.website
        entry[Constants.COL_FACEBOOK] = item.facebook

        distributorUIModelList.add(entry)
    }
    return distributorUIModelList
}

