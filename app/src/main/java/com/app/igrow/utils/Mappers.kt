package com.app.igrow.utils

import com.app.igrow.data.local.models.entities.DealersEntityName
import com.app.igrow.data.local.models.entities.DiagnosticEntityName
import com.app.igrow.data.local.models.entities.DistributorsEntityName
import com.app.igrow.data.local.models.entities.ProductsEntityName
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.model.sheets.Products

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
            part_affected_fr = diagnostic.part_affected_fr,
            symptoms_impact = diagnostic.symptoms_impact,
            symptoms_impact_fr = diagnostic.symptoms_impact_fr,
            control = diagnostic.control,
            control_fr = diagnostic.control_fr,
            image_sample = diagnostic.image_sample
        ).run {
            diagnosticEntityList.add(this)
        }
    }
    return diagnosticEntityList
}

fun ArrayList<DiagnosticEntityName>.toDiagnosticUIModel(): ArrayList<HashMap<String, String>> {

    val diagnosticUIModelList: ArrayList<HashMap<String, String>> = ArrayList()

    this.forEach { item ->

        val entry: HashMap<String, String> = HashMap<String, String>()

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
        entry[Constants.COL_IMAGE_SAMPLE] = item.image_sample

        diagnosticUIModelList.add(entry)
    }

    return diagnosticUIModelList
}


// Dealer Mappers
fun ArrayList<HashMap<String, String>>.toDealerEntityModel(): List<DealersEntityName> {
    val dealersEntityList: MutableList<DealersEntityName> = ArrayList()

    this.forEach { item ->
        val dealer = Utils.parseHashMapToObject(item, Dealers::class.java) as Dealers
        DealersEntityName(
            id = dealer.id,
            dealer_name = dealer.dealer_name,
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
            distributors_fr = dealer.distributors_fr,
            facebook = dealer.facebook
        ).run {
            dealersEntityList.add(this)
        }
    }

    return dealersEntityList
}

fun ArrayList<DealersEntityName>.toDealerUIModel(): ArrayList<HashMap<String, String>> {

    val dealerUIModelList: ArrayList<HashMap<String, String>> = ArrayList()

    this.forEach { item ->
        val entry: HashMap<String, String> = HashMap<String, String>()

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
        entry[Constants.COL_FACEBOOK] = item.facebook

        dealerUIModelList.add(entry)
    }
    return dealerUIModelList
}


// Distributor Mappers
fun ArrayList<HashMap<String, String>>.toDistributorEntityModel(): List<DistributorsEntityName> {
    val distributorEntityList: MutableList<DistributorsEntityName> = ArrayList()

    this.forEach { item ->
        val distributor = Utils.parseHashMapToObject(item, Distributors::class.java) as Distributors
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
            website = distributor.website
        ).run {
            distributorEntityList.add(this)
        }
    }

    return distributorEntityList
}

fun ArrayList<DistributorsEntityName>.toDistributorUIModel(): ArrayList<HashMap<String, String>> {

    val distributorUIModelList: ArrayList<HashMap<String, String>> = ArrayList()

    this.forEach { item ->
        val entry: HashMap<String, String> = HashMap<String, String>()

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

        distributorUIModelList.add(entry)
    }
    return distributorUIModelList
}

fun DistributorsEntityName.toDistributorUIModel(): Distributors {

    val distributorUIModel = Distributors()

    distributorUIModel.id = this.id
    distributorUIModel.distributor_name = this.distributor_name
    distributorUIModel.distributor_name_fr = this.distributor_name_fr
    distributorUIModel.address = this.address
    distributorUIModel.address_fr = this.address_fr
    distributorUIModel.city_town = this.city_town
    distributorUIModel.city_town_fr = this.city_town_fr
    distributorUIModel.region = this.region
    distributorUIModel.region_fr = this.region_fr
    distributorUIModel.telephone = this.telephone
    distributorUIModel.telephone_2 = this.telephone_2
    distributorUIModel.email = this.email
    distributorUIModel.email_fr = this.email_fr
    distributorUIModel.website = this.website

    return distributorUIModel
}

