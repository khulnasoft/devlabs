package com.khulnasoft.commons.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

package com.khulnasoft.commons.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for REST controllers.
 * Provides consistent error response format across all services.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @ExceptionHandler(com.khulnasoft.commons.rest.ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            com.khulnasoft.commons.rest.ValidationException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation Failed",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Invalid Argument",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.khulnasoft.commons.rest.ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            com.khulnasoft.commons.rest.ResourceNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Resource Not Found",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "An unexpected error occurred",
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Error response DTO.
     */
    public static class ErrorResponse {
        private int status;
        private String error;
        private String message;
        private String path;
        private LocalDateTime timestamp;

        public ErrorResponse() {}

        public ErrorResponse(int status, String error, String message, String path, LocalDateTime timestamp) {
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
            this.timestamp = timestamp;
        }

        // Getters and setters
        public int getStatus() { return status; }
        public void setStatus(int status) { this.status = status; }

        public String getError() { return error; }
        public void setError(String error) { this.error = error; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public String getPath() { return path; }
        public void setPath(String path) { this.path = path; }

        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }
}

/**
 * Base exception for validation errors.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Exception for resource not found errors.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Base REST controller with common functionality.
 */
abstract class BaseRestController {

    protected final ObjectMapper objectMapper;

    public BaseRestController() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Creates a successful response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    /**
     * Creates a successful response with custom message.
     */
    protected <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(ApiResponse.success(message, data));
    }

    /**
     * Creates an error response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> error(String message) {
        return ResponseEntity.badRequest().body(ApiResponse.error(message));
    }

    /**
     * Creates a not found response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a created response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created successfully", data));
    }
}

/**
 * API Response wrapper for consistent response format.
 */
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private String errorCode;
    private LocalDateTime timestamp;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(boolean success, String message) {
        this();
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, T data) {
        this(success, message);
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message);
    }

    public static <T> ApiResponse<T> error(String message, String errorCode) {
        ApiResponse<T> response = new ApiResponse<>(false, message);
        response.errorCode = errorCode;
        return response;
    }

    // Getters and setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

/**
 * Utility methods for REST operations.
 */
public class RestUtils {

    /**
     * Extracts pagination parameters from request.
     */
    public static PaginationParams extractPaginationParams(Integer page, Integer size) {
        int pageNumber = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = (size != null && size > 0) ? size : 20;
        return new PaginationParams(pageNumber, pageSize);
    }

    /**
     * Creates pagination metadata.
     */
    public static Map<String, Object> createPaginationMetadata(long totalElements, int page, int size) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("totalElements", totalElements);
        metadata.put("totalPages", (totalElements + size - 1) / size);
        metadata.put("currentPage", page);
        metadata.put("pageSize", size);
        metadata.put("hasNext", (long) (page + 1) * size < totalElements);
        metadata.put("hasPrevious", page > 0);
        return metadata;
    }
}

/**
 * Pagination parameters.
 */
public class PaginationParams {
    private final int page;
    private final int size;

    public PaginationParams(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() { return page; }
    public int getSize() { return size; }
    public int getOffset() { return page * size; }
}

/**
 * Global exception handler for REST controllers.
 * Provides consistent error response format across all services.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @ExceptionHandler(com.khulnasoft.commons.rest.ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            com.khulnasoft.commons.rest.ValidationException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation Failed",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Invalid Argument",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.khulnasoft.commons.rest.ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            com.khulnasoft.commons.rest.ResourceNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Resource Not Found",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "An unexpected error occurred",
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Error response DTO.
     */
    public static class ErrorResponse {
        private int status;
        private String error;
        private String message;
        private String path;
        private LocalDateTime timestamp;

        public ErrorResponse() {}

        public ErrorResponse(int status, String error, String message, String path, LocalDateTime timestamp) {
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
            this.timestamp = timestamp;
        }

        // Getters and setters
        public int getStatus() { return status; }
        public void setStatus(int status) { this.status = status; }

