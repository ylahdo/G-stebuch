package com.example.bookingapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.livedata.observeAsState
import com.example.bookingapp.ui.theme.BookingAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.bookingapp.components.AddBookingScreen
import com.example.bookingapp.components.BookingScreen
import com.example.bookingapp.components.BookingViewModel

class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is created to set up the UI and navigation.
     *
     * @param savedInstanceState The saved state of the activity, if available.
     */
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BookingAppTheme {
                // Create a Navigation Controller for the app
                val navController = rememberNavController()

                // Create the ViewModel for managing booking data
                val viewModel: BookingViewModel = viewModel()

                // Define the navigation within the app
                NavHost(navController = navController, startDestination = "booking_screen") {
                    // The main booking screen
                    composable("booking_screen") {
                        // Displays the booking screen and passes required functions
                        BookingScreen(
                            booking = viewModel.booking.observeAsState().value ?: emptyList(),
                            onItemClick = { item -> viewModel.toggleCompletion(item) },
                            onDeleteClick = { item -> viewModel.deleteBooking(item) },
                            onAddBookingClick = { navController.navigate("add_booking_screen") }
                        )
                    }
                    // The screen for adding a new booking
                    composable("add_booking_screen") {
                        AddBookingScreen(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }
}

/**
 * Data class representing a booking.
 *
 * @property title The title of the booking.
 * @property isCompleted The status indicating if the booking is completed.
 * @property dateRange The date range of the booking, represented as a pair of start and end date (in milliseconds).
 */
data class BookingItem(
    val title: String,
    val isCompleted: Boolean,
    val dateRange: Pair<Long?, Long?>?
)
