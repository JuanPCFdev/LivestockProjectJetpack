package com.example.livestockjetpackcompose.ui.screens.cows.lifting

import android.annotation.SuppressLint
import co.yml.charts.common.model.Point
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.livestockjetpackcompose.R
import com.example.livestockjetpackcompose.domain.model.LiftingPerformance
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.utils.SimpleMultipurposeCard
import com.example.livestockjetpackcompose.ui.viewmodels.cows.lifting.LiftingStatsViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun LiftingStatsScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    cowKey: String,
    onRegisterLiftingPerformance: () -> Unit,
    viewModel: LiftingStatsViewModel = hiltViewModel()
) {

    val liftingList = viewModel.liftingList.collectAsState()
    val keys = viewModel.keys.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedLifting by remember { mutableStateOf<LiftingPerformance?>(null) }

    LaunchedEffect(userKey) {
        viewModel.loadLiftingPerformanceData(userKey, farmKey, cowKey)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(vertical = 45.dp, horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Estadísticas de levante")
        CardWeightList(
            modifier = Modifier.weight(2f),
            liftingList = liftingList.value,
            keys = keys.value,
            liftingSelected = { date ->
                val selected = liftingList.value?.find { it.PLDate == date }
                if (selected != null) {
                    selectedLifting = selected
                    showDialog = true
                }
            },
            viewModel = viewModel,
            userKey = userKey,
            farmKey = farmKey,
            cowKey = cowKey
        )
        GraphCard(Modifier.weight(3f), liftingList.value)
        when (uiState) {
            is LiftingStatsViewModel.UiState.Error -> {
                Text(
                    text = (uiState as LiftingStatsViewModel.UiState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            LiftingStatsViewModel.UiState.Loading -> CircularProgressIndicator()
            else -> Unit
        }
        RegisterWeightButton(Modifier.weight(1f)) {
            onRegisterLiftingPerformance()
        }

        if (showDialog && selectedLifting != null) {
            WeightDetailsDialog(
                liftingPerformance = selectedLifting!!,
                onDismiss = { showDialog = false }
            )
        }

    }
}

@Composable
private fun CardWeightList(
    modifier: Modifier,
    liftingList: List<LiftingPerformance>?,
    keys: List<String>?,
    liftingSelected: (String) -> Unit,
    viewModel: LiftingStatsViewModel,
    userKey: String,
    farmKey: String,
    cowKey: String
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 11.dp)
            .background(background_app)
    ) {
        if (liftingList != null && keys != null) {
            val mixList = liftingList.zip(keys)

            items(mixList) { (lifting, key) ->
                val swipeLeft = SwipeAction(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "",
                            modifier = Modifier
                                .size(48.dp)
                                .padding(12.dp)
                        )
                    },
                    background = Color.Red,
                    isUndo = true,
                    onSwipe = {
                        viewModel.deleteLiftingPerformance(userKey, farmKey, cowKey, key)
                    }
                )

                SwipeableActionsBox(endActions = listOf(swipeLeft)) {
                    SimpleMultipurposeCard(text = lifting.PLDate) {
                        liftingSelected(lifting.PLDate)
                    }
                }
            }
        } else {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Aun no hay vacunas registradas de este tipo",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun WeightDetailsDialog(
    liftingPerformance: LiftingPerformance,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Detalles del Registro", fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                Text(
                    text = "Fecha: ${liftingPerformance.PLDate}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Peso: ${liftingPerformance.PLWeight} kg",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Dieta: ${liftingPerformance.PLDiet}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}

@SuppressLint("DefaultLocale")
@Composable
private fun GraphCard(modifier: Modifier, liftingList: List<LiftingPerformance>?) {

    if (!liftingList.isNullOrEmpty()) {

        // Convertir liftingList a pointsData
        val pointsData = liftingList.mapIndexed { index, lifting ->
            Point(index.toFloat(), lifting.PLWeight.toFloat())
        }

        val steps = 5
        val maxWeight = liftingList.maxOfOrNull { it.PLWeight } ?: 100f // Máximo peso para el eje Y
        val minWeight = liftingList.minOfOrNull { it.PLWeight } ?: 0f   // Mínimo peso para el eje Y


        val xAxisData = AxisData.Builder()
            .axisStepSize(100.dp)
            .backgroundColor(Color.Transparent)
            .steps(pointsData.size - 1)
            .labelData { i -> i.toString() } // Etiquetas del eje X (índices)
            .labelAndAxisLinePadding(15.dp)
            .axisLineColor(MaterialTheme.colorScheme.tertiary)
            .axisLabelColor(MaterialTheme.colorScheme.tertiary)
            .build()

        val yAxisData = AxisData.Builder()
            .steps(steps)
            .backgroundColor(Color.Transparent)
            .labelAndAxisLinePadding(20.dp)
            .labelData { i ->
                // Escalar el eje Y según el peso máximo y mínimo
                val yScale = (maxWeight.toFloat() - minWeight.toFloat()) / steps
                String.format("%.1f", minWeight.toFloat() + (i * yScale))
            }
            .axisLineColor(MaterialTheme.colorScheme.tertiary)
            .axisLabelColor(MaterialTheme.colorScheme.tertiary)
            .build()

        val lineChartData = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = pointsData,
                        LineStyle(
                            color = MaterialTheme.colorScheme.tertiary,
                            lineType = LineType.SmoothCurve(isDotted = false)
                        ),
                        IntersectionPoint(
                            color = MaterialTheme.colorScheme.tertiary
                        ),
                        SelectionHighlightPoint(color = MaterialTheme.colorScheme.tertiary),
                        ShadowUnderLine(
                            alpha = 0.5f,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.tertiary,
                                    Color.Transparent
                                )
                            )
                        ),
                        SelectionHighlightPopUp()
                    )
                )
            ),
            backgroundColor = MaterialTheme.colorScheme.surface,
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines(color = MaterialTheme.colorScheme.outlineVariant)
        )

        LineChart(
            modifier = modifier.fillMaxSize(),
            lineChartData = lineChartData
        )

    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No hay datos para mostrar", color = Color.Gray)
        }
        return
    }
}

@Composable
private fun RegisterWeightButton(modifier: Modifier, onButtonPressed: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Registrar Peso") {
            onButtonPressed()
        }
    }
}