// Products Mappers
fun ArrayList<HashMap<String, String>>.toProductsEntityModel(): List<ProductsEntityName> {
    val productsEntityName: MutableList<ProductsEntityName> = ArrayList()

    this.forEach { item ->
        val products = Utils.parseHashMapToObject(item, Products::class.java) as Products
        ProductsEntityName(
            id = products.id,
            crop = products.crop,
            crop_fr = products.crop_fr,
            type_of_enemy = products.type_of_enemy,
            type_of_enemy_fr = products.type_of_enemy_fr,
            product_category = products.product_category,
            product_category_fr = products.product_category_fr,
            product_name = products.product_name,
            product_name_fr = products.product_name_fr,
            registration_number = products.registration_number,
            registration_number_fr = products.registration_number_fr,
            active_ingredient = products.active_ingredient,
            active_ingredient_fr = products.active_ingredient_fr,
            composition = products.composition,
            composition_fr = products.composition_fr,
            formulation = products.formulation,
            formulation_fr = products.formulation_fr,
            toxicological_class = products.toxicological_class,
            toxicological_class_fr = products.toxicological_class_fr,
            mode_of_action = products.mode_of_action,
            mode_of_action_fr = products.mode_of_action_fr,
            enemy = products.enemy,
            enemy_fr = products.enemy_fr,
            packaging_available = products.packaging_available,
            packaging_available_fr = products.packaging_available_fr,
            application_rate = products.application_rate,
            application_rate_fr = products.application_rate_fr,
            treatment_time = products.treatment_time,
            treatment_time_fr = products.treatment_time_fr,
            frequency_of_application = products.frequency_of_application,
            frequency_of_application_fr = products.frequency_of_application_fr,
            method_of_application = products.method_of_application,
            method_of_application_fr = products.method_of_application_fr,
            restrictions_of_use = products.restrictions_of_use,
            restrictions_of_use_fr = products.restrictions_of_use_fr,
            re_entry_period = products.re_entry_period,
            re_entry_period_fr = products.re_entry_period_fr,
            time_before_harvest = products.time_before_harvest,
            time_before_harvest_fr = products.time_before_harvest_fr,
            distributor = products.distributor,
            distributor_fr = products.distributor_fr
        ).run {
            productsEntityName.add(this)
        }
    }
    return productsEntityName
}

fun ArrayList<ProductsEntityName>.toProductsUIModel(): ArrayList<HashMap<String, String>> {
    val productsUIModelList: ArrayList<HashMap<String, String>> = ArrayList()

    this.forEach { products ->
        val entry: HashMap<String, String> = HashMap<String, String>()

        entry[Constants.COL_ID] = products.id
        entry[Constants.COL_CROP] = products.crop
        entry[Constants.COL_CROP_FR] = products.crop_fr
        entry[Constants.COL_TYPE_OF_ENEMY] = products.type_of_enemy
        entry[Constants.COL_TYPE_OF_ENEMY_FR] = products.type_of_enemy_fr
        entry[Constants.COL_PRODUCTS_CATEGORY] = products.product_category
        entry[Constants.COL_PRODUCTS_CATEGORY_FR] = products.product_category_fr
        entry[Constants.COL_PRODUCT_NAME] = products.product_name
        entry[Constants.COL_PRODUCT_NAME_FR] = products.product_name_fr
        entry[Constants.COL_REGISTRATION_NUMBER] = products.registration_number
        entry[Constants.COL_REGISTRATION_NUMBER_FR] = products.registration_number_fr
        entry[Constants.COL_ACTIVE_INGREDIENT] = products.active_ingredient
        entry[Constants.COL_ACTIVE_INGREDIENT_FR] = products.active_ingredient_fr
        entry[Constants.COL_COMPOSITION] = products.composition
        entry[Constants.COL_COMPOSITION_FR] = products.composition_fr
        entry[Constants.COL_FORMULATION] = products.formulation
        entry[Constants.COL_FORMULATION_FR] = products.formulation_fr
        entry[Constants.COL_TOXICOLOGICAL_CLASS] = products.toxicological_class
        entry[Constants.COL_TOXICOLOGICAL_CLASS_FR] = products.toxicological_class_fr
        entry[Constants.COL_MODE_OF_ACTION] = products.mode_of_action
        entry[Constants.COL_MODE_OF_ACTION_FR] = products.mode_of_action_fr
        entry[Constants.COL_ENEMY] = products.enemy
        entry[Constants.COL_ENEMY_FR] = products.enemy_fr
        entry[Constants.COL_PACKAGING_AVAILABLE] = products.packaging_available
        entry[Constants.COL_PACKAGING_AVAILABLE_FR] = products.packaging_available_fr
        entry[Constants.COL_APPLICATION_RATE] = products.application_rate
        entry[Constants.COL_APPLICATION_RATE_FR] = products.application_rate_fr
        entry[Constants.COL_TREATMENT_TIME] = products.treatment_time
        entry[Constants.COL_TREATMENT_TIME_FR] = products.treatment_time_fr
        entry[Constants.COL_FREQUENCY_OF_APPLICATION] = products.frequency_of_application
        entry[Constants.COL_FREQUENCY_OF_APPLICATION_FR] = products.frequency_of_application_fr
        entry[Constants.COL_METHOD_OF_APPLICATION] = products.method_of_application
        entry[Constants.COL_METHOD_OF_APPLICATION_FR] = products.method_of_application_fr
        entry[Constants.COL_RESTRICTION_OF_USE] = products.restrictions_of_use
        entry[Constants.COL_RESTRICTION_OF_USE_FR] = products.restrictions_of_use_fr
        entry[Constants.COL_RE_ENTRY_PERIOD] = products.re_entry_period
        entry[Constants.COL_RE_ENTRY_PERIOD_FR] = products.re_entry_period_fr
        entry[Constants.COL_TIME_BEFORE_HARVEST] = products.time_before_harvest
        entry[Constants.COL_TIME_BEFORE_HARVEST_FR] = products.time_before_harvest_fr
        entry[Constants.COL_DISTRIBUTOR] = products.distributor
        entry[Constants.COL_DISTRIBUTOR_FR] = products.distributor_fr

        productsUIModelList.add(entry)
    }

    return productsUIModelList
}



