package com.example.bookingapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bookingapp.BookingItem

/**
 * A composable screen that allows the user to add a new booking entry.
 *
 * This screen includes an input field for the booking name, a read-only text field for selecting a date range,
 * and a save button that validates the input before adding the new booking to the ViewModel.
 *
 * @param navController The [NavHostController] used for navigation within the app.
 * @param viewModel The [BookingViewModel] that manages the booking list and provides functionality to add a booking.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookingScreen(navController: NavHostController, viewModel: BookingViewModel) {
    var selectedRange by remember { mutableStateOf<Pair<Long?, Long?>>(null to null) }
    var showModal by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Booking Entry") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        // Main content of the AddBookingScreen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(35.dp))

            // Name input field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Date range input field (readonly, shows the selected date range)
            OutlinedTextField(
                value = "${selectedRange.first?.let { convertMillisToDate(it) } ?: ""} - ${selectedRange.second?.let { convertMillisToDate(it) } ?: ""}",
                onValueChange = { },
                label = { Text("Date Range") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showModal = true }) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select date range")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Display date picker modal if showModal is true
            if (showModal) {
                DatePicker().DateRangePickerModal(
                    onDateRangeSelected = { selectedRange = it },
                    onDismiss = { showModal = false }
                )
            }

            // Display error message if any input validation fails
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Save button for adding the booking entry
            Button(
                onClick = {
                    if (name.isEmpty() || selectedRange.first == null || selectedRange.second == null) {
                        errorMessage = "Please fill in all fields."
                    } else {
                        val booking = BookingItem(title = name, isCompleted = false, dateRange = selectedRange)
                        viewModel.addBooking(booking)
                        navController.popBackStack() // Navigate back to the previous screen
                        errorMessage = "" // Reset error message
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Save")
            }
        }
    }
}
