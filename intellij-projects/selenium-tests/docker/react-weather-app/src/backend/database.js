/**
 * Database.js
 * Backend localstorage connector
 */

class Database {
  constructor() {
    this.dbName = "weather-app";

    // Static secret. In production, derive this securely or prompt the user for a passphrase.
    this._secret = "some-very-strong-hardcoded-password";

    // Returns a CryptoKey derived from secret
    this._getKey = async () => {
      const enc = new TextEncoder();
      const keyMaterial = await window.crypto.subtle.importKey(
        "raw",
        enc.encode(this._secret),
        "PBKDF2",
        false,
        ["deriveKey"]
      );
      return window.crypto.subtle.deriveKey(
        {
          name: "PBKDF2",
          salt: enc.encode("weather-app-salt"), // fixed salt, use per-user/app salt in prod
          iterations: 100000,
          hash: "SHA-256"
        },
        keyMaterial,
        { name: "AES-GCM", length: 256 },
        false,
        ["encrypt", "decrypt"]
      );
    };

    // Encrypts string and returns base64 encoded ciphertext (with IV prefix)
    this._encrypt = async (text) => {
      const key = await this._getKey();
      const enc = new TextEncoder();
      const iv = window.crypto.getRandomValues(new Uint8Array(12));
      const ciphertext = await window.crypto.subtle.encrypt(
        {
          name: "AES-GCM",
          iv: iv
        },
        key,
        enc.encode(text)
      );
      // Store iv+ciphertext together, separated by .
      return (
        btoa(String.fromCharCode.apply(null, iv)) +
        "." +
        btoa(String.fromCharCode.apply(null, new Uint8Array(ciphertext)))
      );
    };

    // Decrypts base64 encoded ciphertext (with IV prefix)
    this._decrypt = async (payload) => {
      if (!payload || typeof payload !== "string" || payload.indexOf(".") === -1) {
        return null;
      }
      const [ivB64, ctB64] = payload.split(".");
      const iv = new Uint8Array(
        atob(ivB64).split("").map((c) => c.charCodeAt(0))
      );
      const ciphertext = new Uint8Array(
        atob(ctB64).split("").map((c) => c.charCodeAt(0))
      );
      const key = await this._getKey();
      try {
        const plaintext = await window.crypto.subtle.decrypt(
          {
            name: "AES-GCM",
            iv: iv
          },
          key,
          ciphertext
        );
        return new TextDecoder().decode(plaintext);
      } catch (e) {
        return null;
      }
    };

    // Async version: store encrypted value
    this.createEncrypted = async (key, value) => {
      if (key === undefined || value === undefined) {
        throw new Error("Database key and value must be declared");
      }
      const encrypted = await this._encrypt(value.toString());
      localStorage.setItem(key, encrypted);
    };

    // Async version: get decrypted value
    this.getDecrypted = async (key) => {
      const data = localStorage.getItem(key);
      if (!data) return null;
      return await this._decrypt(data);
    };

    // Async version: store encrypted value
    this.create = async (key, value) => {
      if (key === undefined || value === undefined) {
        throw new Error("Database key and value must be declared");
      }
      const encrypted = await this._encrypt(value.toString());
      localStorage.setItem(key, encrypted);
    };

    this.delete = (key) => {
      if (key === undefined) {
        throw new Error("Database key must be declared");
      }
      localStorage.removeItem(key);
    };

    this.updateEncrypted = async (key, value) => {
      await this.createEncrypted(key, value);
    };

    this.update = async (key, value) => {
      await this.create(key, value);
    };

    this.get = async (key) => {
      const data = localStorage.getItem(key);
      if (!data) return null;
      return await this._decrypt(data);
    };

    this.countItems = () => {
      return localStorage.length;
    };

    this.destroy = () => {
      localStorage.clear();
    };
  }
}


export default Database;
