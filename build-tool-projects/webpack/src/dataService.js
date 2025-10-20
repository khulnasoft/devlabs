// Data service for API interactions
const API_BASE_URL = 'https://jsonplaceholder.typicode.com';

class DataService {
    constructor() {
        this.cache = new Map();
        this.cacheTimeout = 5 * 60 * 1000; // 5 minutes
    }

    /**
     * Fetches users from the API
     * @returns {Promise<Array>} Array of users
     */
    async fetchUsers() {
        const cacheKey = 'users';
        const cached = this.getCachedData(cacheKey);

        if (cached) {
            console.log('Returning cached users data');
            return cached;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/users`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const users = await response.json();
            this.setCachedData(cacheKey, users);
            return users;
        } catch (error) {
            console.error('Error fetching users:', error);
            throw error;
        }
    }

    /**
     * Fetches posts from the API
     * @returns {Promise<Array>} Array of posts
     */
    async fetchPosts() {
        const cacheKey = 'posts';
        const cached = this.getCachedData(cacheKey);

        if (cached) {
            console.log('Returning cached posts data');
            return cached;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/posts`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const posts = await response.json();
            this.setCachedData(cacheKey, posts);
            return posts;
        } catch (error) {
            console.error('Error fetching posts:', error);
            throw error;
        }
    }

    /**
     * Fetches a specific user by ID
     * @param {number} userId - The user ID
     * @returns {Promise<Object>} User object
     */
    async fetchUserById(userId) {
        const cacheKey = `user_${userId}`;
        const cached = this.getCachedData(cacheKey);

        if (cached) {
            console.log(`Returning cached user data for ID: ${userId}`);
            return cached;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/users/${userId}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const user = await response.json();
            this.setCachedData(cacheKey, user);
            return user;
        } catch (error) {
            console.error(`Error fetching user ${userId}:`, error);
            throw error;
        }
    }

    /**
     * Gets cached data if not expired
     * @param {string} key - Cache key
     * @returns {any|null} Cached data or null if expired/not found
     */
    getCachedData(key) {
        const cached = this.cache.get(key);
        if (!cached) return null;

        const { data, timestamp } = cached;
        if (Date.now() - timestamp > this.cacheTimeout) {
            this.cache.delete(key);
            return null;
        }

        return data;
    }

    /**
     * Sets data in cache
     * @param {string} key - Cache key
     * @param {any} data - Data to cache
     */
    setCachedData(key, data) {
        this.cache.set(key, {
            data,
            timestamp: Date.now()
        });
    }

    /**
     * Clears all cached data
     */
    clearCache() {
        this.cache.clear();
        console.log('Cache cleared');
    }
}

module.exports = DataService;
