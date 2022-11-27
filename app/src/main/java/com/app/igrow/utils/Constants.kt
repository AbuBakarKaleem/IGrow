package com.app.igrow.utils

object Constants {

    //End Points
    const val LIVE_END_POINT = "/live"
    const val CURRENCIES_END_POINT = "list"
    const val TIME_OUT = 1500L

    //Other constants
    const val DEFAULT_SOURCE_CURRENCY = "USD"
    const val REMOVE_SOURCE_STRING_FOR_USD = "USDUSD"
    const val WORKER_TAG = "FetchDataWorker"
    const val DATABASE_NAME = "IGrowDatabase.db"
    const val TABLE_CURRENCY = "currencies"
    const val TABLE_CURRENCY_RATES = "currency_rates"
    const val SHEET_DIAGNOSTIC = "Diagnostic"
    const val SHEET_PRODUCTS = "Products"
    const val SHEET_DISTRIBUTORS = "Distributors"
    const val SHEET_DEALERS = "Dealers"
    const val SHEET_VIDEOS = "Videos"
    const val DOCUMENT_ID = "igrow"

    //Diagnostic Sheet Columns
    const val COL_ID = "id"
    const val COL_CROP = "crop"
    const val COL_CROP_FR = "crop_fr"
    const val COL_TYPE_OF_ENEMY = "type_of_enemy"
    const val COL_TYPE_OF_ENEMY_FR = "type_of_enemy_fr"
    const val COL_PLANT_HEALTH_PROBLEM = "plant_health_problem"
    const val COL_PLANT_HEALTH_PROBLEM_FR = "plant_health_problem_fr"
    const val COL_CAUSAL_AGENT = "causal_agent"
    const val COL_CAUSAL_AGENT_FR = "causal_agent_fr"
    const val COL_PART_AFFECTED = "part_affected"
    const val COL_PART_AFFECTED_FR = "part_affected_fr"
    const val COL_SYMPTOMS_IMPACT = "symptoms_impact"
    const val COL_SYMPTOMS_IMPACT_FR = "symptoms_impact_fr"
    const val COL_CONTROL = "control"
    const val COL_CONTROL_FR = "control_fr"

    //Products Sheet Columns
    const val COL_PRODUCTS_CATEGORY = "product_category"
    const val COL_PRODUCTS_CATEGORY_FR = "product_category_fr"
    const val COL_PRODUCT_NAME = "product_name"
    const val COL_PRODUCT_NAME_FR = "product_name_fr"
    const val COL_REGISTRATION_NUMBER = "registration_number"
    const val COL_REGISTRATION_NUMBER_FR = "registration_number_fr"
    const val COL_ACTIVE_INGREDIENT = "active_ingredient"
    const val COL_ACTIVE_INGREDIENT_FR = "active_ingredient_fr"
    const val COL_COMPOSITION = "composition"
    const val COL_COMPOSITION_FR = "composition_fr"
    const val COL_FORMULATION = "formulation"
    const val COL_FORMULATION_FR = "formulation_fr"
    const val COL_TOXICOLOGICAL_CLASS = "toxicological_class"
    const val COL_TOXICOLOGICAL_CLASS_FR = "toxicological_class_fr"
    const val COL_MODE_OF_ACTION = "mode_of_action"
    const val COL_MODE_OF_ACTION_FR = "mode_of_action_fr"
    const val COL_ENEMY = "enemy"
    const val COL_ENEMY_FR = "enemy_fr"
    const val COL_PACKAGING_AVAILABLE = "packaging_available"
    const val COL_PACKAGING_AVAILABLE_FR = "packaging_available_fr"
    const val COL_APPLICATION_RATE = "application_rate"
    const val COL_APPLICATION_RATE_FR = "application_rate_fr"
    const val COL_TREATMENT_TIME = "treatment_time"
    const val COL_TREATMENT_TIME_FR = "treatment_time_fr"
    const val COL_FREQUENCY_OF_APPLICATION = "frequency_of_application"
    const val COL_FREQUENCY_OF_APPLICATION_FR = "frequency_of_application_fr"
    const val COL_METHOD_OF_APPLICATION = "method_of_application"
    const val COL_METHOD_OF_APPLICATION_FR = "method_of_application_fr"
    const val COL_RESTRICTION_OF_USE = "restrictions_of_use"
    const val COL_RESTRICTION_OF_USE_FR = "restrictions_of_use_fr"
    const val COL_RE_ENTRY_PERIOD = "re_entry_period"
    const val COL_RE_ENTRY_PERIOD_FR = "re_entry_period_fr"
    const val COL_TIME_BEFORE_HARVEST = "time_before_harvest"
    const val COL_TIME_BEFORE_HARVEST_FR = "time_before_harvest_fr"
    const val COL_DISTRIBUTOR = "distributor"
    const val COL_DISTRIBUTOR_FR = "distributor_fr"

    //Distributors Sheet Columns
    const val COL_DISTRIBUTORS_NAME = "distributor_name"
    const val COL_DISTRIBUTORS_NAME_FR = "distributor_name_fr"
    const val COL_TELEPHONE_TWO = "telephone_2"
    const val COL_EMAIL = "email"
    const val COL_EMAIL_FR = "email_fr"
    const val COL_WEBSITE = "website"
    const val COL_FACEBOOK = "facebook"

    //Dealer Sheet Columns
    const val COL_DEALER_NAME = "dealer_name"
    const val COL_DEALER_NAME_FR = "dealer_name_fr"
    const val COL_ADDRESS = "address"
    const val COL_ADDRESS_FR = "address_fr"
    const val COL_CITY_TOWN = "city_town"
    const val COL_CITY_TOWN_FR = "city_town_fr"
    const val COL_REGION = "region"
    const val COL_REGION_FR = "region_fr"
    const val COL_TELEPHONE = "telephone"
    const val COL_TELEPHONE_FR = "telephone_fr"
    const val COL_MOBILE = "mobile"
    const val COL_MOBILE_FR = "mobile_fr"
    const val COL_DISTRIBUTORS = "distributors"
    const val COL_DISTRIBUTORS_FR = "distributors_fr"

    // Session Manager Constant
    const val KEY_LANGUAGE = "language"
    const val KEY_COUNTRY = "country"
    const val DEFAULT_LANGUAGE = "fr"
}