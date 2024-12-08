package com.example.bookingapp.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookingapp.BookingItem

/**
 * [BookingViewModel] is a ViewModel class that holds and manages the list of booking items.
 * It provides functions to add, delete, and toggle the completion status of bookings.
 */
class BookingViewModel : ViewModel() {

    /**
     * Private mutable LiveData holding the list of booking items.
     * Initialized as an empty list.
     */
    private val _booking = MutableLiveData<List<BookingItem>>(emptyList())

    /**
     * Publicly exposed immutable LiveData to observe the list of booking items.
     * This is used to get the current list of bookings.
     */
    val booking: LiveData<List<BookingItem>> get() = _booking

    /**
     * Adds a new booking item to the list of bookings.
     *
     * @param booking The [BookingItem] to be added to the list.
     */
    fun addBooking(booking: BookingItem) {
        val currentList = _booking.value ?: emptyList()
        val updatedList = currentList + booking
        _booking.value = updatedList
    }

    /**
     * Deletes a specific booking item from the list.
     *
     * @param bookingItem The [BookingItem] to be deleted from the list.
     */
    fun deleteBooking(bookingItem: BookingItem) {
        val currentList = _booking.value ?: emptyList()
        _booking.value = currentList.filterNot { it == bookingItem }
    }

    /**
     * Toggles the completion status of a specific booking item.
     * If the item is marked as completed, it will be marked as not completed, and vice versa.
     *
     * @param bookingItem The [BookingItem] whose completion status will be toggled.
     */
    fun toggleCompletion(bookingItem: BookingItem) {
        val currentList = _booking.value ?: emptyList()
        _booking.value = currentList.map {
            if (it == bookingItem) it.copy(isCompleted = !it.isCompleted) else it
        }
    }
}
