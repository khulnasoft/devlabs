import crypto from 'crypto';

/**
 * Database.js
 * Backend localstorage connector
 */

const ENCRYPTION_KEY = 'your-encryption-key'; // Replace with your actual encryption key
const IV_LENGTH = 16;

function encrypt(text) {
  let iv = crypto.randomBytes(IV_LENGTH);
  let cipher = crypto.createCipheriv('aes-256-cbc', crypto.scryptSync(ENCRYPTION_KEY, 'salt', 32), iv);
  let encrypted = cipher.update(text);
  encrypted = Buffer.concat([encrypted, cipher.final()]);
  return iv.toString('hex') + ':' + encrypted.toString('hex');
}

function decrypt(text) {
  let textParts = text.split(':');
  let iv = Buffer.from(textParts.shift(), 'hex');
  let encryptedText = Buffer.from(textParts.join(':'), 'hex');
  let decipher = crypto.createDecipheriv('aes-256-cbc', Buffer.from(ENCRYPTION_KEY), iv);
  let decrypted = decipher.update(encryptedText);
  decrypted = Buffer.concat([decrypted, decipher.final()]);
  return decrypted.toString();
}

class Database {
  constructor() {
    this.dbName = "weather-app";

    this.create = (key, value) => {
      if (key === undefined || value === undefined) {
        throw new Error("Database key and value must be declared");
      }
      const encryptedValue = encrypt(value);
      localStorage.setItem(key, encryptedValue);
    };

    this.delete = (key) => {
      if (key === undefined) {
        throw new Error("Database key and value must be declared");
      }
      localStorage.removeItem(key);
    };

    this.update = (key, value) => {
      this.create(key, value);
    };

    this.get = key => {
      const encryptedValue = localStorage.getItem(key);
      if (encryptedValue) {
        return decrypt(encryptedValue);
      }
      return null;
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