// Import required modules
const express = require('express');           // Web framework for building the API
const mongoose = require('mongoose');         // ODM library for MongoDB
const cors = require('cors');                 // Allows cross-origin requests (important for frontend-backend communication)
require('dotenv').config();                   // Loads environment variables from .env file

// Initialize the Express app
const app = express();

// Define the port from .env or default to 5000
const PORT = process.env.PORT || 5000;

// MIDDLEWARE SETUP

// Enable Cross-Origin Resource Sharing (e.g. allow Android app or browser to call the API)
app.use(cors());

// Enable parsing of JSON bodies in requests
app.use(express.json());

// ROUTE HANDLING

// All requests to /memes will be handled by the memes router
app.use('/memes', require('./routes/memes'));


// CONNECT TO MONGODB

// Connect to the MongoDB database using the connection string from .env
mongoose.connect(process.env.MONGO_URI, {
  useNewUrlParser: true,
  useUnifiedTopology: true
})
.then(() => {
  // Log success message when connected
  console.log(' Connected to MongoDB');

  // Start the server only after a successful DB connection
  app.listen(PORT, () =>
    console.log(` Server running on http://localhost:${PORT}`)
  );
})
.catch(err => {
  // Log an error message if DB connection fails
  console.error(' MongoDB error:', err);
});
