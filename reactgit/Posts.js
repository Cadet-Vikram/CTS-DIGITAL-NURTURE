// HOL 4: Posts class component
// Demonstrates: componentDidMount(), componentDidCatch(), Fetch API
// Data source: https://jsonplaceholder.typicode.com/posts

import React, { Component } from 'react';

class Posts extends Component {

  constructor(props) {
    super(props);
    // Initialize state with an empty posts array
    this.state = {
      posts: [],
      loading: true,
      error: null
    };
  }

  /**
   * HOL 4: loadPosts() – fetches posts from JSONPlaceholder API.
   * Sets the component state with the fetched posts array.
   */
  loadPosts() {
    fetch('https://jsonplaceholder.typicode.com/posts')
      .then(response => response.json())
      .then(data => {
        // Limit to first 10 posts for display clarity
        this.setState({ posts: data.slice(0, 10), loading: false });
      })
      .catch(error => {
        this.setState({ error: error.message, loading: false });
      });
  }

  /**
   * HOL 4: componentDidMount() lifecycle hook.
   * Called immediately after the component is inserted into the DOM.
   * The ideal place to make API calls.
   */
  componentDidMount() {
    this.loadPosts();
  }

  /**
   * HOL 4: componentDidCatch() lifecycle hook.
   * Called when a descendant component throws an error.
   * Displays the error as an alert message.
   */
  componentDidCatch(error, info) {
    alert('Error caught in Posts component: ' + error.message);
    console.error('Component error:', error, info);
  }

  render() {
    const { posts, loading, error } = this.state;

    if (loading) return <p style={{ textAlign: 'center' }}>Loading posts...</p>;
    if (error)   return <p style={{ color: 'red' }}>Error: {error}</p>;

    return (
      <div style={{ maxWidth: '800px', margin: '40px auto', fontFamily: 'Arial, sans-serif' }}>
        <h1 style={{ textAlign: 'center', color: '#2c3e50' }}>Blog Posts</h1>
        <p style={{ textAlign: 'center', color: '#666' }}>
          Fetched from https://jsonplaceholder.typicode.com/posts
        </p>
        {posts.map(post => (
          <div key={post.id} style={{
            border: '1px solid #ddd', borderRadius: '8px',
            padding: '16px', margin: '12px 0',
            backgroundColor: '#fafafa'
          }}>
            {/* Display title as heading */}
            <h3 style={{ color: '#3498db', margin: '0 0 8px' }}>
              {post.id}. {post.title}
            </h3>
            {/* Display body as paragraph */}
            <p style={{ color: '#555', margin: 0 }}>{post.body}</p>
          </div>
        ))}
      </div>
    );
  }
}

export default Posts;
