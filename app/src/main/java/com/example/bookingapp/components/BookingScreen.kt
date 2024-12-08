package com.example.bookingapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bookingapp.BookingItem

/**
 * Displays the main booking screen with a list of booking items.
 *
 * @param booking The list of booking items to be displayed on the screen.
 * @param onItemClick The callback function that is triggered when a booking item is clicked (e.g., to toggle its completion).
 * @param onDeleteClick The callback function that is triggered when the delete icon is clicked on a booking item.
 * @param onAddBookingClick The callback function that is triggered when the floating action button is clicked to add a new booking.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    booking: List<BookingItem>,
    onItemClick: (BookingItem) -> Unit,
    onDeleteClick: (BookingItem) -> Unit,
    onAddBookingClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Booking List") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddBookingClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Booking")
            }
        }
    ) { paddingValues ->

        // If there are no bookings, show a message indicating that
        if (booking.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = "No booking entries available",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        } else {
            // Display the list of bookings
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(booking) { bookingItem ->
                    BookingItemCard(item = bookingItem, onItemClick = onItemClick, onDeleteClick = onDeleteClick)
                }
            }
        }
    }
}

/**
 * Displays a single booking item in a card format with a checkbox to mark its completion status and a delete button.
 *
 * @param item The [BookingItem] to be displayed in the card.
 * @param onItemClick The callback function that is triggered when the checkbox is clicked (e.g., to toggle the completion status).
 * @param onDeleteClick The callback function that is triggered when the delete icon is clicked for this booking item.
 */
@Composable
fun BookingItemCard(
    item: BookingItem,
    onItemClick: (BookingItem) -> Unit,
    onDeleteClick: (BookingItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Checkbox for toggling completion status
        Checkbox(
            checked = item.isCompleted,
            onCheckedChange = { onItemClick(item) }
        )

        // Column to display the title and date range of the booking item
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                textAlign = TextAlign.Left
            )

            // If a date range exists, display it
            item.dateRange?.let {
                Text(
                    text = "${convertMillisToDate(it.first ?: 0)} - ${convertMillisToDate(it.second ?: 0)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // Delete icon to remove the booking item
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Booking",
            tint = Color.Red,
            modifier = Modifier
                .clickable { onDeleteClick(item) }
                .padding(8.dp)
        )
    }
}
