package com.app.igrow.data.model.sheets

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Diagnostic(
    var id: String = "",
    var crop: String = "",
    var crop_fr: String = "",
    var type_of_enemy: String = "",
    var type_of_enemy_fr: String = "",
    var plant_health_problem: String = "",
    var plant_health_problem_fr: String = "",
    var causal_agent: String = "",
    var causal_agent_fr: String = "",
    var part_affected: String = "",
    var part_affected_fr: String = "",
    var symptoms_impact: String = "",
    var symptoms_impact_fr: String = "",
    var control: String = "",
    var control_fr: String = "",
): Parcelable
