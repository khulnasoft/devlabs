package com.khulnasoft.commons.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Base DTO class with common functionality.
 */
public abstract class BaseDto {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String version;

    public BaseDto() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.version = "1.0";
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseDto)) return false;
        BaseDto baseDto = (BaseDto) o;
        return Objects.equals(createdAt, baseDto.createdAt) &&
               Objects.equals(updatedAt, baseDto.updatedAt) &&
               Objects.equals(version, baseDto.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, updatedAt, version);
    }
}

/**
 * User data transfer object.
 */
public class UserDto extends BaseDto {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Boolean active;
    private String role;

    public UserDto() {
        super();
    }

    public UserDto(Long id, String username, String email) {
        this();
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        if (!super.equals(o)) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) &&
               Objects.equals(username, userDto.username) &&
               Objects.equals(email, userDto.email) &&
               Objects.equals(firstName, userDto.firstName) &&
               Objects.equals(lastName, userDto.lastName) &&
               Objects.equals(phone, userDto.phone) &&
               Objects.equals(active, userDto.active) &&
               Objects.equals(role, userDto.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, username, email, firstName, lastName, phone, active, role);
    }
}

/**
 * Product data transfer object.
 */
public class ProductDto extends BaseDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private Integer stockQuantity;
    private Boolean available;
    private String sku;

    public ProductDto() {
        super();
    }

    public ProductDto(Long id, String name, Double price) {
        this();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDto)) return false;
        if (!super.equals(o)) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(name, that.name) &&
               Objects.equals(description, that.description) &&
               Objects.equals(price, that.price) &&
               Objects.equals(category, that.category) &&
               Objects.equals(stockQuantity, that.stockQuantity) &&
               Objects.equals(available, that.available) &&
               Objects.equals(sku, that.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, description, price, category, stockQuantity, available, sku);
    }
}

/**
 * Order data transfer object.
 */
public class OrderDto extends BaseDto {

    private Long id;
    private Long userId;
    private String orderNumber;
    private Double totalAmount;
    private String status;
    private LocalDateTime orderDate;
    private String shippingAddress;

    public OrderDto() {
        super();
    }

    public OrderDto(Long id, Long userId, Double totalAmount, String status) {
        this();
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDto)) return false;
        if (!super.equals(o)) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(id, orderDto.id) &&
               Objects.equals(userId, orderDto.userId) &&
               Objects.equals(orderNumber, orderDto.orderNumber) &&
               Objects.equals(totalAmount, orderDto.totalAmount) &&
               Objects.equals(status, orderDto.status) &&
               Objects.equals(orderDate, orderDto.orderDate) &&
               Objects.equals(shippingAddress, orderDto.shippingAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, userId, orderNumber, totalAmount, status, orderDate, shippingAddress);
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiResponse)) return false;
        ApiResponse<?> that = (ApiResponse<?>) o;
        return success == that.success &&
               Objects.equals(message, that.message) &&
               Objects.equals(data, that.data) &&
               Objects.equals(errorCode, that.errorCode) &&
               Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, data, errorCode, timestamp);
    }
}
