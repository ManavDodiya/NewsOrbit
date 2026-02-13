package com.newsorbit.util

/**
 * A sealed class representing the result of an operation.
 * Used for handling different states (Loading, Success, Error) in the UI.
 */
sealed class Result<out T> {
    
    /**
     * Represents a successful operation with data.
     */
    data class Success<T>(val data: T) : Result<T>()
    
    /**
     * Represents an error state with a message.
     */
    data class Error(val message: String) : Result<Nothing>()
    
    /**
     * Represents a loading state.
     */
    data object Loading : Result<Nothing>()
}