        public String getError() { return error; }
        public void setError(String error) { this.error = error; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public String getPath() { return path; }
        public void setPath(String path) { this.path = path; }

        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }
}

/**
 * Base exception for validation errors.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Exception for resource not found errors.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Base REST controller with common functionality.
 */
public abstract class BaseRestController {

    protected final ObjectMapper objectMapper;

    public BaseRestController() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Creates a successful response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    /**
     * Creates a successful response with custom message.
     */
    protected <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(ApiResponse.success(message, data));
    }

    /**
     * Creates an error response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> error(String message) {
        return ResponseEntity.badRequest().body(ApiResponse.error(message));
    }

    /**
     * Creates a not found response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a created response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created successfully", data));
    }
}

/**
 * API Response wrapper for consistent response format.
 */
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private String errorCode;
    private LocalDateTime timestamp;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(boolean success, String message) {
        this();
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, T data) {
        this(success, message);
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message);
    }

    public static <T> ApiResponse<T> error(String message, String errorCode) {
        ApiResponse<T> response = new ApiResponse<>(false, message);
        response.errorCode = errorCode;
        return response;
    }

    // Getters and setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

/**
 * Utility methods for REST operations.
 */
public class RestUtils {

    /**
     * Extracts pagination parameters from request.
     */
    public static PaginationParams extractPaginationParams(Integer page, Integer size) {
        int pageNumber = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = (size != null && size > 0) ? size : 20;
        return new PaginationParams(pageNumber, pageSize);
    }

    /**
     * Creates pagination metadata.
     */
    public static Map<String, Object> createPaginationMetadata(long totalElements, int page, int size) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("totalElements", totalElements);
        metadata.put("totalPages", (totalElements + size - 1) / size);
        metadata.put("currentPage", page);
        metadata.put("pageSize", size);
        metadata.put("hasNext", (long) (page + 1) * size < totalElements);
        metadata.put("hasPrevious", page > 0);
        return metadata;
    }
}

/**
 * Pagination parameters.
 */
public class PaginationParams {
    private final int page;
    private final int size;

    public PaginationParams(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() { return page; }
    public int getSize() { return size; }
    public int getOffset() { return page * size; }
}

/**
 * Base REST controller with common functionality.
 */
public abstract class BaseRestController {

    protected final ObjectMapper objectMapper;

    public BaseRestController() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Creates a successful response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    /**
     * Creates a successful response with custom message.
     */
    protected <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(ApiResponse.success(message, data));
    }

    /**
     * Creates an error response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> error(String message) {
        return ResponseEntity.badRequest().body(ApiResponse.error(message));
    }

    /**
     * Creates a not found response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a created response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created successfully", data));
    }
}

/**
 * API Response wrapper for consistent response format.
 */
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private String errorCode;
    private LocalDateTime timestamp;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(boolean success, String message) {
        this();
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, T data) {
        this(success, message);
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message);
    }

    public static <T> ApiResponse<T> error(String message, String errorCode) {
        ApiResponse<T> response = new ApiResponse<>(false, message);
        response.errorCode = errorCode;
        return response;
    }

    // Getters and setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

/**
 * Utility methods for REST operations.
 */
public class RestUtils {

    /**
     * Extracts pagination parameters from request.
     */
    public static PaginationParams extractPaginationParams(Integer page, Integer size) {
        int pageNumber = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = (size != null && size > 0) ? size : 20;
        return new PaginationParams(pageNumber, pageSize);
    }

    /**
     * Creates pagination metadata.
     */
    public static Map<String, Object> createPaginationMetadata(long totalElements, int page, int size) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("totalElements", totalElements);
        metadata.put("totalPages", (totalElements + size - 1) / size);
        metadata.put("currentPage", page);
        metadata.put("pageSize", size);
        metadata.put("hasNext", (long) (page + 1) * size < totalElements);
        metadata.put("hasPrevious", page > 0);
        return metadata;
    }
}

/**
 * Pagination parameters.
 */
public class PaginationParams {
    private final int page;
    private final int size;

    public PaginationParams(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() { return page; }
    public int getSize() { return size; }
    public int getOffset() { return page * size; }
}
