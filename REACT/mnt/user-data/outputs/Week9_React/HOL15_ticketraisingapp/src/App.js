// HOL 15: ticketraisingapp – React Forms
// ComplaintRegister: controlled form with textbox (name) + textarea (complaint)
// handleSubmit generates a reference number on form submission

import React, { Component } from 'react';

class ComplaintRegister extends Component {
  constructor(props) {
    super(props);
    this.state = {
      employeeName: '',
      complaint:    '',
      submitted:    false,
      refNumber:    null
    };
  }

  // Controlled component: sync state with input on every change
  handleChange = (event) => {
    this.setState({ [event.target.name]: event.target.value });
  }

  // handleSubmit: generates a reference number and shows in alert
  handleSubmit = (event) => {
    event.preventDefault();
    // Generate a unique reference number
    const refNumber = 'REF-' + Date.now();
    alert(`Complaint Registered!\nEmployee: ${this.state.employeeName}\nReference Number: ${refNumber}\nUse this number for follow-ups.`);
    this.setState({ submitted: true, refNumber });
  }

  render() {
    const { employeeName, complaint, submitted, refNumber } = this.state;

    return (
      <div style={{
        maxWidth: '550px', margin: '40px auto', padding: '30px',
        boxShadow: '0 4px 12px rgba(0,0,0,0.15)', borderRadius: '10px',
        fontFamily: 'Arial, sans-serif', backgroundColor: '#fff'
      }}>
        <h2 style={{ textAlign: 'center', color: '#2c3e50' }}>🎫 Complaint Register</h2>
        <p style={{ textAlign: 'center', color: '#666' }}>Raise a complaint and get it resolved</p>

        <form onSubmit={this.handleSubmit}>
          <div style={{ marginBottom: '16px' }}>
            <label style={{ display: 'block', marginBottom: '6px', fontWeight: 'bold' }}>
              Employee Name *
            </label>
            <input
              type="text"
              name="employeeName"
              value={employeeName}
              onChange={this.handleChange}
              placeholder="Enter your employee name"
              required
              style={{
                width: '100%', padding: '10px', borderRadius: '5px',
                border: '1px solid #ccc', boxSizing: 'border-box'
              }}
            />
          </div>

          <div style={{ marginBottom: '20px' }}>
            <label style={{ display: 'block', marginBottom: '6px', fontWeight: 'bold' }}>
              Complaint Description *
            </label>
            <textarea
              name="complaint"
              value={complaint}
              onChange={this.handleChange}
              placeholder="Describe your complaint in detail..."
              required
              rows={5}
              style={{
                width: '100%', padding: '10px', borderRadius: '5px',
                border: '1px solid #ccc', boxSizing: 'border-box', resize: 'vertical'
              }}
            />
          </div>

          <button
            type="submit"
            style={{
              width: '100%', padding: '12px', backgroundColor: '#3498db',
              color: 'white', border: 'none', borderRadius: '5px',
              fontSize: '1rem', cursor: 'pointer'
            }}>
            Submit Complaint
          </button>
        </form>

        {/* Show reference number after submission */}
        {submitted && (
          <div style={{
            marginTop: '20px', padding: '14px', backgroundColor: '#e8f8f5',
            borderRadius: '8px', borderLeft: '4px solid #27ae60'
          }}>
            <p style={{ margin: 0, color: '#27ae60', fontWeight: 'bold' }}>
              ✅ Complaint submitted successfully!
            </p>
            <p style={{ margin: '6px 0 0', color: '#333' }}>
              Reference Number: <strong>{refNumber}</strong>
            </p>
          </div>
        )}
      </div>
    );
  }
}

function App() {
  return <ComplaintRegister />;
}

export default App;
