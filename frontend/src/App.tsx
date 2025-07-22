import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import { Login } from "./pages/CommonPage/Login";
import { Navbar } from "./pages/CommonPage/Navbar";
import { HomePage } from "./pages/HomePage/HomePage";
import { AdminRoute } from "./routes/AdminRoute";
import { AdminPage } from "./pages/AdminPage/AdminPage";

function App() {
  return (
    <>
      <div className="min-h-screen bg-gray-900">
        <BrowserRouter>
          <Navbar />
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<Login />} />
            <Route
              path="/admin"
              element={
                <AdminRoute>
                  <AdminPage />
                </AdminRoute>
              }
            />
          </Routes>
        </BrowserRouter>
      </div>
    </>
  );
}

export default App;
