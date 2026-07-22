// HOL 11: eventexamplesapp – Event handling in React
// Demonstrates: multiple event handlers, synthetic events, this keyword,
//               argument passing, CurrencyConvertor component

import React, { Component } from 'react';

// ── Counter Component ─────────────────────────────────────────────────
class Counter extends Component {
  constructor(props) {
    super(props);
    this.state = { count: 0 };
  }

  // Handler 1: Increment the counter value
  increment = () => {
    this.setState(prev => ({ count: prev.count + 1 }));
  }

  // Handler 2: Say Hello with a static message (invoked alongside increment)
  sayHello = () => {
    alert('Hello! Counter has been incremented.');
  }

  // Handler 3: Multiple methods called by the Increment button
  handleIncrement = () => {
    this.increment();
    this.sayHello();
  }

  // Decrement handler
  decrement = () => {
    this.setState(prev => ({ count: prev.count - 1 }));
  }

  // Argument passing: "Say Welcome" with parameter
  sayWelcome = (message) => {
    alert(message);
  }

  // Synthetic event demonstration
  handlePress = (event) => {
    // event is the SyntheticEvent object
    alert('I was clicked! Event type: ' + event.type);
  }

  render() {
    return (
      <div style={{ padding: '20px', border: '2px solid #3498db', borderRadius: '10px', margin: '20px' }}>
        <h2>Counter</h2>
        <h3>Count: <span style={{ color: '#e74c3c' }}>{this.state.count}</span></h3>

        {/* Increment button – invokes multiple methods */}
        <button
          onClick={this.handleIncrement}
          style={{ backgroundColor: '#2ecc71', color: 'white', padding: '8px 16px', margin: '5px', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>
          Increment (+)
        </button>

        {/* Decrement button */}
        <button
          onClick={this.decrement}
          style={{ backgroundColor: '#e74c3c', color: 'white', padding: '8px 16px', margin: '5px', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>
          Decrement (-)
        </button>

        {/* Argument passing */}
        <button
          onClick={() => this.sayWelcome('Welcome to React Event Handling!')}
          style={{ backgroundColor: '#9b59b6', color: 'white', padding: '8px 16px', margin: '5px', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>
          Say Welcome
        </button>

        {/* Synthetic Event */}
        <button
          onClick={this.handlePress}
          style={{ backgroundColor: '#f39c12', color: 'white', padding: '8px 16px', margin: '5px', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>
          OnPress (Synthetic Event)
        </button>
      </div>
    );
  }
}

// ── CurrencyConvertor Component ───────────────────────────────────────
// Converts Indian Rupees to Euro
class CurrencyConvertor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      rupees: '',
      euros:  null
    };
  }

  handleChange = (event) => {
    this.setState({ rupees: event.target.value });
  }

  // handleSubmit: converts rupees to euros (1 EUR ≈ 90 INR)
  handleSubmit = (event) => {
    event.preventDefault();
    const conversionRate = 90;
    const euros = (parseFloat(this.state.rupees) / conversionRate).toFixed(4);
    this.setState({ euros });
  }

  render() {
    return (
      <div style={{ padding: '20px', border: '2px solid #e67e22', borderRadius: '10px', margin: '20px' }}>
        <h2>💱 Currency Convertor (INR → EUR)</h2>
        <form onSubmit={this.handleSubmit}>
          <label>Enter Amount in Rupees (₹): </label>
          <input
            type="number"
            value={this.state.rupees}
            onChange={this.handleChange}
            placeholder="e.g. 1000"
            style={{ padding: '6px', margin: '0 10px', borderRadius: '4px', border: '1px solid #ccc' }}
          />
          <button
            type="submit"
            style={{ backgroundColor: '#e67e22', color: 'white', padding: '6px 14px', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>
            Convert
          </button>
        </form>
        {this.state.euros !== null && (
          <p style={{ marginTop: '12px', color: '#27ae60', fontWeight: 'bold' }}>
            ₹{this.state.rupees} = €{this.state.euros}
          </p>
        )}
      </div>
    );
  }
}

// ── App ───────────────────────────────────────────────────────────────
function App() {
  return (
    <div style={{ fontFamily: 'Arial, sans-serif', maxWidth: '700px', margin: '40px auto' }}>
      <h1 style={{ textAlign: 'center', color: '#2c3e50' }}>Event Examples App</h1>
      <Counter />
      <CurrencyConvertor />
    </div>
  );
}

export default App;
