// HOL 16: mailregisterapp – React Form Validation
// Validates: name (≥5 chars), email (has @ and .), password (≥8 chars)
// Validation triggered on both onChange (handleChange) and onSubmit (handleSubmit)

import React, { Component } from 'react';

class Register extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name:     '',
      email:    '',
      password: '',
      errors:   { name: '', email: '', password: '' },
      submitted: false
    };
  }

  // Validate a single field and return error message (empty = valid)
  validate(name, value) {
    switch (name) {
      case 'name':
        return value.length < 5 ? 'Name should have at least 5 characters' : '';

      case 'email':
        return (!value.includes('@') || !value.includes('.'))
          ? 'Email should contain @ and .'
          : '';

      case 'password':
        return value.length < 8 ? 'Password should have at least 8 characters' : '';

      default:
        return '';
    }
  }

  // handleChange: validate each field as user types
  handleChange = (event) => {
    const { name, value } = event.target;
    const errorMsg = this.validate(name, value);
    this.setState({
      [name]: value,
      errors: { ...this.state.errors, [name]: errorMsg }
    });
  }

  // handleSubmit: final validation before accepting the form
  handleSubmit = (event) => {
    event.preventDefault();
    const { name, email, password } = this.state;

    // Validate all fields on submit
    const errors = {
      name:     this.validate('name',     name),
      email:    this.validate('email',    email),
      password: this.validate('password', password)
    };

    // If no errors, accept the form
    const hasErrors = Object.values(errors).some(e => e !== '');
    if (!hasErrors) {
      this.setState({ submitted: true, errors });
    } else {
      this.setState({ errors });
    }
  }

  render() {
    const { name, email, password, errors, submitted } = this.state;

    const inputStyle = (field) => ({
      width: '100%', padding: '10px', borderRadius: '5px', boxSizing: 'border-box',
      border: `2px solid ${errors[field] ? '#e74c3c' : '#ccc'}`,
      marginBottom: '4px', fontSize: '1rem'
    });

    const errorStyle = { color: '#e74c3c', fontSize: '0.85rem', marginBottom: '12px' };

    if (submitted) {
      return (
        <div style={{ maxWidth: '450px', margin: '80px auto', textAlign: 'center', fontFamily: 'Arial' }}>
          <div style={{ backgroundColor: '#e8f8f5', padding: '40px', borderRadius: '10px' }}>
            <h2 style={{ color: '#27ae60' }}>✅ Registration Successful!</h2>
            <p>Welcome, <strong>{name}</strong>!</p>
            <p>Account created for: <strong>{email}</strong></p>
          </div>
        </div>
      );
    }

    return (
      <div style={{
        maxWidth: '450px', margin: '40px auto', padding: '30px',
        boxShadow: '0 4px 12px rgba(0,0,0,0.15)', borderRadius: '10px',
        fontFamily: 'Arial, sans-serif', backgroundColor: '#fff'
      }}>
        <h2 style={{ textAlign: 'center', color: '#2c3e50' }}>📧 Mail Register App</h2>
        <p style={{ textAlign: 'center', color: '#666' }}>Create your account</p>

        <form onSubmit={this.handleSubmit}>

          {/* Name Field */}
          <label style={{ fontWeight: 'bold' }}>Name *</label>
          <input
            type="text"
            name="name"
            value={name}
            onChange={this.handleChange}
            placeholder="At least 5 characters"
            style={inputStyle('name')}
          />
          {errors.name && <p style={errorStyle}>⚠ {errors.name}</p>}

          {/* Email Field */}
          <label style={{ fontWeight: 'bold' }}>Email *</label>
          <input
            type="text"
            name="email"
            value={email}
            onChange={this.handleChange}
            placeholder="must contain @ and ."
            style={inputStyle('email')}
          />
          {errors.email && <p style={errorStyle}>⚠ {errors.email}</p>}

          {/* Password Field */}
          <label style={{ fontWeight: 'bold' }}>Password *</label>
          <input
            type="password"
            name="password"
            value={password}
            onChange={this.handleChange}
            placeholder="At least 8 characters"
            style={inputStyle('password')}
          />
          {errors.password && <p style={errorStyle}>⚠ {errors.password}</p>}

          <button
            type="submit"
            style={{
              width: '100%', padding: '12px', backgroundColor: '#2ecc71',
              color: 'white', border: 'none', borderRadius: '5px',
              fontSize: '1rem', cursor: 'pointer', marginTop: '8px'
            }}>
            Register
          </button>
        </form>
      </div>
    );
  }
}

function App() {
  return <Register />;
}

export default App;
