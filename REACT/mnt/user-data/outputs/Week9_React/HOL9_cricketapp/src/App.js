// HOL 9: cricketapp – ES6 Features
// ListofPlayers: uses map(), arrow functions, filter()
// IndianPlayers: uses destructuring, spread operator

import React from 'react';

// ── ES6 Array Data ──────────────────────────────────────────────────
const players = [
  { id: 1,  name: 'Virat Kohli',    score: 82 },
  { id: 2,  name: 'Rohit Sharma',   score: 61 },
  { id: 3,  name: 'KL Rahul',       score: 45 },
  { id: 4,  name: 'Shubman Gill',   score: 90 },
  { id: 5,  name: 'Suryakumar',     score: 55 },
  { id: 6,  name: 'Hardik Pandya',  score: 38 },
  { id: 7,  name: 'Jadeja',         score: 72 },
  { id: 8,  name: 'Ashwin',         score: 30 },
  { id: 9,  name: 'Bumrah',         score: 15 },
  { id: 10, name: 'Siraj',          score: 10 },
  { id: 11, name: 'Shami',          score: 22 },
];

const t20Players     = ['Kohli', 'Rohit', 'Suryakumar', 'Hardik', 'Bumrah'];
const ranjiPlayers   = ['Gill', 'Rahul', 'Jadeja', 'Ashwin', 'Shami'];

// ── Component 1: ListofPlayers ──────────────────────────────────────
// Demonstrates: map(), filter(), arrow functions
function ListofPlayers() {
  // ES6 arrow function + filter: players with score < 70
  const lowScorers = players.filter(p => p.score < 70);

  return (
    <div style={{ padding: '20px', backgroundColor: '#f0f8ff', borderRadius: '8px', margin: '10px' }}>
      <h2>🏏 All Players (using ES6 map)</h2>
      <ul>
        {/* ES6 map() with arrow function */}
        {players.map(player => (
          <li key={player.id}>
            {player.name} — Score: {player.score}
          </li>
        ))}
      </ul>

      <h3 style={{ color: 'orange' }}>Players with score below 70 (using filter + arrow function)</h3>
      <ul>
        {lowScorers.map(player => (
          <li key={player.id} style={{ color: 'red' }}>
            {player.name} — {player.score}
          </li>
        ))}
      </ul>
    </div>
  );
}

// ── Component 2: IndianPlayers ──────────────────────────────────────
// Demonstrates: destructuring, spread operator
function IndianPlayers() {
  // ES6 Array Destructuring: split players into odd/even by index
  const oddTeam  = players.filter((_, index) => index % 2 !== 0);
  const evenTeam = players.filter((_, index) => index % 2 === 0);

  // ES6 Spread operator: merge T20 and Ranji arrays
  const allPlayers = [...t20Players, ...ranjiPlayers];

  // ES6 Object Destructuring (example)
  const { name: captainName, score: captainScore } = players[0];

  return (
    <div style={{ padding: '20px', backgroundColor: '#fff0f5', borderRadius: '8px', margin: '10px' }}>
      <h2>🇮🇳 Indian Players – ES6 Destructuring &amp; Spread</h2>

      <h3>Captain (Object Destructuring)</h3>
      <p><strong>{captainName}</strong> — Score: {captainScore}</p>

      <h3>Odd Team Players</h3>
      <ul>{oddTeam.map(p => <li key={p.id}>{p.name}</li>)}</ul>

      <h3>Even Team Players</h3>
      <ul>{evenTeam.map(p => <li key={p.id}>{p.name}</li>)}</ul>

      <h3>Merged Squad (T20 + Ranji using Spread)</h3>
      <p>[{allPlayers.join(', ')}]</p>
    </div>
  );
}

// ── App: conditional rendering with flag variable ──────────────────
function App() {
  // Change flag to false to see IndianPlayers component
  const flag = true;

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', maxWidth: '800px', margin: '40px auto' }}>
      <h1 style={{ textAlign: 'center', color: '#2c3e50' }}>🏏 Cricket App – ES6 Features</h1>
      <p style={{ textAlign: 'center' }}>
        flag = <strong>{flag.toString()}</strong>
        {' '}(change in App.js to toggle component)
      </p>

      {/* Simple if-else with flag variable */}
      {flag ? <ListofPlayers /> : <IndianPlayers />}
    </div>
  );
}

export default App;
