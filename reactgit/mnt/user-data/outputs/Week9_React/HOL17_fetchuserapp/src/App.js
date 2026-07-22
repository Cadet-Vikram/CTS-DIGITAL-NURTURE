// HOL 17: fetchuserapp – Calling REST API from React
// Fetches user details from https://api.randomuser.me/
// Displays: title, firstname, and user image
// Uses componentDidMount() for async fetch call

import React, { Component } from 'react';

class Getuser extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user:    null,
      loading: true,
      error:   null
    };
  }

  // HOL 17: componentDidMount() – invokes the URL using fetch method
  componentDidMount() {
    fetch('https://api.randomuser.me/')
      .then(response => response.json())
      .then(data => {
        const user = data.results[0];
        this.setState({ user, loading: false });
      })
      .catch(error => {
        this.setState({ error: error.message, loading: false });
      });
  }

  render() {
    const { user, loading, error } = this.state;

    if (loading) {
      return (
        <div style={{ textAlign: 'center', marginTop: '80px', fontFamily: 'Arial' }}>
          <p>⏳ Fetching user from randomuser.me...</p>
        </div>
      );
    }

    if (error) {
      return (
        <div style={{ textAlign: 'center', marginTop: '80px', color: 'red', fontFamily: 'Arial' }}>
          <p>❌ Error: {error}</p>
        </div>
      );
    }

    // HOL 17: Display title, firstname, and image
    const { title, first, last } = user.name;
    const image = user.picture.large;
    const email = user.email;
    const country = user.location.country;

    return (
      <div style={{
        maxWidth: '400px', margin: '60px auto', padding: '30px',
        boxShadow: '0 4px 16px rgba(0,0,0,0.15)', borderRadius: '12px',
        fontFamily: 'Arial, sans-serif', textAlign: 'center',
        backgroundColor: '#fff'
      }}>
        <h2 style={{ color: '#2c3e50', marginBottom: '20px' }}>👤 Random User</h2>

        {/* User Image */}
        <img
          src={image}
          alt={`${first} ${last}`}
          style={{ width: '120px', height: '120px', borderRadius: '50%', border: '4px solid #3498db' }}
        />

        {/* Title and First Name – as per HOL 17 requirement */}
        <h3 style={{ marginTop: '16px', color: '#2c3e50' }}>
          {title}. {first} {last}
        </h3>

        <p style={{ color: '#666', margin: '6px 0' }}>📧 {email}</p>
        <p style={{ color: '#666', margin: '6px 0' }}>🌍 {country}</p>

        <button
          onClick={() => window.location.reload()}
          style={{
            marginTop: '20px', padding: '10px 24px',
            backgroundColor: '#3498db', color: 'white',
            border: 'none', borderRadius: '6px',
            cursor: 'pointer', fontSize: '0.95rem'
          }}>
          🔄 Load Another User
        </button>
      </div>
    );
  }
}

function App() {
  return (
    <div>
      <Getuser />
    </div>
  );
}

export default App;
