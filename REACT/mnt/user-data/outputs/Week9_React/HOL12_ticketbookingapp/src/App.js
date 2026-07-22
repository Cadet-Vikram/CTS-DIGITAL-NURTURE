// HOL 12: ticketbookingapp – Conditional Rendering
// Guest user: sees flight browsing page only
// Logged-in user: can see and book tickets
// Login/Logout buttons toggle the view

import React, { Component } from 'react';

// ── Flight data ────────────────────────────────────────────────────────
const flights = [
  { id: 1, from: 'Chennai',   to: 'Delhi',     time: '06:00', price: 4500, seats: 12 },
  { id: 2, from: 'Mumbai',    to: 'Bangalore',  time: '09:30', price: 3200, seats: 5  },
  { id: 3, from: 'Hyderabad', to: 'Chennai',    time: '14:15', price: 2800, seats: 20 },
];

// ── Guest Page: only browse, no booking ───────────────────────────────
function GuestPage() {
  return (
    <div style={{ padding: '20px', backgroundColor: '#f8f9fa', borderRadius: '10px' }}>
      <h2 style={{ color: '#666' }}>✈ Available Flights (Guest View)</h2>
      <p style={{ color: '#e74c3c' }}>⚠ Please login to book tickets.</p>
      {flights.map(flight => (
        <div key={flight.id} style={{
          border: '1px solid #ddd', borderRadius: '8px', padding: '14px', margin: '10px 0',
          backgroundColor: 'white'
        }}>
          <strong>{flight.from} → {flight.to}</strong>
          &nbsp;| Departure: {flight.time}
          &nbsp;| Price: <span style={{ color: '#27ae60' }}>₹{flight.price}</span>
          &nbsp;| Seats: {flight.seats}
          <button disabled style={{ marginLeft: '10px', opacity: 0.4 }}>Book (Login Required)</button>
        </div>
      ))}
    </div>
  );
}

// ── User Page: browse and book tickets ────────────────────────────────
function UserPage({ username }) {
  return (
    <div style={{ padding: '20px', backgroundColor: '#e8f8f5', borderRadius: '10px' }}>
      <h2 style={{ color: '#27ae60' }}>✈ Welcome, {username}! Book Your Flight</h2>
      {flights.map(flight => (
        <div key={flight.id} style={{
          border: '1px solid #27ae60', borderRadius: '8px', padding: '14px', margin: '10px 0',
          backgroundColor: 'white'
        }}>
          <strong>{flight.from} → {flight.to}</strong>
          &nbsp;| Departure: {flight.time}
          &nbsp;| Price: <span style={{ color: '#27ae60' }}>₹{flight.price}</span>
          &nbsp;| Seats: {flight.seats}
          <button
            onClick={() => alert(`Ticket booked: ${flight.from} → ${flight.to} for ${username}`)}
            style={{
              marginLeft: '10px', backgroundColor: '#27ae60', color: 'white',
              border: 'none', padding: '6px 12px', borderRadius: '5px', cursor: 'pointer'
            }}>
            Book Now ✓
          </button>
        </div>
      ))}
    </div>
  );
}

// ── Main App with conditional rendering ───────────────────────────────
class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoggedIn: false,
      username: 'Abhirami'
    };
  }

  handleLogin  = () => this.setState({ isLoggedIn: true });
  handleLogout = () => this.setState({ isLoggedIn: false });

  render() {
    const { isLoggedIn, username } = this.state;

    // Element variable: holds the button based on login state
    const authButton = isLoggedIn
      ? <button onClick={this.handleLogout}
          style={{ backgroundColor: '#e74c3c', color: 'white', padding: '8px 20px', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>
          Logout
        </button>
      : <button onClick={this.handleLogin}
          style={{ backgroundColor: '#3498db', color: 'white', padding: '8px 20px', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>
          Login
        </button>;

    return (
      <div style={{ fontFamily: 'Arial, sans-serif', maxWidth: '800px', margin: '40px auto' }}>
        <h1 style={{ textAlign: 'center', color: '#2c3e50' }}>🎫 Ticket Booking App</h1>

        <div style={{ textAlign: 'center', margin: '20px' }}>
          <p>Status: <strong style={{ color: isLoggedIn ? '#27ae60' : '#e74c3c' }}>
            {isLoggedIn ? `Logged in as ${username}` : 'Guest'}
          </strong></p>
          {authButton}
        </div>

        {/* Conditional rendering: show different page based on login state */}
        {isLoggedIn
          ? <UserPage username={username} />
          : <GuestPage />
        }
      </div>
    );
  }
}

export default App;
