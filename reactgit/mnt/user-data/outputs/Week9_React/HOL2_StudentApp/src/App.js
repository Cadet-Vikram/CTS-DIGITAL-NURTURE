// HOL 2: Student Management Portal
// Three class components: Home, About, Contact - all rendered from App

import React from 'react';
import Home    from './components/Home';
import About   from './components/About';
import Contact from './components/Contact';

function App() {
  return (
    <div style={{ fontFamily: 'Arial, sans-serif', maxWidth: '800px', margin: '40px auto' }}>
      <h1 style={{ textAlign: 'center', color: '#333' }}>Student Management Portal</h1>
      <Home />
      <About />
      <Contact />
    </div>
  );
}

export default App;
