// HOL 3: Functional component – CalculateScore
// Accepts Name, School, Total, Goal via props
// Calculates and displays the average score

import React from 'react';
import './Stylesheets/mystyle.css';

function CalculateScore({ name, school, total, goal }) {
  // Calculate average: (total / goal) * 100
  const average = goal > 0 ? ((total / goal) * 100).toFixed(2) : 0;
  const passed  = average >= 50;

  return (
    <div className="score-card">
      <h2 className="score-title">Student Score Report</h2>
      <table className="score-table">
        <tbody>
          <tr><th>Name</th>    <td>{name}</td></tr>
          <tr><th>School</th>  <td>{school}</td></tr>
          <tr><th>Total</th>   <td>{total}</td></tr>
          <tr><th>Goal</th>    <td>{goal}</td></tr>
          <tr>
            <th>Average</th>
            <td style={{ color: passed ? 'green' : 'red', fontWeight: 'bold' }}>
              {average}% — {passed ? 'PASS ✓' : 'FAIL ✗'}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}

export default CalculateScore;
