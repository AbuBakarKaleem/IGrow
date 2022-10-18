package com.app.igrow.ui.admin

sealed class AdminUIStates
object LoadingState:AdminUIStates()
object UnloadingState:AdminUIStates()
