// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth"; 

const firebaseConfig = {
  apiKey: "AIzaSyBTBmFmkzjjg0wIBHlO2SIiJDVu8qeTMbA",
  authDomain: "dev-nav.firebaseapp.com",
  projectId: "dev-nav",
  storageBucket: "dev-nav.firebasestorage.app",
  messagingSenderId: "732683840793",
  appId: "1:732683840793:web:b411b85a1d861317c36988",
  measurementId: "G-V6KJCF0EXP"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);