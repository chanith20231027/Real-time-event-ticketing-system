import { useState, useEffect } from 'react'
import './App.css'
import axios from 'axios';
import { configureSystem, startSystem, fetchLogs, fetchStatus } from './services/api';

function App() {
  const [config, setConfig] = useState({
    totalTickets: 100,
    ticketReleaseRate: 5,
    customerRetrieveRate: 3,
    maxTicketCapacity: 50,
    ticketsPerVendor: 20,
    ticketsPerCustomer: 10
  });

  const [system, setSystem] = useState({
    vendors: 3,
    customers: 5
  });

  const [logs, setLogs] = useState(null);
  const [availableTickets, setAvailableTickets] = useState(0);
  const [isConfiguring, setIsConfiguring] = useState(false);
  const [error, setError] = useState(null);

  const handleConfigSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    try {
      await configureSystem(config);
      setIsConfiguring(false);
    } catch (err) {
      setError(err.message);
    }
  };

  const startSystemHandler = async () => {
    setError(null);
    try {
      await startSystem(system);
    } catch (err) {
      setError(err.message);
    }
  };

  const fetchLogsData = async () => {
    try {
      const data = await fetchLogs();
      setLogs(data);
    } catch (err) {
      console.error('Error fetching logs:', err.message);
    }
  };

  const fetchStatusData = async () => {
    try {
      const data = await fetchStatus();
      setAvailableTickets(data);
    } catch (err) {
      console.error('Error fetching status:', err.message);
    }
  };

  useEffect(() => {
    const interval = setInterval(() => {
      fetchLogsData();
      fetchStatusData();
    }, 1000);
    return () => clearInterval(interval);
  }, []);

  return (
    <div className="container">
      <h1>Ticket Platform Dashboard</h1>
      
      {error && <div className="error">{error}</div>}
      
      <div className="card">
        <h2>System Configuration</h2>
        {isConfiguring ? (
          <form onSubmit={handleConfigSubmit}>
            <div className="form-group">
              <label>Total Tickets:</label>
              <input
                type="number"
                value={config.totalTickets}
                onChange={(e) => setConfig({...config, totalTickets: parseInt(e.target.value)})}
              />
            </div>
            <div className="form-group">
              <label>Ticket Release Rate:</label>
              <input
                type="number"
                value={config.ticketReleaseRate}
                onChange={(e) => setConfig({...config, ticketReleaseRate: parseInt(e.target.value)})}
              />
            </div>
            <div className="form-group">
              <label>Customer Retrieve Rate:</label>
              <input
                type="number"
                value={config.customerRetrieveRate}
                onChange={(e) => setConfig({...config, customerRetrieveRate: parseInt(e.target.value)})}
              />
            </div>
            <div className="form-group">
              <label>Max Ticket Capacity:</label>
              <input
                type="number"
                value={config.maxTicketCapacity}
                onChange={(e) => setConfig({...config, maxTicketCapacity: parseInt(e.target.value)})}
              />
            </div>
            <div className="form-group">
              <label>Tickets Per Vendor:</label>
              <input
                type="number"
                value={config.ticketsPerVendor}
                onChange={(e) => setConfig({...config, ticketsPerVendor: parseInt(e.target.value)})}
              />
            </div>
            <div className="form-group">
              <label>Tickets Per Customer:</label>
              <input
                type="number"
                value={config.ticketsPerCustomer}
                onChange={(e) => setConfig({...config, ticketsPerCustomer: parseInt(e.target.value)})}
              />
            </div>
            <div className="button-group">
              <button type="submit">Save Configuration</button>
              <button type="button" onClick={() => setIsConfiguring(false)}>Cancel</button>
            </div>
          </form>
        ) : (
          <div>
            <button onClick={() => setIsConfiguring(true)}>Configure System</button>
          </div>
        )}
      </div>

      <div className="card">
        <h2>Start System</h2>
        <div className="form-group">
          <label>Number of Vendors:</label>
          <input
            type="number"
            value={system.vendors}
            onChange={(e) => setSystem({...system, vendors: parseInt(e.target.value)})}
          />
        </div>
        <div className="form-group">
          <label>Number of Customers:</label>
          <input
            type="number"
            value={system.customers}
            onChange={(e) => setSystem({...system, customers: parseInt(e.target.value)})}
          />
        </div>
        <button type="button" onClick={startSystemHandler}>Start System</button>
      </div>

      <div className="card">
        <h2>System Status</h2>
        <div className="status">
          <p>Available Tickets: {availableTickets}</p>
          {logs && (
            <>
              <p>Total Tickets Released: {logs.totalTicketsReleased}</p>
              <p>Total Tickets Purchased: {logs.totalTicketsPurchased}</p>
              
              <h3>Vendor Statistics</h3>
              <div className="stats">
                {logs.vendorLogs.map((vendor, index) => (
                  <div key={`vendor-${index}`} className="stat-item">
                    <p>Vendor {vendor.vendorId}: {vendor.ticketsReleased} tickets</p>
                  </div>
                ))}
              </div>

              <h3>Customer Statistics</h3>
              <div className="stats">
                {logs.customerLogs.map((customer, index) => (
                  <div key={`customer-${index}`} className="stat-item">
                    <p>Customer {customer.customerId}: {customer.ticketsPurchased} tickets</p>
                  </div>
                ))}
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  )
}

export default App
