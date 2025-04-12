import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/api/tickets';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded',
  },
});

export const configureSystem = async (config) => {
  const params = new URLSearchParams();
  Object.keys(config).forEach(key => {
    params.append(key, config[key]);
  });
  return api.post('/configure', params);
};

export const startSystem = async (system) => {
  const params = new URLSearchParams();
  Object.keys(system).forEach(key => {
    params.append(key, system[key]);
  });
  return api.post('/start', params);
};

export const fetchLogs = async () => {
  return api.get('/logs');
};

export const fetchStatus = async () => {
  return api.get('/status');
};

// Add interceptor for error handling
api.interceptors.response.use(
  (response) => response.data,
  (error) => {
    console.error('API Error:', error.response?.data || error.message);
    if (error.response) {
      // Server responded with error
      throw new Error(error.response.data || 'Server error occurred');
    } else if (error.request) {
      // Request made but no response
      throw new Error('No response from server. Please check if the backend server is running on port 8081');
    } else {
      // Request setup error
      throw new Error('Error setting up request');
    }
  }
);

export default api;
