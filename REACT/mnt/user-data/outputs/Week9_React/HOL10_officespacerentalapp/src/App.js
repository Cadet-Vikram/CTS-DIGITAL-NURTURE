// HOL 10: officespacerentalapp – JSX elements, attributes, inline CSS
// Demonstrates: JSX elements, objects, lists, conditional inline CSS

import React from 'react';

// ── Data Object ─────────────────────────────────────────────────────
const office = {
  name:    'Tech Hub',
  rent:    75000,
  address: 'Sector 5, Chennai, Tamil Nadu'
};

// ── List of Offices ──────────────────────────────────────────────────
const officeList = [
  { id: 1, name: 'Tech Hub',          rent: 75000, address: 'Chennai' },
  { id: 2, name: 'Startup Nest',       rent: 45000, address: 'Coimbatore' },
  { id: 3, name: 'Innovation Center',  rent: 65000, address: 'Bangalore' },
  { id: 4, name: 'Digital Workspace',  rent: 30000, address: 'Pune' },
  { id: 5, name: 'Enterprise Tower',   rent: 90000, address: 'Mumbai' },
];

// ── Inline CSS Styles ────────────────────────────────────────────────
const styles = {
  container:  { fontFamily: 'Arial, sans-serif', maxWidth: '900px', margin: '40px auto', padding: '20px' },
  heading:    { textAlign: 'center', color: '#2c3e50', fontSize: '2rem', marginBottom: '20px' },
  card:       {
    border: '1px solid #ddd', borderRadius: '10px', padding: '16px',
    margin: '12px 0', display: 'flex', justifyContent: 'space-between',
    alignItems: 'center', boxShadow: '0 2px 6px rgba(0,0,0,0.1)',
    backgroundColor: '#fafafa'
  },
  label:      { fontWeight: 'bold', color: '#555' },
  featured:   { backgroundColor: '#e8f4fd', padding: '20px', borderRadius: '10px', marginBottom: '30px' }
};

function App() {
  return (
    <div style={styles.container}>

      {/* JSX Heading element */}
      <h1 style={styles.heading}>🏢 Office Space Rental App</h1>

      {/* JSX Attribute – image */}
      <div style={{ textAlign: 'center', marginBottom: '20px' }}>
        <img
          src="https://images.unsplash.com/photo-1497366216548-37526070297c?w=400"
          alt="Office Space"
          style={{ width: '400px', borderRadius: '10px', boxShadow: '0 4px 12px rgba(0,0,0,0.2)' }}
        />
      </div>

      {/* Featured Office – JSX Object display */}
      <div style={styles.featured}>
        <h2>Featured Office</h2>
        <p><span style={styles.label}>Name:</span>    {office.name}</p>
        <p><span style={styles.label}>Address:</span> {office.address}</p>
        <p>
          <span style={styles.label}>Rent: </span>
          {/* Conditional inline CSS: red if < 60000, green if >= 60000 */}
          <span style={{ color: office.rent < 60000 ? 'red' : 'green', fontWeight: 'bold' }}>
            ₹{office.rent.toLocaleString()}
          </span>
        </p>
      </div>

      {/* List of offices using map() – JSX Loop */}
      <h2>All Available Offices</h2>
      {officeList.map(office => (
        <div key={office.id} style={styles.card}>
          <div>
            <h3 style={{ margin: '0 0 6px', color: '#2c3e50' }}>{office.name}</h3>
            <p style={{ margin: 0, color: '#666' }}>📍 {office.address}</p>
          </div>
          <div style={{ textAlign: 'right' }}>
            {/* HOL 10: Conditional color – red if rent < 60000, green if >= 60000 */}
            <span style={{
              color: office.rent < 60000 ? 'red' : 'green',
              fontSize: '1.2rem', fontWeight: 'bold'
            }}>
              ₹{office.rent.toLocaleString()}/mo
            </span>
            <p style={{ margin: '4px 0 0', fontSize: '0.8rem', color: '#999' }}>
              {office.rent < 60000 ? '🔴 Budget' : '🟢 Premium'}
            </p>
          </div>
        </div>
      ))}
    </div>
  );
}

export default App;
