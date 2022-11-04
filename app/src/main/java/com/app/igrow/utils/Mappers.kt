package com.app.igrow.utils

import com.app.igrow.data.local.models.entities.DiagnosticEntityName
import com.app.igrow.data.model.sheets.Diagnostic

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
            diagnosticEntityList.addAll(this)
        }

    }





    return diagnosticEntityList
}