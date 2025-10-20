// Utility functions for the application
const utils = {
    /**
     * Formats a number as currency
     * @param {number} amount - The amount to format
     * @param {string} currency - The currency code (default: 'USD')
     * @returns {string} Formatted currency string
     */
    formatCurrency: (amount, currency = 'USD') => {
        return new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: currency
        }).format(amount);
    },

    /**
     * Validates an email address
     * @param {string} email - The email to validate
     * @returns {boolean} True if email is valid
     */
    isValidEmail: (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    },

    /**
     * Debounces a function call
     * @param {Function} func - The function to debounce
     * @param {number} delay - Delay in milliseconds
     * @returns {Function} Debounced function
     */
    debounce: (func, delay) => {
        let timeoutId;
        return (...args) => {
            clearTimeout(timeoutId);
            timeoutId = setTimeout(() => func.apply(null, args), delay);
        };
    },

    /**
     * Generates a random ID
     * @param {number} length - Length of the ID (default: 8)
     * @returns {string} Random ID
     */
    generateId: (length = 8) => {
        const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        let result = '';
        for (let i = 0; i < length; i++) {
            result += chars.charAt(Math.floor(Math.random() * chars.length));
        }
        return result;
    }
};

module.exports = utils;
