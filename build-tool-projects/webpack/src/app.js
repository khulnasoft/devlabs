// Main application entry point
const utils = require('./utils');
const DataService = require('./dataService');

class App {
    constructor() {
        this.dataService = new DataService();
        this.isLoading = false;
        this.initializeEventListeners();
        this.displayWelcomeMessage();
    }

    /**
     * Initializes event listeners for the application
     */
    initializeEventListeners() {
        // Email validation demo
        const emailInput = document.getElementById('email-input');
        const validateBtn = document.getElementById('validate-btn');
        const resultDiv = document.getElementById('validation-result');

        if (validateBtn) {
            validateBtn.addEventListener('click', () => {
                const email = emailInput?.value;
                if (email) {
                    const isValid = utils.isValidEmail(email);
                    resultDiv.textContent = `Email ${email} is ${isValid ? 'valid' : 'invalid'}`;
                    resultDiv.className = `alert ${isValid ? 'alert-success' : 'alert-danger'}`;
                }
            });
        }

        // Load users button
        const loadUsersBtn = document.getElementById('load-users-btn');
        const usersContainer = document.getElementById('users-container');

        if (loadUsersBtn) {
            loadUsersBtn.addEventListener('click', () => {
                this.loadAndDisplayUsers(usersContainer);
            });
        }

        // Generate ID button
        const generateIdBtn = document.getElementById('generate-id-btn');
        const idResult = document.getElementById('id-result');

        if (generateIdBtn) {
            generateIdBtn.addEventListener('click', () => {
                const id = utils.generateId(10);
                idResult.textContent = `Generated ID: ${id}`;
            });
        }
    }

    /**
     * Displays welcome message
     */
    displayWelcomeMessage() {
        console.log('🚀 Webpack Build Tools Application Started');
        console.log('Available modules:', Object.keys(require.cache));

        // Update UI if elements exist
        const welcomeElement = document.getElementById('welcome-message');
        if (welcomeElement) {
            welcomeElement.textContent = 'Welcome to Webpack Build Tools Demo Application!';
        }
    }

    /**
     * Loads and displays users from the API
     * @param {HTMLElement} container - Container element for users
     */
    async loadAndDisplayUsers(container) {
        if (this.isLoading) return;

        this.isLoading = true;
        const loadingElement = document.getElementById('loading');
        const errorElement = document.getElementById('error-message');

        try {
            if (loadingElement) loadingElement.style.display = 'block';
            if (errorElement) errorElement.style.display = 'none';

            const users = await this.dataService.fetchUsers();

            if (container) {
                container.innerHTML = users.map(user => `
                    <div class="user-card">
                        <h3>${user.name}</h3>
                        <p><strong>Email:</strong> ${user.email}</p>
                        <p><strong>Phone:</strong> ${user.phone}</p>
                        <p><strong>Website:</strong> ${user.website}</p>
                    </div>
                `).join('');
            }

            console.log(`Loaded ${users.length} users successfully`);
        } catch (error) {
            console.error('Failed to load users:', error);
            if (errorElement) {
                errorElement.textContent = 'Failed to load users. Please try again.';
                errorElement.style.display = 'block';
            }
        } finally {
            if (loadingElement) loadingElement.style.display = 'none';
            this.isLoading = false;
        }
    }

    /**
     * Formats and displays currency
     * @param {number} amount - Amount to format
     * @param {string} currency - Currency code
     */
    displayFormattedCurrency(amount, currency = 'USD') {
        const formatted = utils.formatCurrency(amount, currency);
        console.log(`Formatted ${amount} ${currency}: ${formatted}`);

        const currencyElement = document.getElementById('currency-display');
        if (currencyElement) {
            currencyElement.textContent = formatted;
        }
    }
}

// Initialize the application when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.app = new App();

    // Demo currency formatting
    window.app.displayFormattedCurrency(1234.56, 'EUR');
});

// Export for testing
module.exports = App;
