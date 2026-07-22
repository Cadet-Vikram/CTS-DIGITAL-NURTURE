// HOL 13: bloggerapp – Lists, Keys, and Conditional Rendering
// Three components: BookDetails, BlogDetails, CourseDetails
// Uses multiple ways of conditional rendering

import React, { Component } from 'react';

// ── Sample Data ───────────────────────────────────────────────────────
const books = [
  { id: 1, title: 'Clean Code',        author: 'Robert C. Martin', genre: 'Programming' },
  { id: 2, title: 'Effective Java',    author: 'Joshua Bloch',      genre: 'Programming' },
  { id: 3, title: 'The Pragmatic Programmer', author: 'Hunt & Thomas', genre: 'Career' },
];

const blogs = [
  { id: 1, title: 'Getting Started with React', date: '2024-01-15', views: 1200 },
  { id: 2, title: 'Spring Boot Best Practices',  date: '2024-02-20', views: 850  },
  { id: 3, title: 'Microservices Architecture',  date: '2024-03-10', views: 2300 },
];

const courses = [
  { id: 1, name: 'Java FSE',      duration: '6 months', enrolled: 120, active: true  },
  { id: 2, name: 'React Basics',  duration: '2 months', enrolled: 85,  active: true  },
  { id: 3, name: 'Spring Boot',   duration: '3 months', enrolled: 0,   active: false },
];

// ── Component 1: BookDetails ─────────────────────────────────────────
// Conditional rendering: if-else with element variable
function BookDetails() {
  const hasBooks = books.length > 0;

  // Way 1: Element variable
  let content;
  if (hasBooks) {
    content = (
      <ul>
        {books.map(book => (
          <li key={book.id} style={{ margin: '8px 0' }}>
            <strong>{book.title}</strong> by {book.author}
            <span style={{ marginLeft: '10px', backgroundColor: '#3498db', color: 'white', padding: '2px 8px', borderRadius: '12px', fontSize: '0.8em' }}>
              {book.genre}
            </span>
          </li>
        ))}
      </ul>
    );
  } else {
    content = <p>No books available.</p>;
  }

  return (
    <div style={{ backgroundColor: '#eaf4fb', padding: '20px', borderRadius: '10px', margin: '10px 0' }}>
      <h2>📚 Book Details</h2>
      <p style={{ color: '#666' }}>Conditional rendering: <em>Element Variable (if-else)</em></p>
      {content}
    </div>
  );
}

// ── Component 2: BlogDetails ─────────────────────────────────────────
// Conditional rendering: ternary operator and && short-circuit
function BlogDetails() {
  const showHighViews = true; // toggle this to filter

  return (
    <div style={{ backgroundColor: '#f0fff0', padding: '20px', borderRadius: '10px', margin: '10px 0' }}>
      <h2>📝 Blog Details</h2>
      <p style={{ color: '#666' }}>Conditional rendering: <em>Ternary operator + && short-circuit</em></p>

      {/* Ternary operator */}
      {blogs.length > 0
        ? blogs.map(blog => (
            <div key={blog.id} style={{ borderBottom: '1px solid #ccc', padding: '8px 0' }}>
              <strong>{blog.title}</strong>
              <span style={{ marginLeft: '10px', color: '#888', fontSize: '0.9em' }}>{blog.date}</span>
              {/* && short-circuit: show badge only for high-view blogs */}
              {showHighViews && blog.views > 1000 && (
                <span style={{ marginLeft: '10px', backgroundColor: '#e74c3c', color: 'white', padding: '2px 8px', borderRadius: '12px', fontSize: '0.8em' }}>
                  🔥 Trending
                </span>
              )}
              <span style={{ float: 'right', color: '#3498db' }}>👁 {blog.views}</span>
            </div>
          ))
        : <p>No blogs found.</p>
      }
    </div>
  );
}

// ── Component 3: CourseDetails ────────────────────────────────────────
// Conditional rendering: preventing component render with null return
// Lists with keys and map()
function CourseDetails() {
  return (
    <div style={{ backgroundColor: '#fff8e1', padding: '20px', borderRadius: '10px', margin: '10px 0' }}>
      <h2>🎓 Course Details</h2>
      <p style={{ color: '#666' }}>Conditional rendering: <em>Prevent render with null return + map()</em></p>
      {courses.map(course => (
        <CourseCard key={course.id} course={course} />
      ))}
    </div>
  );
}

// Prevents rendering if course has 0 enrolled
function CourseCard({ course }) {
  // Conditional rendering: return null to prevent component from rendering
  if (!course.active && course.enrolled === 0) {
    return (
      <div style={{ padding: '10px', backgroundColor: '#ffebee', borderRadius: '5px', margin: '5px 0' }}>
        ⛔ {course.name} — Not Available
      </div>
    );
  }

  return (
    <div style={{
      padding: '10px', backgroundColor: 'white', borderRadius: '5px',
      margin: '5px 0', border: `2px solid ${course.active ? '#27ae60' : '#bdc3c7'}`
    }}>
      <strong>{course.name}</strong>
      <span style={{ marginLeft: '10px', color: '#666' }}>Duration: {course.duration}</span>
      <span style={{ marginLeft: '10px', color: '#3498db' }}>Enrolled: {course.enrolled}</span>
      <span style={{ float: 'right', color: course.active ? '#27ae60' : '#e74c3c' }}>
        {course.active ? '● Active' : '● Inactive'}
      </span>
    </div>
  );
}

// ── Main App ──────────────────────────────────────────────────────────
class App extends Component {
  render() {
    return (
      <div style={{ fontFamily: 'Arial, sans-serif', maxWidth: '800px', margin: '40px auto' }}>
        <h1 style={{ textAlign: 'center', color: '#2c3e50' }}>📖 Blogger App</h1>
        <p style={{ textAlign: 'center', color: '#666' }}>Lists, Keys, and Multiple Conditional Rendering techniques</p>
        <BookDetails />
        <BlogDetails />
        <CourseDetails />
      </div>
    );
  }
}

export default App;
